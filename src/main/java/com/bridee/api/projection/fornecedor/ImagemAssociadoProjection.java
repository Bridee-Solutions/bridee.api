package com.bridee.api.projection.fornecedor;

import java.util.List;

public interface ImagemAssociadoProjection {

    List<ImagemProjection> getImagem();

    interface ImagemProjection{
        String getUrl();
    }

}
