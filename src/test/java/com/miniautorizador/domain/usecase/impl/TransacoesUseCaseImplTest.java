package com.miniautorizador.domain.usecase.impl;

import com.miniautorizador.data.dtos.request.TransacaoRequestDTO;
import com.miniautorizador.data.exceptions.CartaoInexistenteException;
import com.miniautorizador.data.exceptions.SaldoInsuficienteException;
import com.miniautorizador.data.exceptions.SenhaInvalidaException;
import com.miniautorizador.data.models.CartaoModel;
import com.miniautorizador.domain.boundary.CartoesBoundary;
import helpers.CartaoMock;
import helpers.TransacaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransacoesUseCaseImplTest {

    @Mock
    private CartoesBoundary cartoesBoundary;

    @InjectMocks
    private TransacoesUseCaseImpl transacoesUseCase;

    private TransacaoRequestDTO transacaoRequestDTO;
    private CartaoModel cartaoModel;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();
        cartaoModel = CartaoMock.cartaoModelMock();
    }

    @Test
    void efetuarTransacaoComSucesso() {
        when(cartoesBoundary.buscarCartaoPeloNumero(anyString())).thenReturn(Optional.of(cartaoModel));

        transacoesUseCase.efetuarTransacao(transacaoRequestDTO);

        verify(cartoesBoundary, times(1)).buscarCartaoPeloNumero(transacaoRequestDTO.getNumeroCartao());
        verify(cartoesBoundary, times(1)).salvarCartao(any(CartaoModel.class));
    }

    @Test
    void efetuarTransacaoThrowsCartaoInexistenteException() {
        when(cartoesBoundary.buscarCartaoPeloNumero(anyString())).thenReturn(Optional.empty());

        assertThrows(CartaoInexistenteException.class, () -> {
            transacoesUseCase.efetuarTransacao(transacaoRequestDTO);
        });

        verify(cartoesBoundary, times(1)).buscarCartaoPeloNumero(transacaoRequestDTO.getNumeroCartao());
        verify(cartoesBoundary, never()).salvarCartao(any(CartaoModel.class));
    }

    @Test
    void efetuarTransacaoThrowsSenhaInvalidaException() {
        cartaoModel.setSenha("12345");
        when(cartoesBoundary.buscarCartaoPeloNumero(anyString())).thenReturn(Optional.of(cartaoModel));

        transacaoRequestDTO.setSenhaCartao("54321");

        assertThrows(SenhaInvalidaException.class, () -> {
            transacoesUseCase.efetuarTransacao(transacaoRequestDTO);
        });

        verify(cartoesBoundary, times(1)).buscarCartaoPeloNumero(transacaoRequestDTO.getNumeroCartao());
        verify(cartoesBoundary, never()).salvarCartao(any(CartaoModel.class));
    }

    @Test
    void efetuarTransacaoThrowsSaldoInsuficienteException() {
        cartaoModel.setSaldo(BigDecimal.valueOf(100.00));
        when(cartoesBoundary.buscarCartaoPeloNumero(anyString())).thenReturn(Optional.of(cartaoModel));

        transacaoRequestDTO.setValor(BigDecimal.valueOf(200.00));

        assertThrows(SaldoInsuficienteException.class, () -> {
            transacoesUseCase.efetuarTransacao(transacaoRequestDTO);
        });

        verify(cartoesBoundary, times(1)).buscarCartaoPeloNumero(transacaoRequestDTO.getNumeroCartao());
        verify(cartoesBoundary, never()).salvarCartao(any(CartaoModel.class));
    }
}