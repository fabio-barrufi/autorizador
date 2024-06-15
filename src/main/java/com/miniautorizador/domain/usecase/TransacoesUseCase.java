package com.miniautorizador.domain.usecase;

import com.miniautorizador.data.dtos.request.TransacaoRequestDTO;

public interface TransacoesUseCase {
    void efetuarTransacao(TransacaoRequestDTO transacaoRequestDTO);
}
