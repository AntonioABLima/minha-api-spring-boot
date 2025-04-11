package com.codewithantonio.Product.Api.services;

import com.codewithantonio.Product.Api.domain.usuario.Usuario;
import com.codewithantonio.Product.Api.domain.usuario.UsuarioRequestDTO;
import com.codewithantonio.Product.Api.domain.usuario.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDTO> getAllUsuarios();
    Usuario getUserById(String id);
    Usuario updateUser(String id, UsuarioRequestDTO data);
    void deleteUser(String id);
}