package com.codewithantonio.Product.Api.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank(message = "O 'nome' é obrigatório")
        @Size(max = 100, message = "O tamanho do campo 'nome' deve ser entre 0 e 100 caracteres.")
        String nome,

        @NotNull(message = "O 'preço' é obrigatório")
        @Positive(message = "O 'preço' deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A 'quantidade' é obrigatória")
        @Positive(message = "A 'quantidade' deve ser maior que zero")
        Integer qtd
) {
}