package com.miniautorizador.data.mapper;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.models.CartaoModel;
import helpers.CartaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CartoesMapperImplTest {

    private CartoesMapperImpl cartoesMapper;

    private CartaoModel cartaoModel;
    private CartaoRequestDTO cartaoRequestDTO;

    @BeforeEach
    void setUp() {
        cartoesMapper = new CartoesMapperImpl();

        cartaoModel = CartaoMock.cartaoModelMock();
        cartaoRequestDTO = CartaoMock.cartaoRequestDTOMock();
    }

    @Test
    void modelToDTO() {
        CartaoRequestDTO dto = cartoesMapper.modelToDTO(cartaoModel);

        assertEquals(cartaoModel.getNumeroCartao(), dto.getNumeroCartao());
        assertEquals(cartaoModel.getSenha(), dto.getSenha());
    }

    @Test
    void modelToDTONull() {
        assertNull(cartoesMapper.modelToDTO(null));
    }

    @Test
    void dtoToModel() {
        CartaoModel model = cartoesMapper.dtoToModel(cartaoRequestDTO);

        assertEquals(cartaoRequestDTO.getNumeroCartao(), model.getNumeroCartao());
        assertEquals(cartaoRequestDTO.getSenha(), model.getSenha());
    }

    @Test
    void dtoToModelNull() {
        assertNull(cartoesMapper.dtoToModel(null));
    }

    @Test
    void requestToResponseDTO() {
        CartaoResponseDTO responseDTO = cartoesMapper.requestToResponseDTO(cartaoRequestDTO);

        assertEquals(cartaoRequestDTO.getNumeroCartao(), responseDTO.getNumeroCartao());
        assertEquals(cartaoRequestDTO.getSenha(), responseDTO.getSenha());
    }

    @Test
    void requestToResponseDTONull() {
        assertNull(cartoesMapper.requestToResponseDTO(null));
    }
}
