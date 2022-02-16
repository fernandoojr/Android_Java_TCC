package com.cursoandroid.tcc.Activities.Models;

import android.graphics.Bitmap;

public class Produto {
    String nome, categoria;
    int id, idCategoria, quantidade;
    Double preco;
    Bitmap imagem;

    public Produto() { }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }
}
