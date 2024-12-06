package com.bridee.api.service;

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

@Service
@RequiredArgsConstructor
public class ImagemAssociadoService {

    private final ImagemAssociadoRepository repository;
    private final ImagemService imagemService;

    public List<ImagemAssociado> saveAll(List<ImagemAssociado> imagens){
        return repository.saveAll(imagens);
    }

    public String findImagemPrincipalBase64(InformacaoAssociado informacaoAssociado){
        Integer id = informacaoAssociado.getId();
        ImagemAssociadoProjection nomeImagemPrincipal = repository.findImagemAssociadoByTipo(id, TipoImagemAssociadoEnum.PRINCIPAL);
        byte[] imagem = imagemService.downloadImage(nomeImagemPrincipal.getNome());
        if (imagem == null){
            return null;
        }
        return Base64.getEncoder().encodeToString(imagem);
    }

    public List<String> findImagensSecundarias(InformacaoAssociado info) {
        Integer id = info.getId();
        List<String> nomeImagensSecundarias = repository.findImagensAssociadoByTipo(id, TipoImagemAssociadoEnum.SECUNDARIA);
        return nomeImagensSecundarias.stream().map(imagem -> Base64.getEncoder()
                .encodeToString(imagemService.downloadImage(imagem))).toList();
    }
}
