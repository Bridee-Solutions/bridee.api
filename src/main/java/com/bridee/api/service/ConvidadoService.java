package com.bridee.api.service;

import com.bridee.api.dto.request.MesaConvidadoRequestDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.entity.Mesa;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.ConvidadoRepository;
import com.bridee.api.repository.specification.ConvidadoFilter;
import com.bridee.api.utils.PatchHelper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConvidadoService {

    private final ConvidadoRepository repository;
    private final CasamentoService casamentoService;
    private final PatchHelper patchHelper;

    public List<Convidado> findAll() {
        return repository.findAll();
    }

    public Convidado findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Convidado não encontrado!"));
    }

    public List<Convidado> findAllByConviteId(Integer conviteId){
        return repository.findAllByConviteId(conviteId);
    }

    public Convidado save(Convidado convidado, Integer conviteId) {
        validateConvidadoConvite(convidado, conviteId);
        convidado.setConvite(Convite.builder()
                .id(conviteId)
                .build());
        return repository.save(convidado);
    }

    public Convidado update(Convidado convidado, Integer id){
        Convidado convidadoToBeUpdated = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        patchHelper.mergeNonNull(convidado, convidadoToBeUpdated);
        return repository.save(convidadoToBeUpdated);
    }

    public void saveAllInvites(List<Convidado> convidados, Convite convite){
        convidados.forEach(convidado -> convidado.setConvite(convite));
        saveAll(convidados);
    }

    public List<Convidado> saveAll(List<Convidado> convidados){
        convidados = removeDuplicatedConvidados(convidados);
        return repository.saveAll(convidados);
    }

    public void vinculateConvidadosToMesa(List<MesaConvidadoRequestDto> mesaConvidadoDto){

        List<Integer> convidadosIds = mesaConvidadoDto.stream()
                .map(MesaConvidadoRequestDto::getConvidadoId)
                .toList();
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
                    .where(ConvidadoFilter.hasCasamentoId(casamentoId))
                    .and(ConvidadoFilter.hasNome(nome));
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
        List<Convidado> convidadosWithMesa = convidadosWithMesa(mesas);

        if (mesas.isEmpty() || convidadosWithMesa.isEmpty()){
            return allConvidados;
        }

        return allConvidados.stream().filter(convidado -> !convidadosWithMesa.contains(convidado))
                .toList();
    }

    public List<Convidado> convidadosWithMesa(List<Mesa> mesas){
        return mesas.stream().map(Mesa::getConvidados).findFirst().orElse(new ArrayList<>());
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
            log.error("CONVIDADO: convidado de id {}, já cadastrado para esse convite", convidado.getId());
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
