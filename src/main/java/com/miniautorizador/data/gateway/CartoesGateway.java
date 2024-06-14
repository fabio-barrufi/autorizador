package com.miniautorizador.data.gateway;

import com.miniautorizador.data.database.repository.CartoesRepository;
import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.mapper.CartoesMapper;
import com.miniautorizador.data.models.CartaoModel;
import com.miniautorizador.domain.boundary.CartoesBoundary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartoesGateway implements CartoesBoundary {

    private final CartoesMapper mapper;
    private final CartoesRepository cartoesRepository;

    @Override
    public CartaoResponseDTO criarCartao(CartaoRequestDTO cartaoRequestDTO) {
        CartaoModel cartaoModel = mapper.dtoToModel(cartaoRequestDTO);
        cartoesRepository.save(cartaoModel);

        return mapper.requestToResponseDTO(cartaoRequestDTO);
    }

    @Override
    public Optional<CartaoModel> buscarCartaoPeloNumero(String numeroCartao) {
        return cartoesRepository.findByNumeroCartao(numeroCartao);
    }

    @Override
    public void salvarCartao(CartaoModel cartao) {
        cartoesRepository.save(cartao);
    }
}
