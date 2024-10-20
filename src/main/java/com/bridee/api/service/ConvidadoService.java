package com.bridee.api.service;

import com.bridee.api.dto.request.MesaConvidadoRequestDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.entity.Mesa;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.ConvidadoRepository;
import com.bridee.api.repository.specification.ConvidadoFilter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ConvidadoService {

    private final ConvidadoRepository repository;

    public List<Convidado> findAll() {
        return repository.findAll();
    }

    public Convidado findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Convidado não encontrado!"));
    }

    public Convidado save(Convidado convidado, Integer conviteId) {
        validateConvidadoConvite(convidado, conviteId);
        return repository.save(convidado);
    }

    public Convidado update(Convidado convidado, Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Convidado não encontrado!");
        }
        convidado.setId(id);
        return repository.save(convidado);
    }

    public List<Convidado> saveAll(List<Convidado> convidados, Convite convite){
        convidados = removeDuplicatedConvidados(convidados);
        convidados.forEach(convidado -> convidado.setConvite(convite));
        return repository.saveAll(convidados);
    }

    public void vinculateConvidadoToMesa(List<MesaConvidadoRequestDto> mesaConvidadoDto){

        List<Integer> convidadosIds = mesaConvidadoDto.stream().map(MesaConvidadoRequestDto::getConvidadoId).toList();
        List<Convidado> convidados = repository.findAllById(convidadosIds);

        convidados.forEach(convidado -> {
            mesaConvidadoDto.forEach(mesa -> {
                if (mesa.getConvidadoId().equals(convidado.getId())){
                    convidado.setMesa(Mesa.builder()
                            .id(mesa.getMesaId())
                            .build());
                }
            });
        });

        repository.saveAll(convidados);
    }

    public void deleteById(Integer id) {
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Convidado não encontrado!");
        }
        repository.deleteById(id);
    }

    private List<Convidado> findByCasamentoIdAndNome(Integer casamentoId, String nome){
        //TODO: verificar se o casamento existe.
        Specification<Convidado> spec = null;
        if(Objects.nonNull(nome)){
            spec = Specification
                    .where(ConvidadoFilter.hasCasamentoId(casamentoId)).and(ConvidadoFilter.hasNome(nome));
        }else{
            spec = Specification
                    .where(ConvidadoFilter.hasCasamentoId(casamentoId));
        }
        return repository.findAll(spec);
    }

    public List<Convidado> convidadosWithoutMesa(List<Mesa> mesas, String nome, Integer casamentoId){
        return extractConvidadosWithoutMesa(mesas, nome, casamentoId);
    }

    private List<Convidado> extractConvidadosWithoutMesa(List<Mesa> mesas, String nome, Integer casamentoId){

        List<Convidado> allConvidados = findByCasamentoIdAndNome(casamentoId, nome);

        if (mesas.isEmpty()){
            return allConvidados;
        }

        Optional<List<Convidado>> convidadosWithMesaOptional = mesas.stream().map(Mesa::getConvidados).findFirst();

        if (convidadosWithMesaOptional.isEmpty()){
            return allConvidados;
        }

        List<Convidado> convidadosWithMesa = convidadosWithMesaOptional.get();

        return allConvidados.stream().filter(convidado -> !convidadosWithMesa.contains(convidado))
                .toList();
    }

    private List<Convidado> removeDuplicatedConvidados(List<Convidado> convidados){
        List<String> telefones = new ArrayList<>();
        List<Convidado> convidadoList = new ArrayList<>();
        convidados.forEach(convidado -> {
            if (!telefones.contains(convidado.getTelefone())){
                telefones.add(convidado.getTelefone());
                convidadoList.add(convidado);
            }
        });
        return convidadoList;
    }

    private void validateConvidadoConvite(Convidado convidado, Integer conviteId){
        if (repository.existsByTelefoneAndConviteId(convidado.getTelefone(), conviteId)){
            throw new ResourceAlreadyExists("Convidado já cadastrado para esse convite!");
        }
        vinculateConvidadoToConvite(convidado, conviteId);
    }

    private void vinculateConvidadoToConvite(Convidado convidado, Integer conviteId){
        Convite convite = Convite.builder()
                .id(conviteId)
                .build();
        convidado.setConvite(convite);
    }
}
