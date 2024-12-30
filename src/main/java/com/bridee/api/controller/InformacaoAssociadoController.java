package com.bridee.api.controller;

import com.bridee.api.dto.request.InformacaoAssociadoDto;
import com.bridee.api.dto.request.InformacaoAssociadoPerfilDto;
import com.bridee.api.dto.response.InformacaoAssociadoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Informação do Associado", description = "Endpoints para a gestão das informações dos associados")
public interface InformacaoAssociadoController {

    @Operation(
        summary = "Salvar perfil do assessor",
        description = "Salva as informações do perfil de um assessor, incluindo as imagens associadas ao perfil."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informações do assessor salvas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos na requisição"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao salvar as informações do assessor")
    })
    ResponseEntity<InformacaoAssociadoResponseDto> save(
        @PathVariable Integer assessorId,
        @RequestParam("json") String informacaoAssociadoDto,
        @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal,
        @RequestPart("imagemSecundaria") MultipartFile imagemSecundaria,
        @RequestPart("imagemTerciaria") MultipartFile imagemTerciaria,
        @RequestPart("imagemQuaternaria") MultipartFile imagemQuaternaria,
        @RequestPart("imagemQuinaria") MultipartFile imagemQuinaria
    );

    @Operation(
        summary = "Salvar informações do fornecedor",
        description = "Salva as informações do perfil de um fornecedor, incluindo as imagens associadas ao perfil."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informações do fornecedor salvas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos na requisição"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao salvar as informações do fornecedor")
    })
    ResponseEntity<InformacaoAssociadoResponseDto> saveFornecedorInformation(
        @PathVariable Integer fornecedorId,
        @RequestParam("json") String informacaoAssociadoDto,
        @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal,
        @RequestPart("imagemSecundaria") MultipartFile imagemSecundaria,
        @RequestPart("imagemTerciaria") MultipartFile imagemTerciaria,
        @RequestPart("imagemQuaternaria") MultipartFile imagemQuaternaria,
        @RequestPart("imagemQuinaria") MultipartFile imagemQuinaria
    );

    @Operation(
        summary = "Buscar informações do assessor pelo ID",
        description = "Retorna todas as informações associadas ao perfil do assessor, incluindo imagens, tipos de casamento, cerimônias e formas de pagamento."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informações do assessor retornadas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Assessor não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao buscar as informações do assessor")
    })
    ResponseEntity<InformacaoAssociadoResponseDto> findByAssessorId(@PathVariable Integer id);
}
