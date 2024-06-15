package com.miniautorizador.data.gateway;

import com.miniautorizador.data.database.repository.CartoesRepository;
import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.mapper.CartoesMapper;
import com.miniautorizador.data.models.CartaoModel;
import helpers.CartaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartoesGatewayTest {

    @Mock
    private CartoesMapper mapper;

    @Mock
    private CartoesRepository cartoesRepository;

    @InjectMocks
    private CartoesGateway cartoesGateway;

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
        when(mapper.dtoToModel(any(CartaoRequestDTO.class))).thenReturn(cartaoModel);
        when(mapper.requestToResponseDTO(any(CartaoRequestDTO.class))).thenReturn(CartaoMock.cartaoResponseDTOMock());

        CartaoResponseDTO responseDTO = cartoesGateway.criarCartao(cartaoRequestDTO);

        verify(cartoesRepository, times(1)).save(cartaoModel);
        assertEquals(CartaoMock.cartaoResponseDTOMock(), responseDTO);
    }

    @Test
    void buscarCartaoPeloNumero() {
        when(cartoesRepository.findByNumeroCartao(anyString())).thenReturn(Optional.of(cartaoModel));

        Optional<CartaoModel> cartaoEncontrado = cartoesGateway.buscarCartaoPeloNumero(cartaoModel.getNumeroCartao());

        verify(cartoesRepository, times(1)).findByNumeroCartao(cartaoModel.getNumeroCartao());
        assertTrue(cartaoEncontrado.isPresent());
        assertEquals(cartaoModel, cartaoEncontrado.get());
    }

    @Test
    void salvarCartaoComSucesso() {
        cartoesGateway.salvarCartao(cartaoModel);

        verify(cartoesRepository, times(1)).save(cartaoModel);
    }
}