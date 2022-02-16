package com.cursoandroid.tcc.Activities.Models;

import android.graphics.Bitmap;

public class Loja {
    int id, entregaGratis, previsao;
    String rua, numero, bairro, cidade, complemento, nome;
    Double valorEntregaPadrao, valorEntregaGratis;
    Bitmap logo;

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public Loja() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntregaGratis() {
        return entregaGratis;
    }

    public void setEntregaGratis(int entregaGratis) {
        this.entregaGratis = entregaGratis;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValorEntregaPadrao() {
        return valorEntregaPadrao;
    }

    public void setValorEntregaPadrao(Double valorEntregaPadrao) {
        this.valorEntregaPadrao = valorEntregaPadrao;
    }

    public Double getValorEntregaGratis() {
        return valorEntregaGratis;
    }

    public void setValorEntregaGratis(Double valorEntregaGratis) {
        this.valorEntregaGratis = valorEntregaGratis;
    }

    public int getPrevisao() {
        return previsao;
    }

    public void setPrevisao(int previsao) {
        this.previsao = previsao;
    }
}
