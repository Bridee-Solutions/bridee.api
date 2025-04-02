package com.bridee.api.controller.impl;

import com.bridee.api.controller.ConvidadoController;
import com.bridee.api.dto.request.ConvidadoRequestDto;
import com.bridee.api.dto.request.MesaConvidadoRequestDto;
import com.bridee.api.dto.response.ConvidadoResponseDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.mapper.request.ConvidadoRequestMapper;
import com.bridee.api.mapper.response.ConvidadoResponseMapper;
import com.bridee.api.service.ConvidadoService;
import com.bridee.api.service.MesaService;
import com.bridee.api.utils.PatchHelper;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonMergePatch;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/convidados")
@RequiredArgsConstructor
public class ConvidadoControllerImpl implements ConvidadoController {

    private final ConvidadoService service;
    private final ConvidadoRequestMapper convidadoRequestMapper;
    private final ConvidadoResponseMapper convidadoResponseMapper;
    private final PatchHelper patchHelper;

    @GetMapping("/{id}")
    public ResponseEntity<ConvidadoResponseDto> findById(@PathVariable Integer id) {
        log.info("CONVIDADO: buscando convidado pelo id {}", id);
        Convidado convidado = service.findById(id);
        return ResponseEntity.ok(convidadoResponseMapper.toDomain(convidado));
    }

    @PostMapping("/mesa")
    public ResponseEntity<Void> vinculateToMesa(@RequestBody @Valid List<MesaConvidadoRequestDto> requestDtoList){
        log.info("CONVIDADO: vinculando convidados a mesa de id {}",
                requestDtoList.get(0).getMesaId());
        service.vinculateConvidadosToMesa(requestDtoList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/convite/{conviteId}")
    public ResponseEntity<ConvidadoResponseDto> create(@RequestBody @Valid ConvidadoRequestDto requestDto,
                                            @PathVariable Integer conviteId) {
        log.info("CONVIDADO: persistindo as informações dos convidados do convite de id {}", conviteId);
        Convidado convidado = convidadoRequestMapper.toEntity(requestDto);
        convidado = service.save(convidado, conviteId);
        ConvidadoResponseDto convidadoResponseDto = convidadoResponseMapper.toDomain(convidado);
        return ResponseEntity.created(UriUtils.uriBuilder(convidadoResponseDto.getId())).body(convidadoResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ConvidadoResponseDto> update(@PathVariable Integer id,
                                                       @RequestBody JsonMergePatch jsonMergePatch) {
        log.info("CONVIDADO: persistindo as informações do convidado de id {}", id);
        Convidado convidado = patchHelper.mergePatch(jsonMergePatch, new Convidado(), Convidado.class);
        convidado = service.update(convidado, id);
        return ResponseEntity.ok(convidadoResponseMapper.toDomain(convidado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        log.info("CONVIDADO: deletando o convidado de id {}", id);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
