package com.miniautorizador.data.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaoRequestDTO {
    @NotNull
    @Size(min = 16, max = 16)
    @Schema(description = "Número do cartão do usuário.", example = "6549873025634501")
    private String numeroCartao;

    @NotNull
    @Size(min = 4, max = 6)
    @Schema(description = "Senha do cartão do usuário", example = "1234")
    private String senha;
}
