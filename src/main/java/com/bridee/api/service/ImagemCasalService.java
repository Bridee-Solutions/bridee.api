package com.bridee.api.service;

import com.bridee.api.configuration.cache.CacheConstants;
import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemCasal;
import com.bridee.api.entity.enums.ImagemCasalEnum;
import com.bridee.api.exception.BadRequestEntityException;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.request.ImageMapper;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import com.bridee.api.repository.ImagemCasalRepository;
import com.bridee.api.utils.ApplicationCloudProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImagemCasalService {

    private final ApplicationCloudProvider applicationCloudProvider;
    private final ImagemCasalRepository repository;
    private final ImageMapper imageMapper;
    private final ImagemService imagemService;
    private BlobStorageStrategy blobStorageStrategy;
    private final CasalService casalService;

    @PostConstruct
    public void init(){
        blobStorageStrategy = applicationCloudProvider.getBlobImplementation();
    }

    public String casalImage64Encoded(Integer casalId){
        return downloadCasalImage(casalId);
    }

    private String downloadCasalImage(Integer casalId){
        Imagem casalImage = repository.findProfileImageByCasalId(casalId);
        if (Objects.isNull(casalImage)){
            return null;
        }
        return blobStorageStrategy.downloadFile(casalImage.getNome());
    }

    public Imagem favoriteImage(Integer casalId, ImageMetadata metadata){
        validateFavoriteImage(casalId, metadata);
        Imagem imagem = findImageToFavorite(metadata);
        createImagemCasal(casalId, imagem);
        return imagem;
    }

    public void uploadCasalImage(ImageMetadata imageMetadata,
                                 MultipartFile multipartFile){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Casal casal = casalService.findByEmail(email);
        log.info("CASAL: upload da imagem de perfil do casal de id: {}", casal.getId());
        Imagem imagem = buildCasalImage(casal.getId(), imageMetadata);
        imagemService.uploadImage(multipartFile, imagem.getNome());
    }

    private Imagem findImageToFavorite(ImageMetadata metadata){
        String url = metadata.getUrl();
        Imagem imagem = null;
        if(Objects.nonNull(url) && !repository.existsImageByUrl(url)){
            imagem = createImage(metadata);
            return imagem;
        }
        return repository.findByUrl(url)
                    .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"));
    }

    private void validateFavoriteImage(Integer casalId, ImageMetadata metadata){
        if(!metadata.getTipo().equals(ImagemCasalEnum.FAVORITO)){
            throw new BadRequestEntityException("Tipo da imagem não é válido");
        }
        if(repository.existsByUrlAndCasalId(casalId, metadata.getUrl())){
            throw new ResourceAlreadyExists("Já existe uma imagem com essa url salva para este usuário");
        }
    }

    private Imagem buildCasalImage(Integer casalId, ImageMetadata imageMetadata){
        removePreviousProfileImageCasal(casalId);
        Imagem imagem = createImage(imageMetadata);
        createImagemCasal(casalId, imagem);
        return imagem;
    }

    private void removePreviousProfileImageCasal(Integer casalId) {
        Optional<ImagemCasal> profileImageCasalOpt = repository
                .findProfileCasalImageByCasalId(casalId);
        profileImageCasalOpt.ifPresent(repository::delete);
    }

    private Imagem createImage(ImageMetadata imageMetadata){
        Imagem imagem = imageMapper.fromMetadata(imageMetadata);
        return imagemService.save(imagem);
    }

    private void createImagemCasal(Integer casalId, Imagem imagem){
        Casal casal = new Casal(casalId);
        ImagemCasal imagemCasal = new ImagemCasal(null, casal, imagem);
        repository.save(imagemCasal);
    }

    public Page<Imagem> findFavoriteImages(Pageable pageable, Integer casalId) {
        return repository.findAllCasalFavoritesImages(pageable, casalId);
    }

    @Cacheable(cacheNames = CacheConstants.FAVORITE_IMAGE)
    public List<Imagem> findAllFavoriteImages(Integer casalId){
        return repository.findAllCasalFavoritesImages(casalId);
    }

    @Transactional
    public void desfavorite(Integer id, Integer casalId) {
        repository.deleteByImagemIdAndCasalId(id, casalId);
        if(!repository.existsByImagemId(id)){
            imagemService.deleteById(id);
        }
    }
}
