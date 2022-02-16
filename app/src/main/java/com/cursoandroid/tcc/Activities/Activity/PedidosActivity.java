package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cursoandroid.tcc.Activities.Adapter.AdapterPedidos;
import com.cursoandroid.tcc.Activities.Models.Pedido;
import com.cursoandroid.tcc.Activities.RecyclerItemClickListener;
import com.cursoandroid.tcc.Activities.Task.AsyncResponse;
import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;

import java.util.ArrayList;
import java.util.List;

public class PedidosActivity extends AppCompatActivity implements AsyncResponse {

    private RecyclerView recyclerView;
    private TextView txtVazio;
    private List<Pedido> listaPedidos = new ArrayList<>();
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaPedidos.clear();
        setContentView(R.layout.activity_pedidos);
        recyclerView = findViewById(R.id.recyclerView);
        txtVazio = findViewById(R.id.txtVazio);
        btnRefresh = findViewById(R.id.btnRefresh);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaPedidos.clear();
                onResume();
            }
        });

        String method = "selectPedidos";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        int idUsuario = ((VariaveisGlobais) this.getApplication()).getUsuarioLogado().getId();
        backgroundTask.execute(method, String.valueOf(idUsuario));
    }

    @Override
    public void processFinish(String output) {
        if(output.equals("n")){
            txtVazio.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else {
            txtVazio.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            String aux[] = output.split(";;;;");

            for (int i = 0; i < aux.length; i = i + 12) {
                Pedido pedido = new Pedido();
                pedido.setId(Integer.parseInt(aux[i]));
                pedido.setLoja(aux[i + 1]);
                pedido.setStatus(Integer.parseInt(aux[i + 2]));
                pedido.setValorTotal(Double.parseDouble(aux[i + 3]));

                String data = aux[i+4];
                String aux1[] = data.split("-");
                String ano = aux1[0];
                String mes = aux1[1];
                String aux2[] = aux1[2].split(" ");
                String dia = aux2[0];
                String dataNova = dia+"/"+mes+"/"+ano;
                pedido.setData(dataNova);

                pedido.setValorProdutos(Double.parseDouble(aux[i + 5]));
                pedido.setValorEntrega(Double.parseDouble(aux[i + 6]));
                pedido.setPagamento(aux[i + 7]);
                pedido.setApelido(aux[i+8]);
                pedido.setRua(aux[i+9]);
                pedido.setNumero(aux[i+10]);
                pedido.setBairro(aux[i+11]);
                listaPedidos.add(pedido);
            }

            AdapterPedidos adapter = new AdapterPedidos(listaPedidos, this);

            //Configurar RecycleView
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
            recyclerView.setAdapter(adapter);

            //Evento de clique
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            getApplicationContext(),
                            recyclerView,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Pedido pedido = listaPedidos.get(position);
                                    setVariavelGlobal(pedido);
                                    Intent intent = new Intent(getApplicationContext(), PedidoActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongItemClick(View view, int position) { }

                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { }
                            }
                    )
            );
        }
    }
    public void setVariavelGlobal(Pedido pedido){
        ((VariaveisGlobais) this.getApplication()).setPedidoSelecionado(pedido);
    }
}
