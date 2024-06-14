package com.miniautorizador.data.mapper;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.models.CartaoModel;
import org.mapstruct.Mapper;

@Mapper
public interface CartoesMapper {
    CartaoRequestDTO modelToDTO(CartaoModel model);

    CartaoModel dtoToModel(CartaoRequestDTO dto);

    CartaoResponseDTO requestToResponseDTO(CartaoRequestDTO dto);
}
