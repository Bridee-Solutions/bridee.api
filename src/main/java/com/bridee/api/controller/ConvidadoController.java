package com.bridee.api.controller;

import com.bridee.api.dto.request.ConvidadoRequestDto;
import com.bridee.api.dto.request.MesaConvidadoRequestDto;
import com.bridee.api.dto.response.ConvidadoResponseDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Mesa;
import com.bridee.api.mapper.request.ConvidadoRequestMapper;
import com.bridee.api.mapper.response.ConvidadoResponseMapper;
import com.bridee.api.service.ConvidadoService;
import com.bridee.api.service.MesaService;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/convidados")
@RequiredArgsConstructor
public class ConvidadoController {

    private final ConvidadoService service;
    private final MesaService mesaService;
    private final ConvidadoRequestMapper convidadoRequestMapper;
    private final ConvidadoResponseMapper convidadoResponseMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ConvidadoResponseDto> findById(@PathVariable Integer id) {
        Convidado convidado = service.findById(id);
        return ResponseEntity.ok(convidadoResponseMapper.toDomain(convidado));
    }

    @GetMapping("/{casamentoId}")
    public ResponseEntity<Page<ConvidadoResponseDto>> findConvidadosWithoutMesa(@PathVariable Integer casamentoId, @RequestParam String nome){
        List<Mesa> mesas = mesaService.findAllByCasamentoId(casamentoId);
        List<Convidado> convidadosWithoutMesa = service.convidadosWithoutMesa(mesas, nome);
        return ResponseEntity.ok(convidadoResponseMapper.toDomainPage(convidadosWithoutMesa));
    }

    @PostMapping("/mesa")
    public ResponseEntity<Void> vinculateToMesa(@RequestBody @Valid List<MesaConvidadoRequestDto> requestDtoList){
        service.vinculateConvidadoToMesa(requestDtoList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/convite/{conviteId}")
    public ResponseEntity<ConvidadoResponseDto> create(@RequestBody @Valid ConvidadoRequestDto requestDto,
                                            @PathVariable Integer conviteId) {
        Convidado convidado = convidadoRequestMapper.toEntity(requestDto);
        convidado = service.save(convidado, conviteId);
        ConvidadoResponseDto convidadoResponseDto = convidadoResponseMapper.toDomain(convidado);
        return ResponseEntity.created(UriUtils.uriBuilder(convidadoResponseDto.getId())).body(convidadoResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConvidadoResponseDto> update(@PathVariable Integer id,
                                                       @RequestBody @Valid ConvidadoRequestDto requestDto) {
        Convidado convidado = convidadoRequestMapper.toEntity(requestDto);
        convidado = service.update(convidado, id);
        return ResponseEntity.ok(convidadoResponseMapper.toDomain(convidado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
