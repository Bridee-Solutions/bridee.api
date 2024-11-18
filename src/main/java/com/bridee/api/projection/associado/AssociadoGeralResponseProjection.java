package com.bridee.api.projection.associado;

public interface AssociadoGeralResponseProjection extends AssociadoResponseProjection {

    String getSiteUrl();
    String getServicosFornecidos();
    String getFormaDeTrabalho();
    String getQtdConvidados();
    Boolean getIsCasamentoCatolico();

}
