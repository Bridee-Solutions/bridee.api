package com.bridee.api.service;

import com.bridee.api.dto.request.MesaConvidadoRequestDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.entity.Mesa;
import com.bridee.api.entity.enums.TipoConvidado;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.ConvidadoRepository;
import com.bridee.api.repository.specification.ConvidadoFilter;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ConvidadoService {

    private final ConvidadoRepository repository;
    private final CasamentoService casamentoService;

    public List<Convidado> findAll() {
        return repository.findAll();
    }

    public Convidado findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Convidado não encontrado!"));
    }

    public Convidado save(Convidado convidado, Integer conviteId) {
        validateConvidadoConvite(convidado, conviteId);
        convidado.setConvite(Convite.builder()
                .id(conviteId)
                .build());
        return repository.save(convidado);
    }

    public Convidado update(Convidado convidado, Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Convidado não encontrado!");
        }
        convidado.setId(id);
        return repository.save(convidado);
    }

    public void saveAllInvites(List<Convidado> convidados, Convite convite){
        convidados.forEach(convidado -> convidado.setConvite(convite));
        saveAll(convidados);
    }

    public List<Convidado> saveAll(List<Convidado> convidados){
        removeDuplicatedConvidados(convidados);
        return repository.saveAll(convidados);
    }

    public void vinculateConvidadosToMesa(List<MesaConvidadoRequestDto> mesaConvidadoDto){

        List<Integer> convidadosIds = mesaConvidadoDto.stream().map(MesaConvidadoRequestDto::getConvidadoId).toList();
        List<Convidado> convidados = repository.findAllById(convidadosIds);

        if (convidados.isEmpty()){
            throw new ResourceNotFoundException("Nenhum dos convidados informados existem!");
        }

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

    public List<Convidado> findByCasamentoIdAndNome(Integer casamentoId, String nome){
        casamentoService.existsById(casamentoId);

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

        Optional<List<Convidado>> convidadosWithMesaOptional = convidadosWithMesa(mesas);

        if (convidadosWithMesaOptional.isEmpty()){
            return allConvidados;
        }

        List<Convidado> convidadosWithMesa = convidadosWithMesaOptional.get();

        return allConvidados.stream().filter(convidado -> !convidadosWithMesa.contains(convidado))
                .toList();
    }

    public Optional<List<Convidado>> convidadosWithMesa(List<Mesa> mesas){
        return mesas.stream().map(Mesa::getConvidados).findFirst();
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

    public List<Convidado> findAllByIds(@NotNull List<@NotNull Integer> convidadosIds) {
        return repository.findAllById(convidadosIds);
    }
}
