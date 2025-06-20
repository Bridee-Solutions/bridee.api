package com.bridee.api.service;

import com.bridee.api.configuration.cache.CacheConstants;
import com.bridee.api.dto.request.InformacaoAssociadoPerfilDto;
import com.bridee.api.dto.response.ImagemResponseDto;
import com.bridee.api.dto.response.InformacaoAssociadoDetalhes;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Fornecedor;
import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemAssociado;
import com.bridee.api.entity.enums.TipoImagemAssociadoEnum;
import com.bridee.api.exception.BadRequestEntityException;
import com.bridee.api.mapper.request.InformacaoAssociadoMapper;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.FornecedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.InformacaoAssociadoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InformacaoAssociadoService {

    private final InformacaoAssociadoRepository repository;
    private final AssessorRepository assessorRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ImagemService imagemService;
    private final ImagemAssociadoService imagemAssociadoService;
    private final InformacaoAssociadoMapper requestMapper;
    private final TipoCasamentoAssociadoService tipoCasamentoAssociadoService;
    private final TipoCerimoniaAssociadoService tipoCerimoniaAssociadoService;
    private final FormaPagamentoAssociadoService formaPagamentoAssociadoService;

    @CacheEvict(cacheNames = CacheConstants.ASSESSOR)
    public InformacaoAssociado save(InformacaoAssociadoPerfilDto informacaoAssociadoPerfil, Integer assessorId){
        InformacaoAssociado informacaoAssociado = requestMapper.toEntity(informacaoAssociadoPerfil.getInformacaoAssociado());
        if (repository.existsByAssessorId(assessorId) && Objects.isNull(informacaoAssociado.getId())){
            log.error("INFORMACAO ASSOCIADO: Não foi possível cadastrar uma nova informação associado, assessor já possui uma informação");
            throw new BadRequestEntityException("Não foi possível cadastrar uma nova informação associado, assessor já possui uma informação");
        }
        vinculateAssessorToInformation(informacaoAssociado, assessorId);
        List<Imagem> imagens = saveInformacaoAssociadoImages(informacaoAssociado);
        uploadAssociadoImages(informacaoAssociadoPerfil, imagens);
        return informacaoAssociado;
    }

    @CacheEvict(cacheNames = CacheConstants.FORNECEDOR)
    public InformacaoAssociado saveFornecedor(InformacaoAssociadoPerfilDto informacaoAssociadoPerfil, Integer fornecedorId){
        InformacaoAssociado informacaoAssociado = requestMapper.toEntity(informacaoAssociadoPerfil.getInformacaoAssociado());
        if (repository.existsByFornecedorId(fornecedorId) && Objects.isNull(informacaoAssociado.getId())){
            throw new BadRequestEntityException("Não foi possível cadastrar uma nova informação associado, assessor já possui uma ");
        }
        vinculateFornecedorToInformation(informacaoAssociado, fornecedorId);
        List<Imagem> imagens = saveInformacaoAssociadoImages(informacaoAssociado);
        uploadAssociadoImages(informacaoAssociadoPerfil, imagens);
        return informacaoAssociado;
    }

    private void vinculateFornecedorToInformation(InformacaoAssociado informacaoAssociado, Integer fornecedorId) {
        if (!fornecedorRepository.existsById(fornecedorId)) {
            throw new ResourceNotFoundException("Assessor não encontrado");
        }
        Fornecedor fornecedor = Fornecedor.builder()
                .id(fornecedorId)
                .build();
        informacaoAssociado.setFornecedor(fornecedor);
    }

    private void uploadAssociadoImages(InformacaoAssociadoPerfilDto informacaoAssociadoPerfil, List<Imagem> imagens) {
        MultipartFile imagemPrincipal = informacaoAssociadoPerfil.getImagemPrincipal();
        MultipartFile imagemSecundaria = informacaoAssociadoPerfil.getImagemSecundaria();
        MultipartFile imagemTerciaria = informacaoAssociadoPerfil.getImagemTerciaria();
        MultipartFile imagemQuaternaria = informacaoAssociadoPerfil.getImagemQuaternaria();
        MultipartFile imagemQuinaria = informacaoAssociadoPerfil.getImagemQuinaria();
        uploadImagemPrincipal(imagemPrincipal, imagens);
        uploadImagemSecudaria(imagemSecundaria, imagens);
        uploadImagemTerciaria(imagemTerciaria, imagens);
        uploadImagemQuaternaria(imagemQuaternaria, imagens);
        uploadImagemQuinaria(imagemQuinaria, imagens);
    }

    private void uploadImagemPrincipal(MultipartFile imagemPrincipal, List<Imagem> imagens) {
        log.info("INFORMAÇÃO ASSOCIADO: realizando upload da imagem principal");
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.PRINCIPAL.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemPrincipal, image.getNome()));
    }

    private void uploadImagemSecudaria(MultipartFile imagemSecundaria, List<Imagem> imagens){
        log.info("INFORMAÇÃO ASSOCIADO: realizando upload da imagem secundária");
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.SECUNDARIA.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemSecundaria, image.getNome()));
    }

    private void uploadImagemTerciaria(MultipartFile imagemTerciaria, List<Imagem> imagens){
        log.info("INFORMAÇÃO ASSOCIADO: realizando upload da imagem terciária");
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.TERCIARIA.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemTerciaria, image.getNome()));
    }

    private void uploadImagemQuaternaria(MultipartFile imagemQuaternaria, List<Imagem> imagens){
        log.info("INFORMAÇÃO ASSOCIADO: realizando upload da imagem quaternária");
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.QUATERNARIA.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemQuaternaria, image.getNome()));
    }

    private void uploadImagemQuinaria(MultipartFile imagemQuinaria, List<Imagem> imagens) {
        log.info("INFORMAÇÃO ASSOCIADO: realizando upload da imagem quinária");
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.QUINARIA.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemQuinaria, image.getNome()));
    }

    private void vinculateAssessorToInformation(InformacaoAssociado informacaoAssociado, Integer assessorId){
        if (!assessorRepository.existsById(assessorId)) {
            log.error("INFORMAÇÃO ASSOCIADO: assessor não encontrado para vincular a informação.");
            throw new ResourceNotFoundException("Assessor não encontrado");
        }
        Assessor assessor = new Assessor(assessorId);
        informacaoAssociado.setAssessor(assessor);
    }

    private List<Imagem> saveInformacaoAssociadoImages(InformacaoAssociado informacaoAssociado){
        log.info("INFORMAÇÃO ASSOCIADO: salvando imagens da informação associado");
        informacaoAssociado.getImagemAssociados().forEach(imagemAssociado -> {
            imagemAssociado.setImagem(imagemService.save(imagemAssociado.getImagem()));
        });

        log.info("INFORMAÇÃO ASSOCIADO: salvando informação associado");
        InformacaoAssociado savedInformacaoAssociado = repository.save(informacaoAssociado);

        log.info("INFORMAÇÃO ASSOCIADO: vinculando imagens à informação associado");
        savedInformacaoAssociado.getImagemAssociados().forEach(imagemAssociado ->
                imagemAssociado.setInformacaoAssociado(savedInformacaoAssociado));

        log.info("INFORMAÇÃO ASSOCIADO: criando as associações das imagens no banco");
        List<ImagemAssociado> createdImages = imagemAssociadoService.saveAll(informacaoAssociado.getImagemAssociados());

        repository.save(savedInformacaoAssociado);
        return createdImages.stream().map(ImagemAssociado::getImagem).toList();
    }

    public InformacaoAssociado update(InformacaoAssociado info, Integer id) {
        InformacaoAssociado informacaoExistente = repository.findByAssessorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Informação de associado não encontrada para o idAssessor: " + id));
    
        info.setId(informacaoExistente.getId());
    
        if (info.getAssessor() == null) {
            info.setAssessor(informacaoExistente.getAssessor());
        }
    
        return repository.save(info);
    }

    public InformacaoAssociado findByAssessorId(Integer id) {
        return repository.findByAssessorId(id)
        .orElseThrow(() -> new ResourceNotFoundException("Informação de associado não encontrada para o idAssessor: " + id));
    }

    public ImagemResponseDto findImagemPrincipal(Integer associadoId){
        if (!repository.existsById(associadoId)) {
            log.error("INFORMAÇÃO ASSOCIADO: associado não encontrado");
            throw new ResourceNotFoundException("Associado não encontrado!");
        }
        return imagemAssociadoService.findImagemPrincipalBase64(associadoId);
    }

    public List<ImagemResponseDto> findImagemSecudaria(Integer associadoId){
        if (!repository.existsById(associadoId)) {
            log.error("INFORMAÇÃO ASSOCIADO: associado não encontrado");
            throw new ResourceNotFoundException("Associado não encontrado!");
        }
        return imagemAssociadoService.findImagensSecundarias(associadoId);
    }

    public InformacaoAssociado findByFornecedorId(Integer id) {
        return repository.findByFornecedorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Informação de associado não encontrada para o idAssessor: " + id));

    }

    @Transactional(readOnly = true)
    public InformacaoAssociadoDetalhes buildInformacaoAssociadoDetalhes(InformacaoAssociado info){
        log.info("INFORMAÇÃO ASSOCIADO: construindo detalhes da informação associado");
        InformacaoAssociadoDetalhes informacaoAssociadoDetalhes = new InformacaoAssociadoDetalhes();
        informacaoAssociadoDetalhes.setTiposCasamento(tipoCasamentoAssociadoService.findAllByInformacaoAssociadoId(info));
        informacaoAssociadoDetalhes.setTiposCerimonia(tipoCerimoniaAssociadoService.findAllByInformacaoAssociadoId(info));
        informacaoAssociadoDetalhes.setFormasPagamento(formaPagamentoAssociadoService.findAllByInformacaoAssociadoId(info));
        informacaoAssociadoDetalhes.setImagemPrimaria(imagemAssociadoService.findImagemPrincipalBase64(info.getId()));
        informacaoAssociadoDetalhes.setImagensSecundarias(imagemAssociadoService.findImagensSecundarias(info.getId()));
        log.info("INFORMAÇÃO ASSOCIADO: detalhes construidos com sucesso");
        return informacaoAssociadoDetalhes;
    }
}
