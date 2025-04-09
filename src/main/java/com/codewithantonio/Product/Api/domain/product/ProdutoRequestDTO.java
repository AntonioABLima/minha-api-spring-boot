package com.codewithantonio.Product.Api.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank
        String nome,

        @NotNull
        BigDecimal preco,

        @NotNull
        Integer qtd
) {
}