package com.bridee.api.service;

import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemCasal;
import com.bridee.api.entity.enums.ImagemCasalEnum;
import com.bridee.api.exception.BadRequestEntityException;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.mapper.request.ImageMapper;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import com.bridee.api.repository.ImagemCasalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImagemCasalService {

    private final ImagemCasalRepository repository;
    private final ImageMapper imageMapper;
    private final ImagemService imagemService;
    private final BlobStorageStrategy blobStorageStrategy;
    private final CasalService casalService;

    public String casalImage64Encoded(Integer casalId){
        byte[] casalImage = downloadCasalImage(casalId);
        if (Objects.isNull(casalImage)){
            return null;
        }
        return Base64.getEncoder().encodeToString(casalImage);
    }

    private byte[] downloadCasalImage(Integer casalId){
        Imagem casalImage = repository.findProfileImageByCasalId(casalId);
        if (Objects.isNull(casalImage)){
            return null;
        }
        return blobStorageStrategy.downloadFile(casalImage.getNome());
    }

    public void favoriteImage(Integer casalId, ImageMetadata metadata){
        if(!metadata.getTipo().equals(ImagemCasalEnum.FAVORITO)){
            throw new BadRequestEntityException("Tipo da imagem não é válido");
        }
        if(repository.existsByUrl(metadata.getUrl())){
            throw new ResourceAlreadyExists("Já existe uma imagem com essa url salva para este usuário");
        }
        Imagem imagem = createImage(metadata);
        createImagemCasal(casalId, imagem);
    }

    public void uploadCasalImage(ImageMetadata imageMetadata,
                                 MultipartFile multipartFile){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Casal casal = casalService.findByEmail(email);
        log.info("CASAL: upload da imagem de perfil do casal de id: {}", casal.getId());
        Imagem imagem = buildCasalImage(casal.getId(), imageMetadata);
        imagemService.uploadImage(multipartFile, imagem.getNome());
    }

    private Imagem buildCasalImage(Integer casalId, ImageMetadata imageMetadata){
        removePreviousProfileImageCasal(casalId, imageMetadata);
        Imagem imagem = createImage(imageMetadata);
        createImagemCasal(casalId, imagem);
        return imagem;
    }

    private void removePreviousProfileImageCasal(Integer casalId, ImageMetadata imageMetadata) {
        Optional<ImagemCasal> profileImageCasalOpt = repository
                .findProfileCasalImageByCasalId(casalId);
        profileImageCasalOpt.ifPresent(repository::delete);
    }

    private Imagem createImage(ImageMetadata imageMetadata){
        Imagem imagem = imageMapper.fromMetadata(imageMetadata);
        return imagemService.save(imagem);
    }

    private void createImagemCasal(Integer casalId, Imagem imagem){
        ImagemCasal imagemCasal = new ImagemCasal(null, new Casal(casalId), imagem);
        repository.save(imagemCasal);
    }

    public Page<Imagem> findFavoriteImages(Pageable pageable, Integer casalId) {
        return repository.findAllCasalFavoritesImages(pageable, casalId);
    }
}
