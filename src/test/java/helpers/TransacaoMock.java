package helpers;

import com.miniautorizador.data.dtos.request.TransacaoRequestDTO;

import java.math.BigDecimal;

public class TransacaoMock {

    public static TransacaoRequestDTO transacaoRequestDTOMock() {
        return TransacaoRequestDTO.builder()
                .numeroCartao("6549873025634501")
                .senhaCartao("1234")
                .valor(BigDecimal.valueOf(10.00))
                .build();
    }


}
