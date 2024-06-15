package com.miniautorizador.entrypoint.controller.impl;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.exceptions.CampoInvalidoException;
import com.miniautorizador.data.exceptions.CartaoExistenteException;
import com.miniautorizador.data.exceptions.CartaoInexistenteException;
import com.miniautorizador.data.mapper.CartoesMapper;
import com.miniautorizador.domain.usecase.CartoesUseCase;
import com.miniautorizador.entrypoint.controller.CartoesController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CartoesControllerImpl implements CartoesController {

    private final CartoesUseCase cartoesUseCase;

    private final CartoesMapper mapper;

    @Override
    public ResponseEntity<CartaoResponseDTO> criarCartao(CartaoRequestDTO cartaoRequestDTO) {
        validarCampoSenha(cartaoRequestDTO.getSenha());
        validarCampoCartao(cartaoRequestDTO.getNumeroCartao());
        try {
            CartaoResponseDTO cartaoResponseDTO = cartoesUseCase.criarCartao(cartaoRequestDTO);
            return new ResponseEntity<>(cartaoResponseDTO, HttpStatus.CREATED);
        } catch (CartaoExistenteException ex) {
            return new ResponseEntity<>(mapper.requestToResponseDTO(cartaoRequestDTO), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public ResponseEntity<BigDecimal> consultarSaldoCartao(String numeroCartao) {
        validarCampoCartao(numeroCartao);
        try {
            return new ResponseEntity<>(cartoesUseCase.consultarSaldoCartao(numeroCartao), HttpStatus.OK);
        } catch (CartaoInexistenteException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
