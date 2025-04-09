package com.codewithantonio.Product.Api.domain.usuario;

public record UsuarioResponseDTO(String id, String nome, String email, String cpf, UsuarioRole role) {
    public UsuarioResponseDTO(Usuario usuario){
        this(String.valueOf(usuario.getId()), usuario.getNome(), usuario.getEmail(), usuario.getCpf(), usuario.getRole());
    }
}
