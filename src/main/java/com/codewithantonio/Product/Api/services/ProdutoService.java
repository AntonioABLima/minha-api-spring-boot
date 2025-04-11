package com.codewithantonio.Product.Api.services;

import com.codewithantonio.Product.Api.domain.product.Produto;
import com.codewithantonio.Product.Api.domain.product.ProdutoListResponseDTO;
import com.codewithantonio.Product.Api.domain.product.ProdutoRequestDTO;
import com.codewithantonio.Product.Api.domain.product.ProdutoResponseDTO;

import java.util.List;

public interface ProdutoService {
    ProdutoListResponseDTO getAllProducts();
    Produto getProductById(String id);
    Produto createProduct(ProdutoRequestDTO data);
    Produto updateProduct(String id, ProdutoRequestDTO data);
    void deleteProduct(String id);
}
