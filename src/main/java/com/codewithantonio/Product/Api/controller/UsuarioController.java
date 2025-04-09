package com.codewithantonio.Product.Api.controller;

import com.codewithantonio.Product.Api.domain.usuario.UsuarioRequestDTO;
import com.codewithantonio.Product.Api.domain.usuario.Usuario;
import com.codewithantonio.Product.Api.domain.usuario.UsuarioResponseDTO;
import com.codewithantonio.Product.Api.infra.exception.ResourceNotFoundException;
import com.codewithantonio.Product.Api.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public ResponseEntity getAllUsuarios(){
        List<UsuarioResponseDTO> userList = this.repository.findAll().stream().map(UsuarioResponseDTO::new).toList();

        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable String id){
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    @PostMapping
    public ResponseEntity postUsuario(@RequestBody @Valid UsuarioRequestDTO data){
        System.out.println(data);
        Usuario newUsuario = new Usuario(data);
        repository.save(newUsuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensagem", "Usuário criado com sucesso!",
                        "id", newUsuario.getId()
                ));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity updateUsuario(@PathVariable String id, @RequestBody  @Valid UsuarioRequestDTO data){
        Integer idInteger = Integer.valueOf(id);

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        Usuario usuarioComEmail = repository.findByEmail(data.email());
        if (usuarioComEmail != null && !usuarioComEmail.getId().equals(idInteger)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email já cadastrado."));
        }


        if (!data.cpf().matches("^\\d{11}$")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "CPF deve conter exatamente 11 dígitos numéricos, sem letras ou símbolos."));
        }

        Usuario usuarioComCpf = repository.findByCpf(data.cpf());
        if (usuarioComCpf != null && !usuarioComCpf.getId().equals(idInteger)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "CPF já cadastrado."));
        }



        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        usuario.setNome(data.nome());
        usuario.setPassword(encryptedPassword);
        usuario.setEmail(data.email());
        usuario.setCpf(data.cpf());
        usuario.setRole(data.role());

        return ResponseEntity.ok(Map.of(
                "mensagem", "Usuário atualizado com sucesso!",
                "usuario", usuario
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUsuario(@PathVariable String id) {
        Integer userId = Integer.valueOf(id);
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário nao encontrado."));

        repository.delete(usuario);

        return ResponseEntity.ok(Map.of(
                "mensagem", "Usuário removido com sucesso!"
        ));
    }

}
