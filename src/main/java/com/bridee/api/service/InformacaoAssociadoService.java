package com.bridee.api.service;

import com.bridee.api.dto.request.InformacaoAssociadoPerfilDto;
import com.bridee.api.dto.response.ImagemResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemAssociado;
import com.bridee.api.entity.enums.TipoImagemAssociadoEnum;
import com.bridee.api.exception.BadRequestEntityException;
import com.bridee.api.mapper.request.InformacaoAssociadoMapper;
import com.bridee.api.repository.AssessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.InformacaoAssociadoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class InformacaoAssociadoService {

    private final InformacaoAssociadoRepository repository;
    private final AssessorRepository assessorRepository;
    private final ImagemService imagemService;
    private final ImagemAssociadoService imagemAssociadoService;
    private final InformacaoAssociadoMapper requestMapper;

    public InformacaoAssociado save(InformacaoAssociadoPerfilDto informacaoAssociadoPerfil, Integer assessorId){
        InformacaoAssociado informacaoAssociado = requestMapper.toEntity(informacaoAssociadoPerfil.getInformacaoAssociado());
        if (repository.existsByAssessorId(assessorId) && Objects.isNull(informacaoAssociado.getId())){
            throw new BadRequestEntityException("Não foi possível cadastrar uma nova informação associado, assessor já possui uma ");
        }
        vinculateAssessorToInformation(informacaoAssociado, assessorId);
        List<Imagem> imagens = saveInformacaoAssociadoImages(informacaoAssociado);
        uploadAssociadoImages(informacaoAssociadoPerfil, imagens);
        return informacaoAssociado;
    }

    private void uploadAssociadoImages(InformacaoAssociadoPerfilDto informacaoAssociadoPerfil, List<Imagem> imagens) {
        MultipartFile imagemPrincipal = informacaoAssociadoPerfil.getImagemPrincipal();
        MultipartFile imagemSecundaria = informacaoAssociadoPerfil.getImagemSecundaria();
        MultipartFile imagemTerciaria = informacaoAssociadoPerfil.getImagemTerciaria();
        MultipartFile imagemQuaternaria = informacaoAssociadoPerfil.getImagemQuaternaria();
        uploadImagemPrincipal(imagemPrincipal, imagens);
        uploadImagemSecudaria(imagemSecundaria, imagens);
        uploadImagemTerciaria(imagemTerciaria, imagens);
        uploadImagemQuaternaria(imagemQuaternaria, imagens);
    }

    private void uploadImagemPrincipal(MultipartFile imagemPrincipal, List<Imagem> imagens) {
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.PRINCIPAL.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemPrincipal, image.getNome()));
    }

    private void uploadImagemSecudaria(MultipartFile imagemSecundaria, List<Imagem> imagens){
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.SECUNDARIA.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemSecundaria, image.getNome()));
    }

    private void uploadImagemTerciaria(MultipartFile imagemTerciaria, List<Imagem> imagens){
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.TERCIARIA.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemTerciaria, image.getNome()));
    }

    private void uploadImagemQuaternaria(MultipartFile imagemQuaternaria, List<Imagem> imagens){
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.QUATERNARIA.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemQuaternaria, image.getNome()));
    }

    private void vinculateAssessorToInformation(InformacaoAssociado informacaoAssociado, Integer assessorId){
        if (!assessorRepository.existsById(assessorId)) {
            throw new ResourceNotFoundException("Assessor não encontrado");
        }
        Assessor assessor = new Assessor(assessorId);
        informacaoAssociado.setAssessor(assessor);
    }

    private List<Imagem> saveInformacaoAssociadoImages(InformacaoAssociado informacaoAssociado){
        informacaoAssociado.getImagemAssociados().forEach(imagemAssociado -> {
            imagemAssociado.setImagem(imagemService.save(imagemAssociado.getImagem()));
        });

        InformacaoAssociado savedInformacaoAssociado = repository.save(informacaoAssociado);

        savedInformacaoAssociado.getImagemAssociados().forEach(imagemAssociado ->
                imagemAssociado.setInformacaoAssociado(savedInformacaoAssociado));

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
            throw new ResourceNotFoundException("Associado não encontrado!");
        }
        return imagemAssociadoService.findImagemPrincipalBase64(associadoId);
    }

    public List<ImagemResponseDto> findImagemSecudaria(Integer associadoId){
        if (!repository.existsById(associadoId)) {
            throw new ResourceNotFoundException("Associado não encontrado!");
        }
        return imagemAssociadoService.findImagensSecundarias(associadoId);
    }
}
