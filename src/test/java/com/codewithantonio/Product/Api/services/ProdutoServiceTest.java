package com.codewithantonio.Product.Api.services;

import com.codewithantonio.Product.Api.domain.product.Produto;
import com.codewithantonio.Product.Api.domain.product.ProdutoRequestDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class ProdutoServiceTest {

    private EntityManager entityManager;

    @Test
    void getAllProducts() {
    }

    @Test
    void getProductById() {
    }

    @DisplayName("Should create Produto successfully when everything is ok")
    @Test
    void createProductCase1() {
    }

    @Test
    void createProductCase2() {
    }

    @DisplayName("Should throw exception when Produto is missing name")
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    private Produto createProduct(ProdutoRequestDTO data){
        Produto newProduct = new Produto(data);
        this.entityManager.persist(newProduct);
        return newProduct;
    }
}