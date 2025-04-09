package com.codewithantonio.Product.Api.controller;

import com.codewithantonio.Product.Api.domain.product.Produto;
import com.codewithantonio.Product.Api.domain.product.ProdutoRequestDTO;
import com.codewithantonio.Product.Api.domain.product.ProdutoResponseDTO;
import com.codewithantonio.Product.Api.infra.exception.ResourceNotFoundException;
import com.codewithantonio.Product.Api.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/product")
public class ProdutoController {
    @Autowired
    ProdutoRepository repository;

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProducts(){
        List<ProdutoResponseDTO> productList = this.repository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();

        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable String id){
        Produto product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody @Valid ProdutoRequestDTO body){
        Produto newProduct = new Produto(body);

        this.repository.save(newProduct);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensagem", "Produto criado com sucesso!",
                        "id", newProduct.getCod()
                ));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody  @Valid ProdutoRequestDTO data){
        Produto product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        product.setNome(data.nome());
        product.setPreco(data.preco());
        product.setQtd(data.qtd());

        return ResponseEntity.ok(Map.of(
                "mensagem", "Produto atualizado com sucesso!",
                "produto", product
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        Produto product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto nao encontrado."));

        repository.delete(product);

        return ResponseEntity.ok(Map.of("mensagem", "Produto removido com sucesso!"));

    }
}
