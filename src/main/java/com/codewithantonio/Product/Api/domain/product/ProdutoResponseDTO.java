package com.codewithantonio.Product.Api.domain.product;

import java.math.BigDecimal;

public record ProdutoResponseDTO(String id, String nome, BigDecimal preco, Integer qtd) {
    public ProdutoResponseDTO(Produto produto){
        this(String.valueOf(produto.getCod()), produto.getNome(), produto.getPreco(), produto.getQtd());
    }
}

