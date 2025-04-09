package com.codewithantonio.Product.Api.repositories;

import com.codewithantonio.Product.Api.domain.product.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, String> {
}
