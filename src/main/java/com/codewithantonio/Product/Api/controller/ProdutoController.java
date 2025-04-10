package com.codewithantonio.Product.Api.controller;

import com.codewithantonio.Product.Api.domain.product.Produto;
import com.codewithantonio.Product.Api.domain.product.ProdutoRequestDTO;
import com.codewithantonio.Product.Api.domain.product.ProdutoResponseDTO;
import com.codewithantonio.Product.Api.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProducts() {
        List<ProdutoResponseDTO> products = produtoService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getProductById(@PathVariable String id) {
        Produto product = produtoService.getProductById(id);
        return ResponseEntity.ok(new ProdutoResponseDTO(product));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> postProduct(@RequestBody @Valid ProdutoRequestDTO body) {
        Produto newProduct = produtoService.createProduct(body);
        ProdutoResponseDTO response = new ProdutoResponseDTO(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> updateProduct(@PathVariable String id, @RequestBody @Valid ProdutoRequestDTO data) {
        Produto updatedProduct = produtoService.updateProduct(id, data);
        ProdutoResponseDTO response = new ProdutoResponseDTO(updatedProduct);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        produtoService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}