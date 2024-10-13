package com.bridee.api.projection;

import java.util.List;

public interface FornecedorGeralResponseProjection extends FornecedorResponseProjection{

    String getSiteUrl();
    String getServicosFornecidos();
    String getFormaDeTrabalho();
    String getQtdConvidados();
    List<ImagemAssociadoProjection> getImagens();
    List<FormaPagamentoProjection> getFormaPagamento();

}
