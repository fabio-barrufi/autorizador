package com.miniautorizador.entrypoint.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miniautorizador.data.dtos.request.TransacaoRequestDTO;
import com.miniautorizador.domain.usecase.TransacoesUseCase;
import com.miniautorizador.entrypoint.controller.impl.TransacoesControllerImpl;
import com.miniautorizador.infrastructure.ApiConstants;
import helpers.TransacaoMock;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class TransacoesControllerTest {

    @InjectMocks
    TransacoesControllerImpl controller;

    @Mock
    TransacoesUseCase useCase;

    private MockMvc mockMvc;

    private Gson gson;

    private final TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.gson = new GsonBuilder()
                .create();
    }

    @Test
    void efetuarTransacao() throws Exception {
        String baseUrl = ApiConstants.TRANSACOES_BASE_URL;
        var mvcResult =
                mockMvc
                        .perform(
                        post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(transacaoRequestDTO)))
                        .andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn();

        Assertions.assertThat(mvcResult.getResponse()).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void validarEfetuarTransacao(){
        controller.efetuarTransacao(transacaoRequestDTO);

        verify(useCase, times(1)).efetuarTransacao(transacaoRequestDTO);
    }
}
