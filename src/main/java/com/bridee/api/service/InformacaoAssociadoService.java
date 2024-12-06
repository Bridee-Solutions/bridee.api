package com.bridee.api.service;

import com.bridee.api.dto.request.InformacaoAssociadoPerfilDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemAssociado;
import com.bridee.api.entity.enums.TipoImagemAssociadoEnum;
import com.bridee.api.exception.BadRequestEntityException;
import com.bridee.api.mapper.request.InformacaoAssociadoMapper;
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
    private final AssessorService assessorService;
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
        List<MultipartFile> imagensSecundarias = informacaoAssociadoPerfil.getImagensSecundarias();
        uploadImagemPrincipal(imagemPrincipal, imagens);
        uploadImagensSecudarias(imagensSecundarias, imagens);
    }

    private void uploadImagemPrincipal(MultipartFile imagemPrincipal, List<Imagem> imagens) {
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.PRINCIPAL.name()))
                .findFirst().ifPresent((image) -> imagemService.uploadImage(imagemPrincipal, image.getNome()));
    }

    private void uploadImagensSecudarias(List<MultipartFile> imagensSecundarias, List<Imagem> imagens){
        imagens.stream().filter(image -> image.getTipo().equals(TipoImagemAssociadoEnum.SECUNDARIA.name()))
            .forEach((imagem) -> {
                imagensSecundarias.forEach(imagemSecundaria -> {
                if (imagem.getNome().contains(imagemSecundaria.getName())){
                    imagemService.uploadImage(imagemSecundaria, imagem.getNome());
                }
            });
        });
    }

    private void vinculateAssessorToInformation(InformacaoAssociado informacaoAssociado, Integer assessorId){
        assessorService.existsById(assessorId);
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
}
