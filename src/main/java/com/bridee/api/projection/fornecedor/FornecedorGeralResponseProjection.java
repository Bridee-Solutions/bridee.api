package com.bridee.api.projection.fornecedor;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface FornecedorGeralResponseProjection extends FornecedorResponseProjection{

    String getSiteUrl();
    String getServicosFornecidos();
    String getFormaDeTrabalho();
    String getQtdConvidados();

}
