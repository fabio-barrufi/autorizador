package com.miniautorizador.entrypoint.controller.impl;

import com.miniautorizador.data.dtos.request.TransacaoRequestDTO;
import com.miniautorizador.data.exceptions.CampoInvalidoException;
import com.miniautorizador.data.exceptions.CartaoInexistenteException;
import com.miniautorizador.data.exceptions.SaldoInsuficienteException;
import com.miniautorizador.data.exceptions.SenhaInvalidaException;
import com.miniautorizador.domain.usecase.TransacoesUseCase;
import com.miniautorizador.entrypoint.controller.TransacoesController;
import com.miniautorizador.infrastructure.TransacaoResponseEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TransacoesControllerImpl implements TransacoesController {

    private final TransacoesUseCase transacoesUseCase;

    @Override
    public ResponseEntity<TransacaoResponseEnum> efetuarTransacao(TransacaoRequestDTO transacaoRequestDTO) {
        validarCampoSenha(transacaoRequestDTO.getSenhaCartao());
        validarCampoCartao(transacaoRequestDTO.getNumeroCartao());
        try {
            transacoesUseCase.efetuarTransacao(transacaoRequestDTO);
            return ResponseEntity.ok(TransacaoResponseEnum.OK);
        } catch (SaldoInsuficienteException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(TransacaoResponseEnum.SALDO_INSUFICIENTE);
        } catch (SenhaInvalidaException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(TransacaoResponseEnum.SENHA_INVALIDA);
        } catch (CartaoInexistenteException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(TransacaoResponseEnum.CARTAO_INEXISTENTE);
        }
    }

    private void validarCampoSenha(String senha) {
        Optional.of(senha)
                .filter(s -> s.matches("[0-9]+") && s.length() >= 4 && s.length() <= 6)
                .orElseThrow(() -> new CampoInvalidoException("A senha deve ter entre 4 e 6 dígitos numéricos"));
    }

    private void validarCampoCartao(String numeroCartao) {
        Optional.of(numeroCartao)
                .filter(n -> n.matches("[0-9]+") && n.length() == 16)
                .orElseThrow(() -> new CampoInvalidoException("O número do cartão deve ter exatamente 16 dígitos numéricos"));
    }
}
