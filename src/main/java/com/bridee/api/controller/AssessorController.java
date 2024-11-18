package com.bridee.api.controller;

import com.bridee.api.dto.request.AssessorRequestDto;
import com.bridee.api.dto.request.SolicitacaoOrcamentoRequestDto;
import com.bridee.api.dto.request.ValidateAssessorFieldsRequestDto;
import com.bridee.api.dto.request.externo.AssessorExternoRequestDto;
import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.dto.response.ErrorResponseDto;
import com.bridee.api.dto.response.ValidateAssessorFieldsResponseDto;
import com.bridee.api.dto.response.externo.AssessorExternoResponseDto;
import com.bridee.api.projection.associado.AssociadoGeralResponseDto;
import com.bridee.api.projection.associado.AssociadoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Controller de assessor")
public interface AssessorController {

    @Operation(summary = "Encontra todos os assessores cadastrados",
            description = "Encontra todos os assessores cadastrados de forma paginada")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de assessores")
    )
    ResponseEntity<Page<AssessorResponseDto>> findAll(Pageable pageable);

    @Operation(summary = "Encontra um assessor específico",
            description = "Encontra um assessor específico pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de assessores"),
            @ApiResponse(responseCode = "404", description = "Assessor não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
        }
    )
    ResponseEntity<AssessorResponseDto> findById(@PathVariable Integer id);

    @Operation(summary = "Encontra uma lista de detalhes dos assessores.",
            description = "Retorna os detalhes de todos os assessores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de assessores"),
    }
    )
    ResponseEntity<Page<AssociadoResponseDto>> findAssessoresDetails(Pageable pageable);

    @Operation(summary = "Retorna todas as informações sobre um assessor e seus serviços",
            description = "Retorna todas as informações sobre um assessor e seus serviços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de assessores")
        }
    )
    ResponseEntity<AssociadoGeralResponseDto> findAssessorInformation(@PathVariable Integer id);

    @Operation(summary = "Envio de e-mail de orçamento",
            description = "Envia um e-mail de solicitação de orcamento de um casal para um assessor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso")
        }
    )
    ResponseEntity<Void> sendOrcamentoEmail(@RequestBody @Valid SolicitacaoOrcamentoRequestDto requestDto);

    @Operation(summary = "Cria um assessor",
            description = "Cria um assessor a partir do fluxo da nossa aplicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria um assessor"),
            @ApiResponse(responseCode = "409", description = "Email ou Cnpj do assessor já cadastrado",
                        content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Role de assessor não encontrado",
                        content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
        }
    )
    ResponseEntity<AssessorResponseDto> save(@RequestBody @Valid AssessorRequestDto requestDto);

    @Operation(summary = "Cria um assessor externo",
            description = "Cria um assessor externo (de uma aplicação externa)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria um assessor"),
            @ApiResponse(responseCode = "409", description = "Email ou Cnpj do assessor já cadastrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Role de assessor não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    }
    )
    ResponseEntity<AssessorExternoResponseDto> saveExternal(@RequestBody @Valid AssessorExternoRequestDto requestDto);

    @Operation(summary = "Validação informações do usuário",
            description = "Validar informações do assessor durante o cadastro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um objeto com o resultado das validações")
        }
    )
    ResponseEntity<ValidateAssessorFieldsResponseDto> validateFields(@RequestBody @Valid ValidateAssessorFieldsRequestDto requestDto);

    @Operation(summary = "Atualiza as informações de um assessor",
            description = "Atualiza as informações de um assessor pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informações atualizadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Assessor não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
        }
    )
    ResponseEntity<AssessorResponseDto> update(@RequestBody @Valid AssessorRequestDto requestDto, @PathVariable Integer id);

    @Operation(summary = "Deleta as informações de um assessor",
            description = "Deleta as informações de um assessor pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Assessor não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    }
    )
    ResponseEntity<Void> delete(@PathVariable Integer id);

}

