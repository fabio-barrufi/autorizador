package com.miniautorizador.entrypoint.controller;

import com.miniautorizador.data.dtos.request.TransacaoRequestDTO;
import com.miniautorizador.infrastructure.ApiConstants;
import com.miniautorizador.infrastructure.ApiResponseConstants;
import com.miniautorizador.infrastructure.TransacaoResponseEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Tag(name = "Transações Service")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApiConstants.TRANSACOES_BASE_URL)
@CrossOrigin
public interface TransacoesController {
    @Operation(summary = "Efetua transações",
            description = "<h4><b>Endpoint para efetuar uma transação.</b></h4>")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ApiResponseConstants.STATUS_200_OK,
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(value = "OK"))),
                    @ApiResponse(responseCode = "400", description = ApiResponseConstants.STATUS_400_BAD_REQUEST,
                            content = @Content()),
                    @ApiResponse(responseCode = "422", description = ApiResponseConstants.STATUS_422_UNPROCESSABLE_ENTITY,
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(value = "CARTAO_INEXISTENTE")))
            })
    @PostMapping
    ResponseEntity<TransacaoResponseEnum> efetuarTransacao(@RequestBody @Valid TransacaoRequestDTO transacaoRequestDTO);
}
