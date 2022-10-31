package br.com.fernandoguide.cache.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProdutoDTO  {
    private Integer id;
    private String nome;
    private String descricao;
    private Double preco;

    public ProdutoDTO() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getPreco() {
        return this.preco;
    }
}