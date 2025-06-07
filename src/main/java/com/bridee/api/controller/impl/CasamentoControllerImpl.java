package com.bridee.api.controller.impl;

import com.bridee.api.aop.AdvisorIdentifier;
import com.bridee.api.aop.CoupleIdentifier;
import com.bridee.api.aop.WeddingIdentifier;
import com.bridee.api.dto.request.UpdateCasalMessageDto;
import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.dto.response.CasamentoResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casamento;
import com.bridee.api.mapper.response.AssessorResponseMapper;
import com.bridee.api.mapper.response.CasamentoResponseMapper;
import com.bridee.api.service.CasamentoService;
import com.bridee.api.utils.PatchHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.JsonMergePatch;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/casamentos")
@RequiredArgsConstructor
public class CasamentoControllerImpl {

    private final CasamentoService casamentoService;
    private final AssessorResponseMapper assessorResponseMapper;
    private final CasamentoResponseMapper responseMapper;
    private final PatchHelper patchHelper;

    @GetMapping("/orcamento")
    public ResponseEntity<BigDecimal> calculateCasamentoOrcamento(@CoupleIdentifier Integer casalId){
        log.info("CASAMENTO: calculando o orçamento do casal de id: {}", casalId);
        return ResponseEntity.ok(casamentoService.calculateOrcamento(casalId));
    }

    @PatchMapping(consumes = "application/merge-patch+json")
    public ResponseEntity<CasamentoResponseDto> updateCasamento(@RequestBody JsonMergePatch jsonMergePatch,
                                                                @WeddingIdentifier Integer casamentoId){
        log.info("CASAMENTO: atualizando informações do casamento de id: {}", casamentoId);
        Casamento casamento = patchHelper.mergePatch(jsonMergePatch, new Casamento(), Casamento.class);
        casamento = casamentoService.updateCasamento(casamento, casamentoId);
        CasamentoResponseDto response = responseMapper.toDomain(casamento);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/assessor/{assessorId}/vincular")
    public ResponseEntity<AssessorResponseDto> vinculateAssessorToWedding(@WeddingIdentifier Integer casamentoId,
                                                                          @PathVariable Integer assessorId){
        log.info("CASAMENTO: vinculando assessor de id: {} com o casamento de id: {}", assessorId, casamentoId);
        Assessor assessor = casamentoService.vinculateAssessorToWedding(casamentoId, assessorId);
        AssessorResponseDto responseDto = assessorResponseMapper.toDomain(assessor);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{casamentoId}/aceitar-proposta")
    public ResponseEntity<Void> acceptWedding(@PathVariable Integer casamentoId,
                                              @AdvisorIdentifier Integer assessorId){
        log.info("CASAMENTO: aceitando a proposta do casamento de id: {}, para o assessor de id: {}",
                casamentoId, assessorId);
        casamentoService.acceptWedding(casamentoId, assessorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{casamentoId}/recusar-proposta")
    public ResponseEntity<Void> denyWedding(@PathVariable Integer casamentoId,
                                            @AdvisorIdentifier Integer assessorId){
        log.info("CASAMENTO: recusando a proposta do casamento de id: {}, para o assessor de id: {}",
                casamentoId, assessorId);
        casamentoService.denyWedding(casamentoId, assessorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{casamentoId}/remover-proposta")
    public ResponseEntity<Void> removeWeddingAdvise(@PathVariable Integer casamentoId,
                                            @AdvisorIdentifier Integer assessorId){
        log.info("CASAMENTO: removendo a proposta do casamento de id: {}, para o assessor de id: {}",
                casamentoId, assessorId);
        casamentoService.removeWeddingAdvise(casamentoId, assessorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/mensagem")
    public ResponseEntity<Void> updateCasamentoMessage(@WeddingIdentifier Integer casamentoId,
                                                       @RequestBody @Valid UpdateCasalMessageDto request){
        log.info("CASAMENTO: atualizando mensagem do casamento: {}", casamentoId);
        casamentoService.updateMessage(casamentoId, request.getMessage());
        return ResponseEntity.ok().build();
    }

}
