package com.miniautorizador.entrypoint.controller.impl;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.exceptions.CampoInvalidoException;
import com.miniautorizador.data.exceptions.CartaoExistenteException;
import com.miniautorizador.data.exceptions.CartaoInexistenteException;
import com.miniautorizador.data.mapper.CartoesMapper;
import com.miniautorizador.domain.usecase.CartoesUseCase;
import helpers.CartaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class CartoesControllerImplTest {

    @Mock
    CartoesUseCase useCase;

    @InjectMocks
    private CartoesControllerImpl controller;

    @Mock
    CartoesMapper mapper;

    private final String numeroCartao = "6549873025634503";
    private final String numeroCartaoInvalido = "abc";
    private final String senhaInvalida = "123";

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        controller = new CartoesControllerImpl(useCase, mapper);
    }

    @Test
    void criarCartaoComSucesso() {
        CartaoRequestDTO cartaoRequestDTO = CartaoMock.cartaoRequestDTOMock();
        CartaoResponseDTO cartaoResponseDTO = CartaoMock.cartaoResponseDTOMock();

        when(useCase.criarCartao(any(CartaoRequestDTO.class))).thenReturn(cartaoResponseDTO);

        ResponseEntity<CartaoResponseDTO> response = controller.criarCartao(cartaoRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartaoResponseDTO, response.getBody());
    }

    @Test
    void criarCartaoComNumeroCartaoInvalidoThrowsCampoInvalidoException() {
        CartaoRequestDTO cartaoRequestDTO = CartaoMock.cartaoRequestDTOMock();
        cartaoRequestDTO.setNumeroCartao(numeroCartaoInvalido);

        assertThrows(CampoInvalidoException.class, () -> {
            controller.criarCartao(cartaoRequestDTO);
        });
    }

    @Test
    void criarCartaoComSenhaInvalidaThrowsCampoInvalidoException() {
        CartaoRequestDTO cartaoRequestDTO = CartaoMock.cartaoRequestDTOMock();
        cartaoRequestDTO.setSenha(senhaInvalida);

        assertThrows(CampoInvalidoException.class, () -> {
            controller.criarCartao(cartaoRequestDTO);
        });
    }

    @Test
    void criarCartaoThrowsCartaoExistenteException() {
        CartaoRequestDTO cartaoRequestDTO = CartaoMock.cartaoRequestDTOMock();
        CartaoResponseDTO cartaoResponseDTO = CartaoMock.cartaoResponseDTOMock();

        doThrow(CartaoExistenteException.class).when(useCase).criarCartao(any(CartaoRequestDTO.class));

        when(mapper.requestToResponseDTO(any(CartaoRequestDTO.class))).thenReturn(cartaoResponseDTO);

        ResponseEntity<CartaoResponseDTO> response = controller.criarCartao(cartaoRequestDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartaoResponseDTO, response.getBody());
    }

    @Test
    void consultarSaldoCartaoComSucesso() {
        BigDecimal saldo = BigDecimal.valueOf(1000);

        when(useCase.consultarSaldoCartao(numeroCartao)).thenReturn(saldo);

        ResponseEntity<BigDecimal> response = controller.consultarSaldoCartao(numeroCartao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saldo, response.getBody());
    }

    @Test
    void consultarSaldoCartaoThrowsCampoInvalidoException() {
        assertThrows(CampoInvalidoException.class, () -> {
            controller.consultarSaldoCartao(numeroCartaoInvalido);
        });
    }

    @Test
    void consultarSaldoCartaoThrowsCartaoInexistenteException() {
        doThrow(CartaoInexistenteException.class).when(useCase).consultarSaldoCartao(numeroCartao);

        ResponseEntity<BigDecimal> response = controller.consultarSaldoCartao(numeroCartao);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}