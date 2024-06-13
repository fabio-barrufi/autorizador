package com.miniautorizador.infrastructure;

public abstract class ApiConstants {
    private ApiConstants() {
    }

    public static final String CARTOES_BASE_URL = "/cartoes";

    public static final String CARTOES_SALDO_ENDPOINT = "/{numeroCartao}";

    public static final String TRANSACOES_BASE_URL = "/transacoes";
}
