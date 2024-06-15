package helpers;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.data.models.CartaoModel;

import java.math.BigDecimal;
import java.util.UUID;

public class CartaoMock {

    public static CartaoRequestDTO cartaoRequestDTOMock() {
        return CartaoRequestDTO.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .build();
    }

    public static CartaoResponseDTO cartaoResponseDTOMock() {
        return CartaoResponseDTO.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .build();
    }

    public static CartaoModel cartaoModelMock() {
        return CartaoModel.builder()
                .id(UUID.randomUUID().toString())
                .numeroCartao("6549873025634503")
                .senha("1234")
                .saldo(BigDecimal.valueOf(500.00))
                .build();
    }
}
