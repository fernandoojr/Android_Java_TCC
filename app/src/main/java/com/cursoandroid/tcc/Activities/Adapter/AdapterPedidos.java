package com.cursoandroid.tcc.Activities.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.tcc.Activities.Models.Pedido;
import com.cursoandroid.tcc.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.MyViewHolder> {
    List<Pedido> listaPedidos;
    Activity ac;
    DecimalFormat df = new DecimalFormat("0.00");

    public AdapterPedidos(List<Pedido> lista, Activity ac) {
        this.listaPedidos = lista;
        this.ac = ac;
    }

    @NonNull
    @Override
    public AdapterPedidos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pedidos, parent, false);

        return new AdapterPedidos.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterPedidos.MyViewHolder holder, final int position) {
        final Pedido pedido = listaPedidos.get(position);

        String aux = "Pedido N°" + String.valueOf(pedido.getId());
        holder.txtNumeroPedido.setText(aux);

        aux = "Pedido realizado em "+pedido.getData();
        holder.txtData.setText(aux);

        String status = "";
        if(pedido.getStatus() == 0){
            status = "Em andamento";
        } else if(pedido.getStatus() == 1){
            status = "Entrega em andamento";
        } else if(pedido.getStatus() == 2){
            status = "Entregue";
        } else {
            status = "Cancelado";
        }
        aux = "Status do pedido: " + status;
        holder.txtStatus.setText(aux);

        aux = "Loja: " + pedido.getLoja();
        holder.txtLoja.setText(aux);

        aux = "Valor total do pedido: R$" + df.format(pedido.getValorTotal()).replace('.',',');
        holder.txtValor.setText(aux);

    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNumeroPedido, txtData, txtStatus, txtLoja, txtValor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNumeroPedido = itemView.findViewById(R.id.txtNumeroPedido);
            txtData = itemView.findViewById(R.id.txtData);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtLoja = itemView.findViewById(R.id.txtLoja);
            txtValor = itemView.findViewById(R.id.txtValor);
        }
    }
}
