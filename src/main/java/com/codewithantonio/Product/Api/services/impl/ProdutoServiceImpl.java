package com.codewithantonio.Product.Api.services.impl;

import com.codewithantonio.Product.Api.domain.product.Produto;
import com.codewithantonio.Product.Api.domain.product.ProdutoRequestDTO;
import com.codewithantonio.Product.Api.domain.product.ProdutoResponseDTO;
import com.codewithantonio.Product.Api.infra.exception.ResourceNotFoundException;
import com.codewithantonio.Product.Api.repositories.ProdutoRepository;
import com.codewithantonio.Product.Api.services.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Override
    public List<ProdutoResponseDTO> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
    }

    @Override
    public Produto getProductById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
    }

    @Override
    public Produto createProduct(ProdutoRequestDTO body) {
        Produto newProduct = new Produto(body);
        return repository.save(newProduct);
    }

    @Override
    @Transactional
    public Produto updateProduct(String id, ProdutoRequestDTO data) {
        Produto product = getProductById(id);
        product.setNome(data.nome());
        product.setPreco(data.preco());
        product.setQtd(data.qtd());
        return product;
    }

    @Override
    @Transactional
    public void deleteProduct(String id) {
        Produto product = getProductById(id);
        repository.delete(product);
    }
}