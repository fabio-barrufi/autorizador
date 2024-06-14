package com.miniautorizador.entrypoint.controller;

import com.miniautorizador.data.dtos.request.CartaoRequestDTO;
import com.miniautorizador.data.dtos.response.CartaoResponseDTO;
import com.miniautorizador.infrastructure.ApiConstants;
import com.miniautorizador.infrastructure.ApiResponseConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.math.BigDecimal;

@Tag(name = "Cartões Service")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApiConstants.CARTOES_BASE_URL)
@CrossOrigin
public interface CartoesController {

    @Operation(summary = "Criação de cartão",
            description = "<h4><b>Endpoint para cadastro de um cartão.</b></h4>")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = ApiResponseConstants.STATUS_201_CREATED,
                            content = @Content(schema = @Schema(implementation = CartaoResponseDTO.class))),
                    @ApiResponse(responseCode = "422", description = ApiResponseConstants.STATUS_422_UNPROCESSABLE_ENTITY,
                            content = @Content(schema = @Schema(implementation = CartaoResponseDTO.class)))
            })
    @PostMapping
    ResponseEntity<CartaoResponseDTO> criarCartao(@RequestBody @Valid CartaoRequestDTO cartaoRequestDTO);

    @Operation(summary = "Consulta de saldo de um cartão",
            description = "<h4><b>Endpoint para consultar o saldo de um cartão.</b></h4>")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ApiResponseConstants.STATUS_200_OK,
                            content = @Content(schema = @Schema(implementation = CartaoResponseDTO.class),
                                    examples = @ExampleObject(value = "495.15"))),
                    @ApiResponse(responseCode = "404", description = ApiResponseConstants.STATUS_404_NOT_FOUND,
                            content = @Content(schema = @Schema())),

            })
    @GetMapping(value = ApiConstants.CARTOES_SALDO_ENDPOINT)
    ResponseEntity<BigDecimal> consultarSaldoCartao(@PathVariable String numeroCartao);
}
