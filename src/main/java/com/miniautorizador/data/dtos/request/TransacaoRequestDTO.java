package com.miniautorizador.data.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoRequestDTO {
    @NotNull
    @Size(min = 16, max = 16)
    @Schema(description = "Número do cartão do usuário.", example = "6549873025634501")
    private String numeroCartao;

    @NotNull
    @Size(min = 4, max = 6)
    @Schema(description = "Senha do cartão do usuário.", example = "1234")
    private String senhaCartao;

    @NotNull
    @Positive
    @Schema(description = "Valor da transação realizada pelo usuário.", example = "10.10")
    private BigDecimal valor;
}
