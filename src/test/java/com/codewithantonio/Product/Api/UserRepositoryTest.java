package com.codewithantonio.Product.Api;

import com.codewithantonio.Product.Api.domain.usuario.Usuario;
import com.codewithantonio.Product.Api.domain.usuario.UsuarioRequestDTO;
import com.codewithantonio.Product.Api.domain.usuario.UsuarioRole;
import com.codewithantonio.Product.Api.repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Usuario by email successfully from DB")
    void findUserByEmailCase1(){
        String email = "teste@teste.com";
        UsuarioRequestDTO data = new UsuarioRequestDTO("Teste", email, "12345", "88888888888", UsuarioRole.USER);
        this.createUser(data);

        Optional<Usuario> result = usuarioRepository.findByEmail(email);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Usuario by email from DB when user doesn't exist")
    void findUserByEmailCase2(){
        String wrongEmail = "fail@fail.com";
        UsuarioRequestDTO data = new UsuarioRequestDTO("Teste", "teste@teste.com", "12345", "88888888888", UsuarioRole.USER);
        this.createUser(data);

        Optional<Usuario> result = usuarioRepository.findByEmail(wrongEmail);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should get Usuario by cpf successfully from DB")
    void findUserByCpfCase1(){
        String cpf = "88888888888";
        UsuarioRequestDTO data = new UsuarioRequestDTO("Teste", "teste@teste.com", "12345", cpf, UsuarioRole.USER);
        this.createUser(data);

        Optional<Usuario> result = usuarioRepository.findByCpf(cpf);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Usuario by cpf from DB when user doesn't exist")
    void findUserByCpfCase2(){
        String wrongCpf = "88888888881";
        UsuarioRequestDTO data = new UsuarioRequestDTO("Teste", "teste@teste.com", "12345", "88888888888", UsuarioRole.USER);
        this.createUser(data);
        Optional<Usuario> result = usuarioRepository.findByCpf(wrongCpf);

        assertThat(result.isEmpty()).isTrue();
    }


    private void createUser(UsuarioRequestDTO data){
        Usuario newUser = new Usuario(data);
        this.entityManager.persist(newUser);
    }
}
