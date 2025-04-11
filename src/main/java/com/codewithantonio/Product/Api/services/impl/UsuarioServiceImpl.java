package com.codewithantonio.Product.Api.services.impl;

import com.codewithantonio.Product.Api.domain.usuario.Usuario;
import com.codewithantonio.Product.Api.domain.usuario.UsuarioRequestDTO;
import com.codewithantonio.Product.Api.domain.usuario.UsuarioResponseDTO;
import com.codewithantonio.Product.Api.infra.exception.ResourceNotFoundException;
import com.codewithantonio.Product.Api.repositories.UsuarioRepository;
import com.codewithantonio.Product.Api.services.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioResponseDTO> getAllUsuarios() {
        return repository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .toList();
    }

    @Override
    public Usuario getUserById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
    }

    @Override
    @Transactional
    public Usuario updateUser(String id, UsuarioRequestDTO data) {
        Usuario usuario = getUserById(id);

        Optional<Usuario> usuarioComEmailOpt = repository.findByEmail(data.email());
        if (usuarioComEmailOpt.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        if (!data.cpf().matches("^\\d{11}$")) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos numéricos, sem letras ou símbolos.");
        }
        Optional<Usuario> usuarioComCpfOpt = repository.findByCpf(data.cpf());
        if (usuarioComCpfOpt.isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());

        usuario.setNome(data.nome());
        usuario.setPassword(encryptedPassword);
        usuario.setEmail(data.email());
        usuario.setCpf(data.cpf());
        usuario.setRole(data.role());

        return usuario;
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        Usuario usuario = getUserById(id);
        repository.delete(usuario);
    }
}