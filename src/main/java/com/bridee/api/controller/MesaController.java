package com.bridee.api.controller;

import com.bridee.api.dto.request.MesaRequestDto;
import com.bridee.api.dto.response.ConvidadoResponseDto;
import com.bridee.api.dto.response.MesaResponseDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Mesa;
import com.bridee.api.mapper.request.MesaRequestMapper;
import com.bridee.api.mapper.response.ConvidadoResponseMapper;
import com.bridee.api.mapper.response.MesaResponseMapper;
import com.bridee.api.service.ConvidadoService;
import com.bridee.api.service.MesaService;
import com.bridee.api.utils.PageUtils;
import com.bridee.api.utils.UriUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;
    private final ConvidadoService convidadoService;
    private final ConvidadoResponseMapper convidadoResponseMapper;
    private final MesaRequestMapper mesaRequestMapper;
    private final MesaResponseMapper mesaResponseMapper;

    @GetMapping("/casamento/{casamentoId}")
    public ResponseEntity<Page<MesaResponseDto>> findAll(@PathVariable Integer casamentoId, Pageable pageable){

        List<Mesa> mesasCasamento = mesaService.findAllByCasamentoId(casamentoId);
        Page<MesaResponseDto> mesaResponse = mesaResponseMapper.toDomainPage(mesasCasamento, pageable);

        return ResponseEntity.ok(mesaResponse);
    }

    @GetMapping("/casamento/{casamentoId}/convidados-livres")
    public ResponseEntity<Page<ConvidadoResponseDto>> findConvidadosWithoutMesa(@RequestParam Map<String, Object> params,
                                                                                @PathVariable Integer casamentoId){
        List<Mesa> mesas = mesaService.findAllByCasamentoId(casamentoId);
        Pageable pageRequest = PageUtils.buildPageable(params);
        String nome = (String) params.get("nome");
        List<Convidado> convidadosWithoutMesa = convidadoService.convidadosWithoutMesa(mesas, nome, casamentoId);
        return ResponseEntity.ok(convidadoResponseMapper.toDomainPage(convidadosWithoutMesa, pageRequest));
    }

    @PostMapping("/casamento/{casamentoId}")
    public ResponseEntity<MesaResponseDto> save(@RequestBody MesaRequestDto requestDto,
                                                @PathVariable Integer casamentoId){
        Mesa mesa = mesaRequestMapper.toEntity(requestDto);
        mesa = mesaService.save(mesa, casamentoId);
        MesaResponseDto responseDto = mesaResponseMapper.toDomain(mesa);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PutMapping("/casamento/{id}")
    public ResponseEntity<MesaResponseDto> update(@RequestBody MesaRequestDto requestDto,
                                                  @PathVariable Integer id){
        Mesa mesa = mesaRequestMapper.toEntity(requestDto);
        mesa = mesaService.update(mesa, id);
        return ResponseEntity.ok(mesaResponseMapper.toDomain(mesa));
    }

    @DeleteMapping("/casamento/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        mesaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
