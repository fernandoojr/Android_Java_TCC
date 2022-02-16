package com.cursoandroid.tcc.Activities.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cursoandroid.tcc.Activities.Models.Endereco;
import com.cursoandroid.tcc.R;
import java.util.List;

public class AdapterEnderecos extends RecyclerView.Adapter<AdapterEnderecos.MyViewHolder> {
    private List<Endereco> listaEnderecos;

    public AdapterEnderecos(List<Endereco> lista) {
        this.listaEnderecos = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_endereco, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        Endereco endereco = listaEnderecos.get(position);
        holder.nome.setText(endereco.getApelido());
        holder.ruaNumero.setText(endereco.getRua() + ", "+endereco.getNumero());
        holder.bairroCidade.setText(endereco.getBairro()+", "+endereco.getCidade());
        holder.complemento.setText(endereco.getComplemento());
    }

    @Override
    public int getItemCount() {
        return listaEnderecos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome, ruaNumero, bairroCidade, complemento;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNome);
            ruaNumero = itemView.findViewById(R.id.txtRuaNumero);
            bairroCidade = itemView.findViewById(R.id.txtBairroCidade);
            complemento = itemView.findViewById(R.id.txtComplemento);
        }
    }
}