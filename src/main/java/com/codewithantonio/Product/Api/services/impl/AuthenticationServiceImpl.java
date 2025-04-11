package com.codewithantonio.Product.Api.services.impl;

import com.codewithantonio.Product.Api.domain.usuario.*;
import com.codewithantonio.Product.Api.infra.exception.InvalidCredentialsException;
import com.codewithantonio.Product.Api.infra.exception.ResourceConflictException;
import com.codewithantonio.Product.Api.infra.security.TokenService;
import com.codewithantonio.Product.Api.repositories.UsuarioRepository;
import com.codewithantonio.Product.Api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((Usuario) auth.getPrincipal());
            return new LoginResponseDTO(token);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("E-mail ou senha incorretos.");
        }
    }

    @Override
    public RegisterResponseDTO register(RegisterDTO data) {
        if (repository.findByEmail(data.email()).isPresent()) {
            throw new ResourceConflictException("Email já cadastrado.");
        }

        if (repository.findByCpf(data.cpf()).isPresent()) {
            throw new ResourceConflictException("CPF já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        Usuario newUser = new Usuario(data.nome(), data.email(), encryptedPassword, data.cpf(), UsuarioRole.USER);
        repository.save(newUser);

        return new RegisterResponseDTO("Usuário criado com sucesso.");
    }
}