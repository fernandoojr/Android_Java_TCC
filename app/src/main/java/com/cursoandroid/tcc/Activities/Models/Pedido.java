package com.cursoandroid.tcc.Activities.Models;

public class Pedido {
    int id, status;
    Double valorTotal, valorEntrega, valorProdutos;
    String data, loja, apelido, rua, numero, bairro, pagamento;

    public Pedido() { }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) { this.loja = loja; }

    public Double getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(Double valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public Double getValorProdutos() {
        return valorProdutos;
    }

    public void setValorProdutos(Double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    public String getApelido() { return apelido; }

    public void setApelido(String apelido) { this.apelido = apelido; }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) { this.rua = rua; }

    public String getNumero() { return numero; }

    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return bairro; }

    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getPagamento() { return pagamento; }

    public void setPagamento(String pagamento) { this.pagamento = pagamento; }
}
