package com.miniautorizador.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cartao")
public class CartaoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NotNull
    @Column(name = "numero_cartao", nullable = false, unique = true)
    @Size(min = 16, max = 16)
    private String numeroCartao;

    @NotNull
    @Column(name = "senha", nullable = false)
    @Size(min = 4, max = 6)
    private String senha;

    @Column(name = "saldo", nullable = false)
    @Builder.Default
    private BigDecimal saldo = BigDecimal.valueOf(500.00);
}
