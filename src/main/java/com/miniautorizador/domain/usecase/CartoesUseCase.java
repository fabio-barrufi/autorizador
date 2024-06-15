package com.miniautorizador.domain.usecase;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;

import java.math.BigDecimal;

public interface CartoesUseCase {
    CartaoResponseDTO criarCartao(CartaoRequestDTO cartaoRequestDTO);

    BigDecimal consultarSaldoCartao(String numeroCartao);
}
