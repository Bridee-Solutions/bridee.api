package com.bridee.api.controller.impl;

import com.bridee.api.controller.AssessorController;
import com.bridee.api.dto.request.AssessorRequestDto;
import com.bridee.api.dto.request.AssociadoPrecoRequestDto;
import com.bridee.api.dto.request.SolicitacaoOrcamentoRequestDto;
import com.bridee.api.dto.request.ValidateAssessorFieldsRequestDto;
import com.bridee.api.dto.request.externo.AssessorExternoRequestDto;
import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.dto.response.CasamentoResponseDto;
import com.bridee.api.dto.response.ValidateAssessorFieldsResponseDto;
import com.bridee.api.dto.response.externo.AssessorExternoResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casamento;
import com.bridee.api.mapper.request.AssessorRequestMapper;
import com.bridee.api.mapper.request.externo.AssessorExternoRequestMapper;
import com.bridee.api.mapper.response.AssessorResponseMapper;
import com.bridee.api.mapper.response.CasamentoResponseMapper;
import com.bridee.api.mapper.response.externo.AssessorExternoResponseMapper;
import com.bridee.api.projection.associado.AssociadoGeralResponseDto;
import com.bridee.api.projection.associado.AssociadoResponseDto;
import com.bridee.api.service.AssessorService;
import com.bridee.api.service.PedidoAssessoriaService;
import com.bridee.api.service.EmailService;
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

@RestController
@RequestMapping("/assessores")
@RequiredArgsConstructor
public class AssessorControllerImpl implements AssessorController {

    private final AssessorService service;
    private final EmailService emailService;
    private final PedidoAssessoriaService pedidoAssessoriaService;
    private final CasamentoResponseMapper casamentoResponseMapper;
    private final AssessorRequestMapper requestMapper;
    private final AssessorResponseMapper responseMapper;
    private final AssessorExternoRequestMapper externoRequestMapper;
    private final AssessorExternoResponseMapper externoResponseMapper;

    @GetMapping
    public ResponseEntity<Page<AssessorResponseDto>> findAll(Pageable pageable, String nome){
        return ResponseEntity.ok(responseMapper.toDomain(service.findAllByNome(nome, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessorResponseDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok(responseMapper.toDomain(service.findById(id)));
    }

    @GetMapping("/details")
    public ResponseEntity<Page<AssociadoResponseDto>> findAssessoresDetails(Pageable pageable){
        return ResponseEntity.ok(service.findAssessoresDetails(pageable));
    }

    @GetMapping("/information/{id}")
    public ResponseEntity<AssociadoGeralResponseDto> findAssessorInformation(@PathVariable Integer id){
        return ResponseEntity.ok(service.findAssessorInformation(id));
    }

    @GetMapping("/{assessorId}/casais/pendentes")
    public ResponseEntity<Page<CasamentoResponseDto>> findAllCasamentosPendentes(@PathVariable Integer assessorId,
                                                                             Pageable pageable){
        Page<Casamento> casais = pedidoAssessoriaService.findCasamentosPendenteByAssessorId(assessorId, pageable);
        Page<CasamentoResponseDto> response = casamentoResponseMapper.toDomain(casais);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{assessorId}/casamentos/assessorados")
    public ResponseEntity<List<CasamentoResponseDto>> findAllCasamentosAssessorados(@RequestParam Integer mes,
                                                                                    @RequestParam Integer ano,
                                                                                    @PathVariable Integer assessorId){
        List<Casamento> casamentos = pedidoAssessoriaService
                .findCasamentosAssessorados(assessorId, mes, ano);
        List<CasamentoResponseDto> responseDto = casamentoResponseMapper.toDomain(casamentos);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/solicitar-orcamento/{assessorId}")
    public ResponseEntity<Void> sendOrcamentoEmail(@PathVariable Integer assessorId,
                                                   @RequestBody @Valid SolicitacaoOrcamentoRequestDto requestDto){
        emailService.sendOrcamentoEmail(requestDto, assessorId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AssessorResponseDto> save(@RequestBody @Valid AssessorRequestDto requestDto){
        Assessor assessor = service.save(requestMapper.toEntity(requestDto));
        AssessorResponseDto responseDto = responseMapper.toDomain(assessor);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PostMapping("/externo")
    public ResponseEntity<AssessorExternoResponseDto> saveExternal(@RequestBody @Valid AssessorExternoRequestDto requestDto){
        Assessor assessor = service.saveExternal(externoRequestMapper.toEntity(requestDto));
        AssessorExternoResponseDto responseDto = externoResponseMapper.toDomain(assessor);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);

    }

    @PostMapping("/validate-fields")
    public ResponseEntity<ValidateAssessorFieldsResponseDto> validateFields(@RequestBody @Valid ValidateAssessorFieldsRequestDto requestDto){
        ValidateAssessorFieldsResponseDto responseDto = service.validateAssessorFields(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessorResponseDto> update(@RequestBody @Valid AssessorRequestDto requestDto, @PathVariable Integer id){
        Assessor assessor = service.update(requestMapper.toEntity(requestDto), id);
        return ResponseEntity.ok(responseMapper.toDomain(assessor));
    }

    @PutMapping("/{assessorId}/casamento/{casamentoId}")
    public ResponseEntity<Void> updateAssessorPreco(@PathVariable Integer assessorId,
                                                    @PathVariable Integer casamentoId,
                                                    @RequestBody @Valid AssociadoPrecoRequestDto requestDto
                                                    ){
        pedidoAssessoriaService.updatePrecoCasamentoAssessor(assessorId, casamentoId, requestDto.getPreco());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
