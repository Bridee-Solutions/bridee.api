package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformacaoAssociadoPerfilDto {

    @NotNull
    private InformacaoAssociadoRequestDto informacaoAssociado;
    private List<ImageMetadata> imagem = new ArrayList<>();
    private List<Integer> formasPagamento = new ArrayList<>();
    private List<Integer> tiposCasamento = new ArrayList<>();
    private List<Integer> tiposCerimonia = new ArrayList<>();
    private MultipartFile imagemPrincipal;
    private MultipartFile imagemSecundaria;
    private MultipartFile imagemTerciaria;
    private MultipartFile imagemQuaternaria;
    private MultipartFile imagemQuinaria;
}
