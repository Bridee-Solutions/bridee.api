package com.bridee.api.repository.projection.convite;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface ConviteResumoProjection {

    Integer getTotalConvites();
    Integer getTotalConfirmado();
    Integer getTotalConvidados();
    Integer getTotalAdultos();
    Integer getTotalCriancas();
}
