package com.miniautorizador.data.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaoResponseDTO {
    @Schema(description = "Número do cartão do usuário.", example = "6549873025634501")
    private String numeroCartao;
    @Schema(description = "Senha do cartão do usuário", example = "1234")
    private String senha;
}
