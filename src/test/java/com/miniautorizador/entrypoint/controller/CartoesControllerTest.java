package com.miniautorizador.entrypoint.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.domain.usecase.CartoesUseCase;
import com.miniautorizador.entrypoint.controller.impl.CartoesControllerImpl;
import com.miniautorizador.infrastructure.ApiConstants;
import helpers.CartaoMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class CartoesControllerTest {

    @InjectMocks
    CartoesControllerImpl controller;

    @Mock
    CartoesUseCase useCase;

    private MockMvc mockMvc;

    private Gson gson;

    private final String numeroCartao = "6549873025634503";

    private final CartaoRequestDTO cartaoRequestDTO = CartaoMock.cartaoRequestDTOMock();

    private final String baseUrl = ApiConstants.CARTOES_BASE_URL;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.gson = new GsonBuilder()
                .create();
    }

    @Test
    void criarCartao() throws Exception {
        var mvcResult =
                mockMvc
                        .perform(
                         post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(cartaoRequestDTO)))
                        .andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn();

        Assertions.assertThat(mvcResult.getResponse()).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void consultarSaldoCartao() throws Exception {
        String path = baseUrl + "/" + numeroCartao;

        var mvcResult =
                mockMvc
                        .perform(
                        get(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        Assertions.assertThat(mvcResult.getResponse()).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void validarCriarCartao(){
        controller.criarCartao(cartaoRequestDTO);

        verify(useCase, times(1)).criarCartao(cartaoRequestDTO);
    }

    @Test
    void validarConsultarSaldoCartao(){
        controller.consultarSaldoCartao(numeroCartao);

        verify(useCase, times(1)).consultarSaldoCartao(numeroCartao);
    }
}
