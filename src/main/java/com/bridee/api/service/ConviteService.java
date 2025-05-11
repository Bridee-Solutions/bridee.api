package com.bridee.api.service;

import com.bridee.api.dto.request.ConviteMessageDto;
import com.bridee.api.dto.response.CategoriaConvidadoResumoDto;
import com.bridee.api.dto.response.ConviteResumoResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.CategoriaConvidado;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.entity.enums.TipoConvidado;
import com.bridee.api.exception.BadRequestEntityException;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.request.ConviteMessageMapper;
import com.bridee.api.mapper.response.CategoriaConvidadoResponseMapper;
import com.bridee.api.mapper.response.ConviteResponseMapper;
import com.bridee.api.pattern.observer.dto.ConviteTopicDto;
import com.bridee.api.pattern.observer.impl.ConviteTopic;
import com.bridee.api.repository.projection.convite.ConviteResumoProjection;
import com.bridee.api.repository.projection.orcamento.RelatorioProjection;
import com.bridee.api.repository.ConvidadoRepository;
import com.bridee.api.repository.ConviteRepository;
import com.bridee.api.repository.specification.ConviteFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConviteService {

    private final ConviteRepository repository;
    private final ConvidadoService convidadoService;
    private final ConvidadoRepository convidadoRepository;
    private final ConviteTopic conviteTopic;
    private final CasamentoService casamentoService;
    private final ConviteMessageMapper conviteMessageMapper;
    private final CategoriaConvidadoResponseMapper categoriaConvidadoMapper;
    private final ConviteResponseMapper conviteResponseMapper;
    private final CategoriaConvidadoService categoriaConvidadoService;

    public List<Convite> findAllByCasamentoId(Map<String, Object> filter, Integer casamentoId){
        casamentoService.existsById(casamentoId);
        filter.put("casamentoId", casamentoId);
        log.debug("CONVITE: buscando os convites do casamento {} filtrados por {}", casamentoId, filter.keySet());
        ConviteFilter spec = new ConviteFilter();
        spec.fillSpecification(filter);

        return repository.findAll(spec);
    }

    public void sendConvidadosConvites(ConviteMessageDto conviteMessageDto){
        Casamento casamento = casamentoService.findById(conviteMessageDto.getCasamentoId());
        Casal casal = casamento.getCasal();
        Convite convite = findById(conviteMessageDto.getConviteId());
        List<Convidado> convidados = convidadoService.findAllByIds(conviteMessageDto.getConvidadosIds());

        validateConviteCasamento(casamento, convite);
        validateConvidadosConvite(convite, convidados);

        List<ConviteTopicDto> convidadosTopics = convidados.stream()
                .map(convidado -> conviteMessageMapper.toTopicDto(convite, casal, convidado)).toList();
        conviteTopic.postMessage(convidadosTopics);
        updateConvidadosStatus(convite, convidados);
    }

    private void updateConvidadosStatus(Convite convite, List<Convidado> confirmatedGuests){
        List<Convidado> allInvites = convidadoService.findAllByConviteId(convite.getId());
        allInvites.stream()
                .filter(invite -> !confirmatedGuests.contains(invite))
                .forEach(invite -> invite.setStatus("RECUSADO"));
        convidadoService.saveAll(allInvites);
    }

    @Transactional
    public Convite save(Convite convite, Integer casamentoId, String telefoneTitular){
        if (repository.existsByNomeAndCasamentoId(convite.getNome(), casamentoId)){
            log.error("CONVITE: convite já cadastrado para esse convite");
            throw new ResourceAlreadyExists("Convite já cadastrado para esse casamento.");
        }

        definirConviteTitular(convite, telefoneTitular, casamentoId);

        convite.setPin(generatePinCode(casamentoId));
        List<Convidado> savedGuests = convidadoService.saveAll(convite.getConvidados());
        convite.setConvidados(savedGuests);
        convite = repository.save(convite);
        convidadoService.saveAllInvites(convite.getConvidados(), convite);
        return convite;
    }

    private void definirConviteTitular(Convite convite, String telefoneTitular, Integer casamentoId) {
        Optional<Convidado> optionalConvidado = convite.getConvidados().stream()
                .filter(convidado -> convidado.getTelefone().equals(telefoneTitular))
                .findFirst();

        convite.getConvidados().forEach(convidado -> convidado.setTipo(TipoConvidado.NAO_TITULAR));
        if (optionalConvidado.isEmpty()){
            log.error("CONVITE: titular não cadastrado para o convite do casamento {}", casamentoId);
            throw new ResourceNotFoundException("Titular não foi adicionado para o convite!");
        }
        optionalConvidado.get().setTipo(TipoConvidado.TITULAR);
    }

    @Transactional
    public Convite update(Convite convite, String telefoneTitular,Integer id){
        if (!repository.existsById(id)){
            log.error("CONVITE: convite não encontrado com id {}", id);
            throw new ResourceNotFoundException("Convite não encontrado!");
        }
        updateTitularTelefone(id, telefoneTitular);
        convite.setId(id);
        return repository.save(convite);
    }

    private void updateTitularTelefone(Integer conviteId, String telefoneTitular) {
        Convidado titular = repository.findTitularConvite(conviteId, TipoConvidado.TITULAR);
        titular.setTelefone(telefoneTitular);
        convidadoRepository.save(titular);
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
            log.error("CONVITE: Não há dados para gerar o relátorio do casal");
            throw new ResourceNotFoundException("Não há dados para gerar o relátorio do casal");
        }
        return projection;
    }

    public ConviteResumoResponseDto inviteResume(Integer casamentoId){
        ConviteResumoProjection conviteResumoProjection = repository.resumoCasamentoInvites(casamentoId);
        List<CategoriaConvidado> categoriaConvidados = categoriaConvidadoService.findAll();
        List<CategoriaConvidadoResumoDto> categoriasResumo = generateCategoriaResumo(categoriaConvidados, casamentoId);
        categoriasResumo = categoriasResumo.stream().filter(Objects::nonNull).toList();
        return conviteResponseMapper.fromProjection(conviteResumoProjection, categoriasResumo);
    }

    private List<CategoriaConvidadoResumoDto> generateCategoriaResumo(List<CategoriaConvidado> categoriaConvidado, Integer casamentoId){
        return categoriaConvidado.stream()
                .map((categoria) -> {
                    var projection = repository.resumoCategoriaInvite(casamentoId, categoria.getNome());
                    return categoriaConvidadoMapper.fromProjection(projection, categoria.getNome());
                }).toList();
    }

    public String generatePinCode(Integer casamentoId) {
        String pinCode = RandomStringUtils.randomNumeric(6);
        List<String> allCasamentoPin = repository.findAllPinByCasamentoId(casamentoId);
        while(allCasamentoPin.contains(pinCode)){
            pinCode = RandomStringUtils.randomNumeric(6);
        }
        return pinCode;
    }

    private void validateConviteCasamento(Casamento casamento, Convite convite){
        if (!convite.getCasamento().equals(casamento)) {
            log.error("CONVITE: convite de id {}, não pertence ao casamento {}", convite.getId(), casamento.getId());
            throw new BadRequestEntityException("Convite não pertence ao casamento especificado");
        }
    }

    private void validateConvidadosConvite(Convite convite, List<Convidado> convidados){
        HashSet<Convidado> convidadosConvite = new HashSet<>(convite.getConvidados());

        if (convidados.isEmpty() || convidadosConvite.isEmpty()){
            log.error("CONVITE: não foi encontrado nenhum dos convidados informados no convite de id {}", convite.getId());
            throw new ResourceNotFoundException("Não foi encontrado nenhum dos convidados informados para o convite de id %d"
                    .formatted(convite.getId()));
        }

        if (!convidadosConvite.containsAll(convidados)){
            log.error("CONVITE: Um ou mais convidados não associados ao convite de id {}", convite.getId());
            throw new BadRequestEntityException("Um ou mais convidados não associados ao convite.");
        }

    }

    public void deleteAllWeddingInvites(Integer casamentoId) {
        List<Convite> convites = repository.findAllByCasamentoId(casamentoId);
        repository.deleteAll(convites);
    }
}
