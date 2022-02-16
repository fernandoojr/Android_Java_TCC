package com.cursoandroid.tcc.Activities.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.tcc.Activities.Models.ItemPedido;
import com.cursoandroid.tcc.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.MyViewHolder>{

    List<ItemPedido> listaItem;
    Activity ac;
    DecimalFormat df = new DecimalFormat("0.00");

    public AdapterItem(List<ItemPedido> lista, Activity ac) {
        this.listaItem = lista;
        this.ac = ac;
    }

    @NonNull
    @Override
    public AdapterItem.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);

        return new AdapterItem.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterItem.MyViewHolder holder, final int position) {
        final ItemPedido item = listaItem.get(position);
        holder.txtNome.setText(item.getNome());
        holder.txtPreco.setText("R$"+ df.format(item.getPreco()).replace('.', ','));
        holder.txtQuantidade.setText("x"+item.getQuantidade());
    }

    @Override
    public int getItemCount() {
        return listaItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNome, txtPreco, txtQuantidade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txtNome);
            txtPreco = itemView.findViewById(R.id.txtValorItens);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidade);
        }
    }

}
