package com.bridee.api.controller.impl;

import com.bridee.api.dto.request.UpdateCasalMessageDto;
import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.mapper.response.AssessorResponseMapper;
import com.bridee.api.service.CasamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/casamentos")
@RequiredArgsConstructor
public class CasamentoControllerImpl {

    private final CasamentoService casamentoService;
    private final AssessorResponseMapper responseMapper;

    @GetMapping("/{casamentoId}/orcamento")
    public ResponseEntity<BigDecimal> calculateCasamentoOrcamento(@PathVariable Integer casamentoId){
        return ResponseEntity.ok(casamentoService.calculateOrcamento(casamentoId));
    }

    @PutMapping("/{casamentoId}/assessor/{assessorId}")
    public ResponseEntity<AssessorResponseDto> vinculateAssessorToWedding(@PathVariable Integer casamentoId,
                                                                          @PathVariable Integer assessorId){
        Assessor assessor = casamentoService.vinculateAssessorToWedding(casamentoId, assessorId);
        AssessorResponseDto responseDto = responseMapper.toDomain(assessor);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{casamentoId}/assessor/{assessorId}/aceitar-proposta")
    public ResponseEntity<Void> acceptWedding(@PathVariable Integer casamentoId,
                                            @PathVariable Integer assessorId){
        casamentoService.acceptWedding(casamentoId, assessorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{casamentoId}/assessor/{assessorId}/recusar-proposta")
    public ResponseEntity<Void> denyWedding(@PathVariable Integer casamentoId,
                                            @PathVariable Integer assessorId){
        casamentoService.denyWedding(casamentoId, assessorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{casamentoId}/assessor/{assessorId}/remover-proposta")
    public ResponseEntity<Void> removeWeddingAdvise(@PathVariable Integer casamentoId,
                                            @PathVariable Integer assessorId){
        casamentoService.removeWeddingAdvise(casamentoId, assessorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{casamentoId}")
    public ResponseEntity<Void> updateCasamentoMessage(@PathVariable Integer casamentoId,
                                                       @RequestBody @Valid UpdateCasalMessageDto request){
        casamentoService.updateMessage(casamentoId, request.getMessage());
        return ResponseEntity.ok().build();
    }

}
