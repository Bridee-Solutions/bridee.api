package com.bridee.api.service;

import com.bridee.api.entity.Imagem;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.FornecedorRepository;
import com.bridee.api.repository.ImagemRepository;
import com.bridee.api.utils.ApplicationCloudProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImagemService {

    private final ApplicationCloudProvider applicationCloudProvider;
    private final ImagemRepository repository;
    private final FornecedorRepository fornecedorRepository;
    private final AssessorRepository assessorRepository;
    private BlobStorageStrategy blobStorageStrategy;

    @PostConstruct
    public void init(){
        blobStorageStrategy = applicationCloudProvider.getBlobImplementation();
    }

    public List<String> findUrlBase64ImagensFornecedor(Integer fornecedorId){
        log.info("FORNECEDOR: buscando as imagens do fornecedor com id: {}", fornecedorId);
        return findUrlImagensFornecedor(fornecedorId);
    };

    public List<String> findUrlImagensFornecedor(Integer fornecedorId){
        return findImagensFornecedor(fornecedorId).stream()
                .map(imagem -> blobStorageStrategy.downloadFile(imagem.getNome())).toList();
    }

    private List<Imagem> findImagensFornecedor(Integer fornecedorId){
        if (!fornecedorRepository.existsById(fornecedorId)){
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }
        return repository.findByFornecedorId(fornecedorId);
    }

    public List<String> findBase64UrlImagensAssessor(Integer assessorId){
        log.info("ASSESSOR: buscando as imagens do assessor com id: {}", assessorId);
        return findUrlImagensAssessor(assessorId);
    }

    public List<String> findUrlImagensAssessor(Integer assessorId){
        return findImagensAssessor(assessorId).stream()
                .map(imagem -> blobStorageStrategy.downloadFile(imagem.getNome())).toList();
    }

    private List<Imagem> findImagensAssessor(Integer assessorId){
        if (!assessorRepository.existsById(assessorId)){
            throw new ResourceNotFoundException("Assessor não encontrado!");
        }
        return repository.findByAssessorId(assessorId);
    }

    @Transactional
    public Imagem save(Imagem imagem) {
        return repository.save(imagem);
    }

    public String downloadImage(String imageName){
        return blobStorageStrategy.downloadFile(imageName);
    };

    public void uploadImage(MultipartFile image, String imageName){
        blobStorageStrategy.uploadFile(image, imageName);
    }

    @Transactional
    public void deleteById(Integer id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Imagem não encontrada");
        }
        repository.deleteById(id);
    }
}
