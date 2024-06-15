package com.miniautorizador.entrypoint.controller.impl;

import com.miniautorizador.data.dtos.request.TransacaoRequestDTO;
import com.miniautorizador.data.exceptions.CampoInvalidoException;
import com.miniautorizador.data.exceptions.CartaoInexistenteException;
import com.miniautorizador.data.exceptions.SaldoInsuficienteException;
import com.miniautorizador.data.exceptions.SenhaInvalidaException;
import com.miniautorizador.domain.usecase.TransacoesUseCase;
import com.miniautorizador.infrastructure.TransacaoResponseEnum;
import helpers.TransacaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

class TransacoesControllerImplTest {
    @Mock
    TransacoesUseCase transacoesUseCase;

    @InjectMocks
    TransacoesControllerImpl controller;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void efetuarTransacaoComSucesso() {
        TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();

        ResponseEntity<TransacaoResponseEnum> response = controller.efetuarTransacao(transacaoRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(TransacaoResponseEnum.OK, response.getBody());
    }

    @Test
    void efetuarTransacaoThrowsSaldoInsuficienteException() {
        TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();

        doThrow(SaldoInsuficienteException.class).when(transacoesUseCase).efetuarTransacao(any(TransacaoRequestDTO.class));

        ResponseEntity<TransacaoResponseEnum> response = controller.efetuarTransacao(transacaoRequestDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(TransacaoResponseEnum.SALDO_INSUFICIENTE, response.getBody());
    }

    @Test
    void efetuarTransacaoThrowsSenhaInvalidaException() {
        TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();

        doThrow(SenhaInvalidaException.class).when(transacoesUseCase).efetuarTransacao(any(TransacaoRequestDTO.class));

        ResponseEntity<TransacaoResponseEnum> response = controller.efetuarTransacao(transacaoRequestDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(TransacaoResponseEnum.SENHA_INVALIDA, response.getBody());
    }

    @Test
    void efetuarTransacaoCartaoInexistenteException() {
        TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();

        doThrow(CartaoInexistenteException.class).when(transacoesUseCase).efetuarTransacao(any(TransacaoRequestDTO.class));

        ResponseEntity<TransacaoResponseEnum> response = controller.efetuarTransacao(transacaoRequestDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(TransacaoResponseEnum.CARTAO_INEXISTENTE, response.getBody());
    }

    @Test
    void efetuarTransacaoNumeroCartaoInvalidoThrowsCampoInvalidoException() {
        String numeroCartaoInvalido = "abc";
        TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();
        transacaoRequestDTO.setNumeroCartao(numeroCartaoInvalido);

        assertThrows(CampoInvalidoException.class, () -> {
            controller.efetuarTransacao(transacaoRequestDTO);
        });
    }

    @Test
    void efetuarTransacaoSenhaInvalidaThrowsCampoInvalidoException() {
        String senhaInvalida = "123";
        TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();
        transacaoRequestDTO.setSenhaCartao(senhaInvalida);

        assertThrows(CampoInvalidoException.class, () -> {
            controller.efetuarTransacao(transacaoRequestDTO);
        });
    }
}
