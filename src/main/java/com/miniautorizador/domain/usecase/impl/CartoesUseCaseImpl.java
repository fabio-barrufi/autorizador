package com.miniautorizador.domain.usecase.impl;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.exceptions.CartaoExistenteException;
import com.miniautorizador.data.exceptions.CartaoInexistenteException;
import com.miniautorizador.data.models.CartaoModel;
import com.miniautorizador.domain.boundary.CartoesBoundary;
import com.miniautorizador.domain.usecase.CartoesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartoesUseCaseImpl implements CartoesUseCase {

    private final CartoesBoundary cartoesBoundary;

    @Override
    public CartaoResponseDTO criarCartao(CartaoRequestDTO cartaoRequestDTO) {
        verificaSeCartaoJaExiste(cartaoRequestDTO);

        log.info("Realizando a criação de um novo cartão: {}", cartaoRequestDTO.getNumeroCartao());

        return cartoesBoundary.criarCartao(cartaoRequestDTO);
    }

    @Override
    public BigDecimal consultarSaldoCartao(String numeroCartao) {
        log.info("Consultando o saldo do cartão: {}", numeroCartao);

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
