package com.bridee.api.service;

import com.bridee.api.configuration.cache.CacheConstants;
import com.bridee.api.dto.response.ImagemResponseDto;
import com.bridee.api.entity.Fornecedor;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.response.AssociadoGeralResponseMapper;
import com.bridee.api.repository.FornecedorRepository;
import com.bridee.api.repository.projection.associado.AssociadoGeralResponseDto;
import com.bridee.api.repository.projection.associado.AssociadoGeralResponseProjection;
import com.bridee.api.repository.projection.associado.AssociadoResponseDto;
import com.bridee.api.repository.projection.associado.AssociadoResponseProjection;
import com.bridee.api.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FornecedorService {

    private final FornecedorRepository repository;
    private final SubcategoriaServicoService subcategoriaServicoService;
    private final CategoriaServicoService categoriaServicoService;
    private final ImagemService imagemService;
    private final TipoCasamentoService tipoCasamentoService;
    private final FormaPagamentoService formaPagamentoService;
    private final AssociadoGeralResponseMapper geralResponseMapper;
    private final InformacaoAssociadoService informacaoAssociadoService;

    @Transactional(readOnly = true)
    public Page<Fornecedor> findAll(Pageable pageable){
        return repository.findAllFornecedores(pageable);
    }

    public Fornecedor findById(Integer id){
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConstants.FORNECEDOR)
    public Page<AssociadoResponseDto> findFornecedorDetails(Integer subcategoriaId, Pageable pageable){
        subcategoriaServicoService.existsById(subcategoriaId);
        Page<AssociadoResponseProjection> fornecedorDetailsPage = repository.findFornecedorDetailsBySubcategoria(subcategoriaId, pageable);

        List<AssociadoResponseDto> associadoResponseDto = geralResponseMapper.toResponseDto(fornecedorDetailsPage.getContent());
        try{
            fillFornecedorDetailsImages(associadoResponseDto);
        }catch (Exception e){
            log.error("Houve um erro ao buscar as informações do assessor %s".formatted(e.getMessage()));
            e.printStackTrace();
        }
        return PageUtils.collectionToPage(associadoResponseDto,
                fornecedorDetailsPage);
    }

    @Transactional(readOnly = true, noRollbackFor = ConnectException.class)
    public Page<Fornecedor> findFornecedoresByCategoriaAndNome(Integer categoriaId, String nome, Pageable pageable){
        categoriaServicoService.existsById(categoriaId);
        return repository.findFornecedoresByCategoriaAndNome(categoriaId, nome, pageable);
    }

    private void fillFornecedorDetailsImages(List<AssociadoResponseDto> associadoResponse){
        associadoResponse.forEach(associado -> {
            ImagemResponseDto imagemPrincipal = informacaoAssociadoService.findImagemPrincipal(associado.getInformacaoAssociadoId());
            if (Objects.nonNull(imagemPrincipal)){
                associado.setImagemPrincipal(imagemPrincipal.getData());
            }
        });
    }

    @Transactional(readOnly = true)
    public AssociadoGeralResponseDto findFornecedorInformations(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }

        AssociadoGeralResponseProjection resultProjection = repository.findFornecedorInformations(id);
        if (Objects.isNull(resultProjection)){
            throw new ResourceNotFoundException("Não foi possível recuperar as informações do fornecedor");
        }

        return buildAssociadoGeralResponseDto(resultProjection);
    }

    private AssociadoGeralResponseDto buildAssociadoGeralResponseDto(AssociadoGeralResponseProjection result){
        List<String> imagesUrl = imagemService.findUrlBase64ImagensFornecedor(result.getId());
        List<String> nomeFormasPagamento = formaPagamentoService.findNomeFormasPagamentoFornecedor(result.getId());
        List<String> tiposCasamento = tipoCasamentoService.findNomeTiposCasamentoFornecedor(result.getId());

        AssociadoGeralResponseDto geralDto = geralResponseMapper.toGeralDto(result);
        geralDto.setImagens(imagesUrl);
        geralDto.setFormasPagamento(nomeFormasPagamento);
        geralDto.setTiposCasamento(tiposCasamento);
        return geralDto;
    }

    public Fornecedor save(Fornecedor fornecedor){
        Optional<Fornecedor> optionalFornecedor = repository.findByEmail(fornecedor.getEmail());
        if(optionalFornecedor.isPresent()) throw new ResourceAlreadyExists("Fornecedor já cadastrado");
        return repository.save(fornecedor);
    }

    public Fornecedor update(Fornecedor fornecedor, Integer id){
        if (!repository.existsById(id)){
            log.error("FORNECEDOR: fornecedor não encontrado.");
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
