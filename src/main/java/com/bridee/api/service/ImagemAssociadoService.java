package com.bridee.api.service;

import com.bridee.api.dto.response.ImagemResponseDto;
import com.bridee.api.entity.ImagemAssociado;
import com.bridee.api.entity.enums.TipoImagemAssociadoEnum;
import com.bridee.api.repository.projection.associado.ImagemAssociadoProjection;
import com.bridee.api.repository.ImagemAssociadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImagemAssociadoService {

    private final ImagemAssociadoRepository repository;
    private final ImagemService imagemService;

    public List<ImagemAssociado> saveAll(List<ImagemAssociado> imagens){
        return repository.saveAll(imagens);
    }

    public ImagemResponseDto findImagemPrincipalBase64(Integer id){
        ImagemAssociadoProjection imagemPrincipal = repository.findImagemAssociadoByTipo(id, TipoImagemAssociadoEnum.PRINCIPAL);
        if (Objects.isNull(imagemPrincipal)){
            return null;
        }
        String data = downloadImage(imagemPrincipal);
        return new ImagemResponseDto(imagemPrincipal.getId(), data);
    }

    private String downloadImage(ImagemAssociadoProjection imagemPrincipal){
        return imagemService.downloadImage(imagemPrincipal.getNome());
    }

    public List<ImagemResponseDto> findImagensSecundarias(Integer id) {
        List<ImagemAssociadoProjection> nomeImagensSecundarias = repository.findImagensAssociadoByTipo(id, TipoImagemAssociadoEnum.SECUNDARIA);
        if (Objects.isNull(nomeImagensSecundarias) || nomeImagensSecundarias.isEmpty()){
            return null;
        }
        return nomeImagensSecundarias.stream().map(this::buildImagemSecundaria).toList();
    }

    private ImagemResponseDto buildImagemSecundaria(ImagemAssociadoProjection imagem){
        String data = imagemService.downloadImage(imagem.getNome());
        return new ImagemResponseDto(imagem.getId(), data);
    }
}
