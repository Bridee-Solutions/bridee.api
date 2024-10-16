package com.bridee.api.service;

import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.entity.enums.TipoConvidado;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.ConviteRepository;
import com.bridee.api.repository.specification.ConviteFilter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConviteService {

    private final ConviteRepository repository;
    private final ConvidadoService service;

    public List<Convite> findAllByCasamentoId(Map<String, Object> filter, Integer casamentoId){
        //TODO: validar existência do casamento.
        ConviteFilter spec = new ConviteFilter();
        filter.put("casamentoId", casamentoId);
        spec.fillSpecification(filter);
        return repository.findAll(spec);
    }

    @Transactional
    public Convite save(Convite convite, Integer casamentoId, String telefoneTitular){
        if (repository.existsByNomeAndCasamentoId(convite.getNome(), casamentoId)){
            throw new ResourceAlreadyExists("Convite já cadastrado para esse casamento.");
        }
        Optional<Convidado> optionalConvidado = convite.getConvidados().stream()
                .filter(convidado -> convidado.getTelefone().equals(telefoneTitular)).findFirst();
        if (optionalConvidado.isEmpty()){
            throw new ResourceNotFoundException("Titular não foi adicionado para o convite!");
        }
        optionalConvidado.get().setTipo(TipoConvidado.TITULAR);
        List<Convidado> convidados = service.saveAll(convite.getConvidados());
        convite.setConvidados(convidados);
        return repository.save(convite);
    }

    @Transactional
    public Convite update(Convite convite, Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Convite não encontrado!");
        }
        convite.setId(id);
        return repository.save(convite);
    }

    @Transactional
    public void deleteById(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Convite não encontrado!");
        }
        repository.deleteById(id);
    }

}
