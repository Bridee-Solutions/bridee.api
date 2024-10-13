package com.bridee.api.projection;

import java.util.List;

public interface ImagemAssociadoProjection {

    List<ImagemProjection> getImagem();

    interface ImagemProjection{
        String getUrl();
    }

}
