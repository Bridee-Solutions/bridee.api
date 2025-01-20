package com.bridee.api.controller.impl;

import com.bridee.api.aop.AdvisorIdentifier;
import com.bridee.api.aop.WeddingIdentifier;
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
import com.bridee.api.utils.PatchHelper;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonMergePatch;
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
    private final PatchHelper patchHelper;

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

    @GetMapping("/casais/pendentes")
    public ResponseEntity<Page<CasamentoResponseDto>> findAllCasamentosPendentes(@AdvisorIdentifier Integer assessorId,
                                                                             Pageable pageable){
        Page<Casamento> casais = pedidoAssessoriaService.findCasamentosPendenteByAssessorId(assessorId, pageable);
        Page<CasamentoResponseDto> response = casamentoResponseMapper.toDomain(casais);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/casamentos/assessorados")
    public ResponseEntity<List<CasamentoResponseDto>> findAllCasamentosAssessorados(@RequestParam Integer ano,
                                                                                    @AdvisorIdentifier Integer assessorId){
        List<Casamento> casamentos = pedidoAssessoriaService
                .findCasamentosAssessorados(assessorId, ano);
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

    @PatchMapping("/{id}")
    public ResponseEntity<AssessorResponseDto> update(@RequestBody JsonMergePatch jsonMergePatch, @PathVariable Integer id){
        Assessor assessor = patchHelper.mergePatch(jsonMergePatch, new Assessor(), Assessor.class);
        assessor = service.update(assessor, id);
        return ResponseEntity.ok(responseMapper.toDomain(assessor));
    }

    @PutMapping("/{assessorId}/preco")
    public ResponseEntity<Void> updateAssessorPreco(@PathVariable Integer assessorId,
                                                    @WeddingIdentifier Integer casamentoId,
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
