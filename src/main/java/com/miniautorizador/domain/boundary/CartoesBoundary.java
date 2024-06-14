package com.miniautorizador.domain.boundary;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.models.CartaoModel;

import java.util.Optional;

public interface CartoesBoundary {
    CartaoResponseDTO criarCartao(CartaoRequestDTO cartaoRequestDTO);
    Optional<CartaoModel> buscarCartaoPeloNumero(String numeroCartao);
    void salvarCartao(CartaoModel cartao);
}
