package com.bridee.api.service;

import com.bridee.api.entity.Imagem;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.CasalRepository;
import com.bridee.api.repository.FornecedorRepository;
import com.bridee.api.repository.ImagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImagemService {

    private final ImagemRepository repository;
    private final FornecedorRepository fornecedorRepository;
    private final AssessorRepository assessorRepository;
    private final BlobStorageStrategy blobStorageStrategy;
    private final CasalRepository casalRepository;

    public List<String> findUrlBase64ImagensFornecedor(Integer fornecedorId){
        return findUrlImagensFornecedor(fornecedorId).stream()
                .map(imagem -> {
                    if (Objects.nonNull(imagem)){
                        return Base64.getEncoder().encodeToString(imagem);
                    }
                    return null;
                }).toList();
    };

    public List<byte[]> findUrlImagensFornecedor(Integer fornecedorId){
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
        return findUrlImagensAssessor(assessorId).stream()
                .map(imagem -> {
                    if (Objects.nonNull(imagem)){
                        return Base64.getEncoder().encodeToString(imagem);
                    }
                    return null;
                }).toList();
    }

    public List<byte[]> findUrlImagensAssessor(Integer assessorId){
        return findImagensAssessor(assessorId).stream()
                .map(imagem -> blobStorageStrategy.downloadFile(imagem.getNome())).toList();
    }

    private List<Imagem> findImagensAssessor(Integer assessorId){
        if (!assessorRepository.existsById(assessorId)){
            throw new ResourceNotFoundException("Assessor não encontrado!");
        }
        return repository.findByAssessorId(assessorId);
    }

    public Imagem save(Imagem imagem) {
        return repository.save(imagem);
    }

    public byte[] downloadImage(String imageName){
        return blobStorageStrategy.downloadFile(imageName);
    };

    public void uploadImage(MultipartFile image, String imageName){
        blobStorageStrategy.uploadFile(image, imageName);
    }
}
