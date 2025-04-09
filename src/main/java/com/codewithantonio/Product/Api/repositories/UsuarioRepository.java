package com.codewithantonio.Product.Api.repositories;

import com.codewithantonio.Product.Api.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByEmail(String email);

    Usuario findByCpf(String cpf);

}
