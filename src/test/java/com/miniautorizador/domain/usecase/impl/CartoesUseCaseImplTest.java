package com.miniautorizador.domain.usecase.impl;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.exceptions.CartaoExistenteException;
import com.miniautorizador.data.exceptions.CartaoInexistenteException;
import com.miniautorizador.data.models.CartaoModel;
import com.miniautorizador.domain.boundary.CartoesBoundary;
import helpers.CartaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartoesUseCaseImplTest {

    @Mock
    private CartoesBoundary cartoesBoundary;

    @InjectMocks
    private CartoesUseCaseImpl cartoesUseCase;

    private CartaoRequestDTO cartaoRequestDTO;
    private CartaoModel cartaoModel;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cartaoRequestDTO = CartaoMock.cartaoRequestDTOMock();
        cartaoModel = CartaoMock.cartaoModelMock();
    }

    @Test
    void criarCartaoComSucesso() {
        when(cartoesBoundary.buscarCartaoPeloNumero(anyString())).thenReturn(Optional.empty());
        when(cartoesBoundary.criarCartao(any(CartaoRequestDTO.class))).thenReturn(CartaoMock.cartaoResponseDTOMock());

        CartaoResponseDTO response = cartoesUseCase.criarCartao(cartaoRequestDTO);

        assertNotNull(response);
        verify(cartoesBoundary, times(1)).criarCartao(cartaoRequestDTO);
    }

    @Test
    void criarCartaoThrowsCartaoExistenteException() {
        when(cartoesBoundary.buscarCartaoPeloNumero(anyString())).thenReturn(Optional.of(cartaoModel));

        assertThrows(CartaoExistenteException.class, () -> {
            cartoesUseCase.criarCartao(cartaoRequestDTO);
        });

        verify(cartoesBoundary, never()).criarCartao(any(CartaoRequestDTO.class));
    }

    @Test
    void consultarSaldoCartao_sucesso() {
        when(cartoesBoundary.buscarCartaoPeloNumero(anyString())).thenReturn(Optional.of(cartaoModel));

        BigDecimal saldo = cartoesUseCase.consultarSaldoCartao(cartaoModel.getNumeroCartao());

        assertNotNull(saldo);
        assertEquals(cartaoModel.getSaldo(), saldo);
        verify(cartoesBoundary, times(1)).buscarCartaoPeloNumero(cartaoModel.getNumeroCartao());
    }

    @Test
    void consultarSaldoCartao_cartaoInexistente() {
        when(cartoesBoundary.buscarCartaoPeloNumero(anyString())).thenReturn(Optional.empty());

        assertThrows(CartaoInexistenteException.class, () -> {
            cartoesUseCase.consultarSaldoCartao(cartaoModel.getNumeroCartao());
        });

        verify(cartoesBoundary, times(1)).buscarCartaoPeloNumero(cartaoModel.getNumeroCartao());
    }
}