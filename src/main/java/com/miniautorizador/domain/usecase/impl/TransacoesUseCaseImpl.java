package com.miniautorizador.domain.usecase.impl;

import com.miniautorizador.data.dtos.request.TransacaoRequestDTO;
import com.miniautorizador.data.exceptions.CartaoInexistenteException;
import com.miniautorizador.data.exceptions.SaldoInsuficienteException;
import com.miniautorizador.data.exceptions.SenhaInvalidaException;
import com.miniautorizador.data.models.CartaoModel;
import com.miniautorizador.domain.boundary.CartoesBoundary;
import com.miniautorizador.domain.usecase.TransacoesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransacoesUseCaseImpl implements TransacoesUseCase {

    private final CartoesBoundary cartoesBoundary;

    @Override
    public void efetuarTransacao(TransacaoRequestDTO transacaoRequestDTO) {
        log.info("Realizando transação para o cartão {} no valor de {}",
                transacaoRequestDTO.getNumeroCartao(), transacaoRequestDTO.getValor());

        cartoesBoundary.buscarCartaoPeloNumero(transacaoRequestDTO.getNumeroCartao())
                .ifPresentOrElse(cartao -> {
                    verificarSenhaCorreta(cartao, transacaoRequestDTO.getSenhaCartao());
                    verificarSaldoCartao(cartao.getSaldo(), transacaoRequestDTO.getValor());
                    salvarNovoSaldoCartao(cartao, transacaoRequestDTO.getValor());
                }, () -> {
                    throw new CartaoInexistenteException();
                });
    }

    private void verificarSenhaCorreta(CartaoModel cartao, String senhaCartao) {
        Optional.of(cartao)
                .map(CartaoModel::getSenha)
                .filter(senha -> senha.equals(senhaCartao))
                .orElseThrow(SenhaInvalidaException::new);
    }

    private void verificarSaldoCartao(BigDecimal saldo, BigDecimal valor) {
        Optional.of(saldo)
                .filter(s -> s.compareTo(valor) >= 0)
                .orElseThrow(SaldoInsuficienteException::new);
    }

    private synchronized void salvarNovoSaldoCartao(CartaoModel cartao, BigDecimal valor) {
        BigDecimal novoSaldo = cartao.getSaldo().subtract(valor);
        cartao.setSaldo(novoSaldo);
        cartoesBoundary.salvarCartao(cartao);
    }
}
