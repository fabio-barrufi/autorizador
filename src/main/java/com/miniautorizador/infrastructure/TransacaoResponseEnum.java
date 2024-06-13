package com.miniautorizador.infrastructure;

import lombok.Getter;

@Getter
public enum TransacaoResponseEnum {
    SALDO_INSUFICIENTE("SALDO_INSUFICIENTE"),
    SENHA_INVALIDA("SENHA_INVALIDA"),
    CARTAO_INEXISTENTE("CARTAO_INEXISTENTE"),
    OK("OK");

    private final String value;

    TransacaoResponseEnum(String value) {
        this.value = value;
    }

}
