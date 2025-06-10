package com.bridee.api.configuration.cache;

public class CacheConstants {

    public static final String ASSESSOR = "ASSESSOR";
    public static final String FORNECEDOR = "FORNECEDOR";
    public static final String CATEGORIA_CONVIDADO = "CATEGORIA_CONVIDADO";
    public static final String CATEGORIA_SERVICO = "CATEGORIA_SERVICO";
    public static final String SUBCATEGORIA_SERVICO = "SUBCATEGORIA_SERVICO";
    public static final String USER = "USER";
    public static final String FAVORITE_IMAGE = "FAVORITE_IMAGE";

    public static String[] asArray(){
        return new String[]{
          ASSESSOR,
          FORNECEDOR,
          CATEGORIA_CONVIDADO,
          CATEGORIA_SERVICO,
          SUBCATEGORIA_SERVICO,
          USER,
          FAVORITE_IMAGE
        };
    }
}
