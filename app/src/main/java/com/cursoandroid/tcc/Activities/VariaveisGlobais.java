package com.cursoandroid.tcc.Activities;

import android.app.Application;

import com.cursoandroid.tcc.Activities.Models.Endereco;
import com.cursoandroid.tcc.Activities.Models.FormasPagamento;
import com.cursoandroid.tcc.Activities.Models.Loja;
import com.cursoandroid.tcc.Activities.Models.Pedido;
import com.cursoandroid.tcc.Activities.Models.Produto;
import com.cursoandroid.tcc.Activities.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class VariaveisGlobais extends Application {

    private Usuario usuarioLogado;
    private Endereco enderecoSelecionado;
    private Loja lojaSelecionada;
    private Pedido pedidoSelecionado;
    private List<FormasPagamento> listaFormasPagamentoSelecionada = new ArrayList<>();
    private List<Produto> listaProdutos = new ArrayList<>();
    private List<Produto> carrinho = new ArrayList<>();
    private int ultimoPedidoCadastrado;

    public Endereco getEnderecoSelecionado() { return enderecoSelecionado; }
    public void setEnderecoSelecionado(Endereco enderecoSelecionado) { this.enderecoSelecionado = enderecoSelecionado; }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Loja getLojaSelecionada() { return lojaSelecionada; }
    public void setLojaSelecionada(Loja lojaSelecionada) { this.lojaSelecionada = lojaSelecionada; }

    public List<Produto> getListaProdutos() { return listaProdutos; }
    public void setListaProdutos(List<Produto> listaProdutos) { this.listaProdutos = listaProdutos; }

    public void addCarrinho(Produto produto){ this.carrinho.add(produto); }
    public List<Produto> getCarrinho() { return carrinho; }
    public void setCarrinho(List<Produto> carrinho) { this.carrinho = carrinho; }

    public List<FormasPagamento> getListaFormasPagamentoSelecionada() { return listaFormasPagamentoSelecionada; }
    public void setListaFormasPagamentoSelecionada(List<FormasPagamento> listaFormasPagamentoSelecionada) { this.listaFormasPagamentoSelecionada = listaFormasPagamentoSelecionada; }

    public Pedido getPedidoSelecionado() { return pedidoSelecionado; }
    public void setPedidoSelecionado(Pedido pedidoSelecionado) { this.pedidoSelecionado = pedidoSelecionado; }

    public int getUltimoPedidoCadastrado() { return ultimoPedidoCadastrado; }
    public void setUltimoPedidoCadastrado(int ultimoPedidoCadastrado) { this.ultimoPedidoCadastrado = ultimoPedidoCadastrado; }

}