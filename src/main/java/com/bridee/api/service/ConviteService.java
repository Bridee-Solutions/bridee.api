package com.bridee.api.service;

import com.bridee.api.dto.request.ConviteMessageDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.entity.enums.TipoConvidado;
import com.bridee.api.exception.BadRequestEntityException;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.request.ConviteMessageMapper;
import com.bridee.api.pattern.observer.dto.ConviteTopicDto;
import com.bridee.api.pattern.observer.impl.ConviteTopic;
import com.bridee.api.projection.orcamento.RelatorioProjection;
import com.bridee.api.repository.ConviteRepository;
import com.bridee.api.repository.specification.ConviteFilter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConviteService {

    private final ConviteRepository repository;
    private final ConvidadoService convidadoService;
    private final ConviteTopic conviteTopic;
    private final CasamentoService casamentoService;
    private final ConviteMessageMapper conviteMessageMapper;

    public List<Convite> findAllByCasamentoId(Map<String, Object> filter, Integer casamentoId){
        //TODO: validar existência do casamento.
        filter.put("casamentoId", casamentoId);

        ConviteFilter spec = new ConviteFilter();
        spec.fillSpecification(filter);

        return repository.findAll(spec);
    }

    public void sendConvidadosConvites(ConviteMessageDto conviteMessageDto){

        Casamento casamento = casamentoService.findById(conviteMessageDto.getCasamentoId());
        Casal casal = casamento.getCasal();
        Convite convite = findById(conviteMessageDto.getConviteId());
        List<Convidado> convidados = convidadoService.findAllByIds(conviteMessageDto.getConvidadosIds());

        validateCasalCasamento(casamento, casal);
        validateConviteCasamento(casamento, convite);
        validateConvidadosConvite(convite, convidados);

        List<ConviteTopicDto> convidadosTopics = convidados.stream()
                .map(convidado -> conviteMessageMapper.toTopicDto(convite, casal, convidado)).toList();
        conviteTopic.postMessage(convidadosTopics);
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

        convite.setPin(generatePinCode(casamentoId));
        convite = repository.save(convite);
        convidadoService.saveAll(convite.getConvidados(), convite);
        return convite;
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

    public Convite findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Convite não encontrado!"));
    }

    public RelatorioProjection gerarRelatorioCasamento(Integer casamentoId) {
        RelatorioProjection projection = repository.gerarRelatorio(casamentoId);
        if (Objects.isNull(projection)){
            throw new ResourceNotFoundException("Não há dados para gerar o relátorio do casal");
        }
        return projection;
    }

    public String generatePinCode(Integer casamentoId) {
        String pinCode = RandomStringUtils.randomNumeric(6);
        List<String> allCasamentoPin = repository.findAllPinByCasamentoId(casamentoId);
        while(allCasamentoPin.contains(pinCode)){
            pinCode = RandomStringUtils.randomNumeric(6);;
        }
        return pinCode;
    }

    private void validateCasalCasamento(Casamento casamento, Casal casal){
        if(!casamento.getCasal().equals(casal)){
            throw new BadRequestEntityException("Casal não associado ao casamento especificado");
        }
    }

    private void validateConviteCasamento(Casamento casamento, Convite convite){
        if (!convite.getCasamento().equals(casamento)) {
            throw new BadRequestEntityException("Convite não pertence ao casamento especificado");
        }
    }

    private void validateConvidadosConvite(Convite convite, List<Convidado> convidados){
        HashSet<Convidado> convidadosConvite = new HashSet<>(convite.getConvidados());

        if (convidados.isEmpty() || convidadosConvite.isEmpty()){
            throw new ResourceNotFoundException("Não foi encontrado nenhum dos convidados informados para o convite de id %d"
                    .formatted(convite.getId()));
        }

        if (!convidadosConvite.containsAll(convidados)){
            throw new BadRequestEntityException("Um ou mais convidados não associados ao convite.");
        }

    }

}
