package com.cursoandroid.tcc.Activities.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.tcc.Activities.Models.Loja;
import com.cursoandroid.tcc.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterLojas extends RecyclerView.Adapter<AdapterLojas.MyViewHolder> {

    private List<Loja> listaLojas;

    public AdapterLojas(List<Loja> lista) {
        this.listaLojas = lista;
    }

    @NonNull
    @Override
    public AdapterLojas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_loja, parent, false);

        return new AdapterLojas.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLojas.MyViewHolder holder, int position) {
        Loja loja = listaLojas.get(position);
        holder.nome.setText(loja.getNome());
        DecimalFormat df = new DecimalFormat("0.00");
        String entregaPadraoString = df.format(loja.getValorEntregaPadrao());
        holder.valorEntrega.setText("Valor da entrega: R$"+entregaPadraoString.replace('.',','));
        if(loja.getEntregaGratis() == 1){
            String entregaGratisString = df.format(loja.getValorEntregaGratis());
            holder.entregaGratis.setText("Entrega grátis a partir de R$"+entregaGratisString.replace('.',','));
        }else{
            holder.entregaGratis.setText("Esta loja não possui entrega grátis");
        }
        holder.txtPrevisao.setText("Previsão de entrega: "+loja.getPrevisao()+"min.");
        holder.imgLogo.setImageBitmap(loja.getLogo());
    }

    @Override
    public int getItemCount() {
        return listaLojas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome, valorEntrega, entregaGratis, txtPrevisao;
        ImageView imgLogo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNome);
            valorEntrega = itemView.findViewById(R.id.txtValorEntrega);
            entregaGratis = itemView.findViewById(R.id.txtEntregaGratis);
            imgLogo = itemView.findViewById(R.id.imgFoto);
            txtPrevisao = itemView.findViewById(R.id.txtPrevisao);
        }
    }
}
