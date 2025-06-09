package com.bridee.api.controller.impl;

import com.bridee.api.aop.WeddingIdentifier;
import com.bridee.api.controller.ConviteController;
import com.bridee.api.dto.request.ConviteRequestDto;
import com.bridee.api.dto.response.ConviteResponseDto;
import com.bridee.api.dto.response.ConviteResumoResponseDto;
import com.bridee.api.dto.response.ConvitesResponseDto;
import com.bridee.api.entity.Convite;
import com.bridee.api.mapper.request.ConviteRequestMapper;
import com.bridee.api.mapper.response.ConviteResponseMapper;
import com.bridee.api.repository.projection.orcamento.RelatorioProjection;
import com.bridee.api.service.ConviteService;
import com.bridee.api.utils.PageUtils;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/convites")
@RequiredArgsConstructor
public class ConviteControllerImpl implements ConviteController {

    private final ConviteService conviteService;
    private final ConviteRequestMapper requestMapper;
    private final ConviteResponseMapper responseMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ConvitesResponseDto> findById(@PathVariable Integer id){
        log.info("CONVITE: buscando convite de id: {}", id);
        Convite convite = conviteService.findById(id);
        return ResponseEntity.ok(responseMapper.toDomain(convite));
    }

    @GetMapping
    public ResponseEntity<Page<ConvitesResponseDto>> findAllInvites(@RequestParam Map<String, Object> filter,
                                                                    @WeddingIdentifier Integer casamentoId){
        log.info("CONVITE: buscando todos os convites filtrados por {}", filter.keySet());
        List<Convite> convites = conviteService.findAllByCasamentoId(filter, casamentoId);
        Pageable pageable = PageUtils.buildPageable(filter);
        return ResponseEntity.ok(responseMapper.toDomainPage(convites, pageable));
    }

    @GetMapping("/pin/{pin}")
    public ResponseEntity<ConvitesResponseDto> findByPin(@PathVariable Integer pin){
        Convite convite = conviteService.findByPin(pin);
        ConvitesResponseDto responseDto = responseMapper.toDomain(convite);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/relatorio")
    public ResponseEntity<RelatorioProjection> findRelatorioConviteCasamento(@WeddingIdentifier Integer casamentoId){
        log.info("CONVITE: gerando relatório de convites do casamento: {}", casamentoId);
        return ResponseEntity.ok(conviteService.gerarRelatorioCasamento(casamentoId));
    }

    @GetMapping("/resumo")
    public ResponseEntity<ConviteResumoResponseDto> resumo(@WeddingIdentifier Integer casamentoId){
        log.info("CONVITE: gerando resumo de convites do casamento {}", casamentoId);
        return ResponseEntity.ok(conviteService.inviteResume(casamentoId));
    }

    @PostMapping
    public ResponseEntity<ConvitesResponseDto> save(@WeddingIdentifier Integer casamentoId,
                                                    @RequestBody @Valid ConviteRequestDto requestDto){
        log.info("CONVITE: salvando convite de titular {}, para o casamento {}",
                requestDto.getTelefoneTitular(), casamentoId);
        Convite convite = requestMapper.toEntity(requestDto);
        convite = conviteService.save(convite, casamentoId, requestDto.getTelefoneTitular());
        ConvitesResponseDto convitesResponseDto = responseMapper.toDomain(convite);
        return ResponseEntity.created(UriUtils.uriBuilder(convitesResponseDto.getId())).body(convitesResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConviteResponseDto> update(@RequestBody @Valid ConviteRequestDto requestDto,
                                                     @PathVariable Integer id){
        log.info("CONVITE: atualizando convite de id {}", id);
        Convite convite = requestMapper.toEntity(requestDto);
        convite = conviteService.update(convite, requestDto.getTelefoneTitular(), id);
        ConviteResponseDto responseDto = responseMapper.toConviteResponse(convite);
        responseDto.setTelefoneTitular(requestDto.getTelefoneTitular());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        log.info("CONVITE: deletando convite de id {}", id);
        conviteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll(@WeddingIdentifier Integer casamentoId){
        log.info("CONVITE: deletando todos os convites do casamento {}", casamentoId);
        conviteService.deleteAllWeddingInvites(casamentoId);
        return ResponseEntity.noContent().build();
    }

}
