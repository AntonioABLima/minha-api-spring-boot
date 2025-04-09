package com.codewithantonio.Product.Api.domain.product;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "produto")
@Entity(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cod")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cod;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer qtd;

    public Produto(ProdutoRequestDTO data){
        this.nome = data.nome();
        this.preco = data.preco();
        this.qtd = data.qtd();
    }
}