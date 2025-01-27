package com.bridee.api.service;

import com.bridee.api.dto.response.ImagemResponseDto;
import com.bridee.api.entity.ImagemAssociado;
import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.entity.enums.TipoImagemAssociadoEnum;
import com.bridee.api.projection.associado.ImagemAssociadoProjection;
import com.bridee.api.repository.ImagemAssociadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        byte[] imagem = imagemService.downloadImage(imagemPrincipal.getNome());
        if (Objects.isNull(imagem)){
            return null;
        }
        String data = Base64.getEncoder().encodeToString(imagem);
        return new ImagemResponseDto(imagemPrincipal.getId(), data);
    }

    public List<ImagemResponseDto> findImagensSecundarias(Integer id) {
        List<ImagemAssociadoProjection> nomeImagensSecundarias = repository.findImagensAssociadoByTipo(id, TipoImagemAssociadoEnum.SECUNDARIA);
        if (Objects.isNull(nomeImagensSecundarias) || nomeImagensSecundarias.isEmpty()){
            return null;
        }
        return nomeImagensSecundarias.stream().map(this::buildImagemSecundaria).toList();
    }

    private ImagemResponseDto buildImagemSecundaria(ImagemAssociadoProjection imagem){
        String data = "";
        byte[] image = imagemService.downloadImage(imagem.getNome());
        if (Objects.nonNull(image)){
            data = Base64.getEncoder()
                    .encodeToString(image);
        }
        return new ImagemResponseDto(imagem.getId(), data);
    }
}
