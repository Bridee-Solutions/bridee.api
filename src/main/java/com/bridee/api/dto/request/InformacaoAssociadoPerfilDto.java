package com.bridee.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformacaoAssociadoPerfilDto {

    private InformacaoAssociadoDto informacaoAssociado;
    private MultipartFile imagemPrincipal;
    private List<MultipartFile> imagensSecundarias;
}
