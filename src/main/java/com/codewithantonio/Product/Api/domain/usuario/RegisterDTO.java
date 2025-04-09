package com.codewithantonio.Product.Api.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotNull String nome,
        @NotNull String email,
        @NotNull String password,
        @NotNull String cpf) {
}
