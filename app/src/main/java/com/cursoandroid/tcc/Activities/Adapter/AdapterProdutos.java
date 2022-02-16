package com.cursoandroid.tcc.Activities.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.tcc.Activities.Models.Produto;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterProdutos extends RecyclerView.Adapter<AdapterProdutos.MyViewHolder> {

    List<Produto> listaProdutos;
    List<Produto> listaProdutosCopia = new ArrayList<>();
    Activity ac;

    public AdapterProdutos(List<Produto> lista, Activity ac) {
        this.listaProdutos = lista;
        listaProdutosCopia.addAll(lista);
        this.ac = ac;
    }

    @NonNull
    @Override
    public AdapterProdutos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produto, parent, false);

        return new AdapterProdutos.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterProdutos.MyViewHolder holder, final int position) {
        final Produto produto = listaProdutos.get(position);
        holder.nome.setText(produto.getNome());
        DecimalFormat df = new DecimalFormat("0.00");
        String preco = df.format(produto.getPreco()).replace('.',',');
        holder.preco.setText("R$ "+preco);
        holder.imgFoto.setImageBitmap(produto.getImagem());

        holder.btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt = Integer.parseInt(holder.txtQuantidade.getText().toString());
                if(qt < produto.getQuantidade()){
                    qt++;
                    holder.txtQuantidade.setText(String.valueOf(qt));
                }
            }
        });

        holder.btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt = Integer.parseInt(holder.txtQuantidade.getText().toString());
                if(qt > 0){
                    qt--;
                    holder.txtQuantidade.setText(String.valueOf(qt));
                }
            }
        });

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantidade = Integer.parseInt(holder.txtQuantidade.getText().toString());
                if(quantidade>0) {
                    boolean existe = false;
                    for(int i=0; i<((VariaveisGlobais) ac.getApplication()).getCarrinho().size(); i++){
                        if(((VariaveisGlobais) ac.getApplication()).getCarrinho().get(i).getId() == produto.getId()){
                            existe = true;
                            int newQt = ((VariaveisGlobais) ac.getApplication()).getCarrinho().get(i).getQuantidade() + Integer.parseInt(holder.txtQuantidade.getText().toString());
                            if(newQt > produto.getQuantidade()){
                                ((VariaveisGlobais) ac.getApplication()).getCarrinho().get(i).setQuantidade(produto.getQuantidade());
                                Toast.makeText(ac.getApplicationContext(), "Excedeu a quantidade disponível!", Toast.LENGTH_SHORT).show();
                            } else {
                                ((VariaveisGlobais) ac.getApplication()).getCarrinho().get(i).setQuantidade(newQt);
                                Toast.makeText(ac.getApplicationContext(), "Produto adicionado novamente ao carrinho", Toast.LENGTH_SHORT).show();
                            }
                            holder.txtQuantidade.setText(String.valueOf(0));
                        }
                    }
                    if(existe == false) {
                        Produto produtoCarrinho = new Produto();
                        produtoCarrinho.setId(produto.getId());
                        produtoCarrinho.setPreco(produto.getPreco());
                        produtoCarrinho.setNome(produto.getNome());
                        produtoCarrinho.setIdCategoria(produto.getIdCategoria());
                        produtoCarrinho.setImagem(produto.getImagem());
                        produtoCarrinho.setQuantidade(Integer.parseInt(holder.txtQuantidade.getText().toString()));
                        ((VariaveisGlobais) ac.getApplication()).addCarrinho(produtoCarrinho);
                        Toast.makeText(ac.getApplicationContext(), "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
                        holder.txtQuantidade.setText(String.valueOf(0));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome, preco;
        ImageView imgFoto;
        Button btnMais, btnMenos, btnAdd;
        EditText txtQuantidade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNome);
            preco = itemView.findViewById(R.id.txtValorItens);
            imgFoto = itemView.findViewById(R.id.imgFoto);
            btnMais = itemView.findViewById(R.id.btnMais);
            btnMenos = itemView.findViewById(R.id.btnMenos);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidade);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }

    public void filter(String text) {
        listaProdutos.clear();
        if (text.isEmpty()) {
            listaProdutos.addAll(listaProdutosCopia);
        } else {
            text = text.toLowerCase();
            for (Produto produto : listaProdutosCopia) {
                if (produto.getNome().toLowerCase().contains(text) || produto.getCategoria().toLowerCase().contains(text)) {
                    listaProdutos.add(produto);
                }
            }
        }
        notifyDataSetChanged();
    }
}
