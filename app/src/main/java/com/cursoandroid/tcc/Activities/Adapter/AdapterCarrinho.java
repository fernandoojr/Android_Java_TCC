package com.cursoandroid.tcc.Activities.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.tcc.Activities.Models.Produto;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterCarrinho extends RecyclerView.Adapter<AdapterCarrinho.MyViewHolder> {
    List<Produto> listaProdutos;
    List<Produto> todosProdutos;
    Activity ac;
    DecimalFormat df = new DecimalFormat("0.00");
    String preco, total;
    public AdapterCarrinho(List<Produto> lista, Activity ac) {
        this.listaProdutos = lista;
        this.ac = ac;
    }

    @NonNull
    @Override
    public AdapterCarrinho.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_carrinho, parent, false);
        todosProdutos = ((VariaveisGlobais) ac.getApplication()).getListaProdutos();
        return new AdapterCarrinho.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterCarrinho.MyViewHolder holder, final int position) {
        final Produto produto = ((VariaveisGlobais) ac.getApplication()).getCarrinho().get(position);
        holder.nome.setText(produto.getNome());
        preco = df.format(produto.getPreco()).replace('.',',');
        holder.preco.setText("R$ " + preco);
        total = df.format(produto.getPreco() * produto.getQuantidade()).replace('.', ',');
        holder.total.setText("Total: R$ " + total);
        holder.txtQuantidade.setText(String.valueOf(produto.getQuantidade()));

        holder.imgFoto.setImageBitmap(produto.getImagem());

        holder.btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt = Integer.parseInt(holder.txtQuantidade.getText().toString());
                for(int i = 0; i < ((VariaveisGlobais) ac.getApplication()).getListaProdutos().size(); i++){
                    if(produto.getId() == ((VariaveisGlobais) ac.getApplication()).getListaProdutos().get(i).getId()){
                        if(qt < ((VariaveisGlobais) ac.getApplication()).getListaProdutos().get(i).getQuantidade()){
                            qt++;
                            ((VariaveisGlobais) ac.getApplication()).getCarrinho().get(position).setQuantidade(qt);
                            holder.txtQuantidade.setText(String.valueOf(qt));
                            total = df.format(produto.getPreco() * ((VariaveisGlobais) ac.getApplication()).getCarrinho().get(position).getQuantidade()).replace('.', ',');
                            holder.total.setText("Total: R$ "+total);
                        }
                    }
                }
            }
        });

        holder.btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt = Integer.parseInt(holder.txtQuantidade.getText().toString());
                if(qt > 1) {
                    qt--;
                    ((VariaveisGlobais) ac.getApplication()).getCarrinho().get(position).setQuantidade(qt);
                    holder.txtQuantidade.setText(String.valueOf(qt));
                    total = df.format(produto.getPreco() * ((VariaveisGlobais) ac.getApplication()).getCarrinho().get(position).getQuantidade()).replace('.', ',');
                    holder.total.setText("Total: R$ "+total);
                }else{
                    removerProduto(position);
                }
            }
        });

        holder.btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerProduto(position);
            }
        });
    }

    public void removerProduto(int position){
        ((VariaveisGlobais) ac.getApplication()).getCarrinho().remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

        if( ((VariaveisGlobais) ac.getApplication()).getCarrinho().isEmpty()){
            ac.findViewById(R.id.txtVazio).setVisibility(View.VISIBLE);
        }else{
            ac.findViewById(R.id.txtVazio).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome, preco, total;
        ImageView imgFoto;
        Button btnMais, btnMenos, btnRemover;
        EditText txtQuantidade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNome);
            preco = itemView.findViewById(R.id.txtValorItens);
            imgFoto = itemView.findViewById(R.id.imgFoto);
            btnMais = itemView.findViewById(R.id.btnMais);
            btnMenos = itemView.findViewById(R.id.btnMenos);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidade);
            btnRemover = itemView.findViewById(R.id.btnRemover);
            total = itemView.findViewById(R.id.txtTotal);
        }
    }
}
