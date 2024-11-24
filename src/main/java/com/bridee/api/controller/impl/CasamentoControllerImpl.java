package com.bridee.api.controller.impl;

import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.mapper.response.AssessorResponseMapper;
import com.bridee.api.service.CasamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/casamentos")
@RequiredArgsConstructor
public class CasamentoControllerImpl {

    private final CasamentoService casamentoService;
    private final AssessorResponseMapper responseMapper;

    @PutMapping("/{casamentoId}/assessor/{assessorId}")
    public ResponseEntity<AssessorResponseDto> vinculateAssessorToWedding(@PathVariable Integer casamentoId,
                                                                          @PathVariable Integer assessorId){
        Assessor assessor = casamentoService.vinculateAssessorToWedding(casamentoId, assessorId);
        AssessorResponseDto responseDto = responseMapper.toDomain(assessor);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{casamentoId}")
    public ResponseEntity<Void> updateCasamentoMessage(@PathVariable Integer casamentoId,
                                                       @RequestBody String message){
        casamentoService.updateMessage(casamentoId, message);
        return ResponseEntity.ok().build();
    }

}
