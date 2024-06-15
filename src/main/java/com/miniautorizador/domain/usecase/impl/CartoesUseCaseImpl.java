package com.miniautorizador.domain.usecase.impl;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.exceptions.CartaoExistenteException;
import com.miniautorizador.data.exceptions.CartaoInexistenteException;
import com.miniautorizador.data.models.CartaoModel;
import com.miniautorizador.domain.boundary.CartoesBoundary;
import com.miniautorizador.domain.usecase.CartoesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartoesUseCaseImpl implements CartoesUseCase {

    private final CartoesBoundary cartoesBoundary;

    @Override
    public CartaoResponseDTO criarCartao(CartaoRequestDTO cartaoRequestDTO) {
        verificaSeCartaoJaExiste(cartaoRequestDTO);

        return cartoesBoundary.criarCartao(cartaoRequestDTO);
    }

    @Override
    public BigDecimal consultarSaldoCartao(String numeroCartao) {
        return cartoesBoundary.buscarCartaoPeloNumero(numeroCartao)
                .map(CartaoModel::getSaldo)
                .orElseThrow(CartaoInexistenteException::new);
    }

    private void verificaSeCartaoJaExiste(CartaoRequestDTO cartao) {
        cartoesBoundary.buscarCartaoPeloNumero(cartao.getNumeroCartao())
                .ifPresent(cartaoEncontrado -> {
                    throw new CartaoExistenteException();
                });
    }
}
