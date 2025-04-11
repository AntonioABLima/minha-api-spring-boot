package com.codewithantonio.Product.Api.controller;

import com.codewithantonio.Product.Api.domain.usuario.Usuario;
import com.codewithantonio.Product.Api.domain.usuario.UsuarioRequestDTO;
import com.codewithantonio.Product.Api.domain.usuario.UsuarioResponseDTO;
import com.codewithantonio.Product.Api.services.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(new UsuarioResponseDTO(usuarioService.getUserById(id)));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUsuario(@PathVariable String id, @RequestBody @Valid UsuarioRequestDTO data) {
        Usuario usuarioAtualizado = usuarioService.updateUser(id, data);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Usuário atualizado com sucesso!",
                "usuario", usuarioAtualizado
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUsuario(@PathVariable String id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Usuário removido com sucesso!"
        ));
    }
}