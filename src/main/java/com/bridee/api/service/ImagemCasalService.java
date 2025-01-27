package com.bridee.api.service;

import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemCasal;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.request.ImageMapper;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import com.bridee.api.repository.CasamentoRepository;
import com.bridee.api.repository.ImagemCasalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImagemCasalService {

    private final ImagemCasalRepository repository;
    private final ImageMapper imageMapper;
    private final ImagemService imagemService;
    private final BlobStorageStrategy blobStorageStrategy;
    private final CasamentoRepository casamentoRepository;

    public String casalImage64Encoded(Integer casalId){
        byte[] casalImage = downloadCasalImage(casalId);
        if (Objects.isNull(casalImage)){
            return null;
        }
        return Base64.getEncoder().encodeToString(casalImage);
    }

    private byte[] downloadCasalImage(Integer casalId){
        Imagem casalImage = repository.findImageByCasalId(casalId);
        if (Objects.isNull(casalImage)){
            return null;
        }
        return blobStorageStrategy.downloadFile(casalImage.getNome());
    }

    public void uploadCasalImage(Integer casamentoId, ImageMetadata imageMetadata,
                                 MultipartFile multipartFile){
        Casamento casamento = casamentoRepository.findById(casamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Casamento não encontrado!"));
        Casal casal = casamento.getCasal();
        Imagem imagem = buildCasalImage(casal.getId(), imageMetadata);
        imagemService.uploadImage(multipartFile, imagem.getNome());
    }

    private Imagem buildCasalImage(Integer casalId, ImageMetadata imageMetadata){
        Imagem imagem = createImage(imageMetadata);
        removePreviousImagemCasal(casalId);
        createImagemCasal(casalId, imagem);
        return imagem;
    }

    private Imagem createImage(ImageMetadata imageMetadata){
        Imagem imagem = imageMapper.fromMetadata(imageMetadata);
        return imagemService.save(imagem);
    }

    private void removePreviousImagemCasal(Integer casalId) {
        Optional<ImagemCasal> imageCasalOpt = repository.findByCasalId(casalId);
        imageCasalOpt.ifPresent(repository::delete);
    }

    private void createImagemCasal(Integer casalId, Imagem imagem){
        ImagemCasal imagemCasal = new ImagemCasal(null, new Casal(casalId), imagem);
        repository.save(imagemCasal);
    }

}
