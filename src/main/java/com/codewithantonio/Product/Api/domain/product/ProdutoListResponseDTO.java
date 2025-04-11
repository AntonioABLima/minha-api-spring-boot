package com.codewithantonio.Product.Api.domain.product;

import java.util.List;

public class ProdutoListResponseDTO {
    private List<ProdutoResponseDTO> products;
    private int total;

    public ProdutoListResponseDTO(List<ProdutoResponseDTO> products) {
        this.products = products;
        this.total = products.size();
    }

    public List<ProdutoResponseDTO> getProdutos() {
        return products;
    }

    public int getTotal() {
        return total;
    }
}
