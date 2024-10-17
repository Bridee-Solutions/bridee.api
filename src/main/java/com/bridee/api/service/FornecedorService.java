package com.bridee.api.service;

import com.bridee.api.entity.Fornecedor;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.response.FornecedorGeralResponseMapper;
import com.bridee.api.projection.fornecedor.FornecedorGeralResponseDto;
import com.bridee.api.projection.fornecedor.FornecedorGeralResponseProjection;
import com.bridee.api.projection.fornecedor.FornecedorResponseProjection;
import com.bridee.api.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FornecedorService {

    private final FornecedorRepository repository;
    private final SubcategoriaServicoService subcategoriaServicoService;
    private final CategoriaServicoService categoriaServicoService;
    private final ImagemService imagemService;
    private final FormaPagamentoService formaPagamentoService;
    private final FornecedorGeralResponseMapper geralResponseMapper;

    public Page<Fornecedor> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Fornecedor findById(Integer id){
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Page<FornecedorResponseProjection> findFornecedorDetails(Integer subcategoriaId, Pageable pageable){
        subcategoriaServicoService.existsById(subcategoriaId);
        return repository.findFornecedorDetailsBySubcategoria(subcategoriaId, pageable);
    }

    public Page<FornecedorResponseProjection> findFornecedorDetailsByCategoria(Integer categoriaId, Pageable pageable){
        categoriaServicoService.existsById(categoriaId);
        return repository.findFornecedorDetailsByCategoria(categoriaId, pageable);
    }

    public FornecedorGeralResponseDto findFornecedorInformations(Integer id){

        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }

        FornecedorGeralResponseProjection resultProjection = repository.findFornecedorInformations(id);
        List<String> imagesUrl = imagemService.findUrlImagensFornecedor(id);
        List<String> nomeFormasPagamento = formaPagamentoService.findNomeFormasPagamentoFornecedor(id);

        FornecedorGeralResponseDto geralDto = geralResponseMapper.toGeralDto(resultProjection);
        geralDto.setImagens(imagesUrl);
        geralDto.setFormasPagamento(nomeFormasPagamento);

        return geralDto;
    }

    public Fornecedor save(Fornecedor fornecedor){
        Optional<Fornecedor> optionalFornecedor = repository.findByEmail(fornecedor.getEmail());
        if(optionalFornecedor.isPresent()) throw new ResourceAlreadyExists("Fornecedor já cadastrado");
        return repository.save(fornecedor);
    }

    public Fornecedor update(Fornecedor fornecedor, Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }
        fornecedor.setId(id);
        return repository.save(fornecedor);
    }

    public void deleteById(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }
        repository.deleteById(id);
    }

}
