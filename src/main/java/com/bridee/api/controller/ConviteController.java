package com.bridee.api.controller;

import com.bridee.api.dto.request.ConviteRequestDto;
import com.bridee.api.dto.response.ConviteResponseDto;
import com.bridee.api.dto.response.ConvitesResponseDto;
import com.bridee.api.entity.Convite;
import com.bridee.api.mapper.request.ConviteRequestMapper;
import com.bridee.api.mapper.response.ConviteResponseMapper;
import com.bridee.api.projection.orcamento.RelatorioProjection;
import com.bridee.api.service.ConviteService;
import com.bridee.api.utils.PageUtils;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/convites")
@RequiredArgsConstructor
public class ConviteController {

    private final ConviteService conviteService;
    private final ConviteRequestMapper requestMapper;
    private final ConviteResponseMapper responseMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ConvitesResponseDto> findById(@PathVariable Integer id){
        Convite convite = conviteService.findById(id);
        return ResponseEntity.ok(responseMapper.toDomain(convite));
    }

    @GetMapping("/casamento/{casamentoId}")
    public ResponseEntity<Page<ConvitesResponseDto>> findAllInvites(@RequestParam Map<String, Object> filter, @PathVariable Integer casamentoId){
        List<Convite> convites = conviteService.findAllByCasamentoId(filter, casamentoId);
        Pageable pageable = PageUtils.buildPageable(filter);
        return ResponseEntity.ok(responseMapper.toDomainPage(convites, pageable));
    }

    @GetMapping("/casamento/{casamentoId}/relatorio")
    public ResponseEntity<RelatorioProjection> findRelatorioConviteCasamento(@PathVariable Integer casamentoId){
        return ResponseEntity.ok(conviteService.gerarRelatorioCasamento(casamentoId));
    }

    @PostMapping
    public ResponseEntity<ConvitesResponseDto> save(@RequestBody @Valid ConviteRequestDto requestDto){
        Convite convite = requestMapper.toEntity(requestDto);
        convite = conviteService.save(convite, requestDto.getCasamentoId(), requestDto.getTelefoneTitular());
        ConvitesResponseDto convitesResponseDto = responseMapper.toDomain(convite);
        return ResponseEntity.created(UriUtils.uriBuilder(convitesResponseDto.getId())).body(convitesResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConviteResponseDto> update(@RequestBody @Valid ConviteRequestDto requestDto,
                                                     @PathVariable Integer id){
        Convite convite = requestMapper.toEntity(requestDto);
        convite = conviteService.update(convite, id);
        return ResponseEntity.ok(responseMapper.toConviteResponse(convite));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        conviteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
