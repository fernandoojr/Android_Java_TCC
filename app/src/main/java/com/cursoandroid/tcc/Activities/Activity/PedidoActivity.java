package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cursoandroid.tcc.Activities.Adapter.AdapterItem;
import com.cursoandroid.tcc.Activities.Models.ItemPedido;
import com.cursoandroid.tcc.Activities.Task.AsyncResponse;
import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity implements AsyncResponse {
    private TextView txtNumeroPedido, txtStatus, txtData, txtLoja, txtValorItens, txtValorEntrega, txtValorTotal, txtFormaPagamento, txtEndereco;
    private List<ItemPedido> listaItens = new ArrayList<>();
    private RecyclerView recyclerView;
    private DecimalFormat df = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        txtNumeroPedido = findViewById(R.id.txtNumeroPedido);
        txtStatus = findViewById(R.id.txtStatus);
        txtData = findViewById(R.id.txtData);
        txtLoja = findViewById(R.id.txtLoja);
        txtValorItens = findViewById(R.id.txtValorItens);
        txtValorEntrega = findViewById(R.id.txtValorEntrega);
        txtValorTotal = findViewById(R.id.txtValorTotal);
        txtFormaPagamento = findViewById(R.id.txtFormaPagamento);
        txtEndereco = findViewById(R.id.txtEndereco);
        recyclerView = findViewById(R.id.recyclerView);

        //Pegando status do pedido
        String status;
        if(((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getStatus() == 0){
            status = "Em andamento";
        } else if(((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getStatus() == 1){
            status = "Entrega em andamento";
        } else if(((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getStatus() == 2){
            status = "Entregue";
        } else {
            status = "Cancelado";
        }


        txtNumeroPedido.setText("Pedido número " +((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getId());
        txtStatus.setText("Status: "+status);
        txtData.setText("Realizado em "+((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getData());
        txtLoja.setText("Realizado em "+((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getLoja());
        txtValorItens.setText("Valor dos itens: R$"+ df.format( ((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getValorProdutos()).replace('.', ','));
        txtValorEntrega.setText("Valor da entrega: R$"+df.format( ((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getValorEntrega()).replace('.', ','));
        txtValorTotal.setText("Valor total do pedido: R$"+df.format( ((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getValorTotal()).replace('.', ','));
        txtFormaPagamento.setText("Forma de pagamento: "+((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getPagamento());
        txtEndereco.setText("Endereço de entrega: "+((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getRua() +
                "N° "+((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getNumero()+
                ", bairro "+((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getBairro());

        String method = "selectPedido";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        int idPedido = ((VariaveisGlobais) this.getApplication()).getPedidoSelecionado().getId();
        backgroundTask.execute(method, String.valueOf(idPedido));
    }

    @Override
    public void processFinish(String output) {

        String aux[] = output.split(";;;;");

        for (int i = 0; i < aux.length; i = i + 3) {
            ItemPedido item = new ItemPedido();
            item.setNome(aux[i]);
            item.setQuantidade(Integer.parseInt(aux[i + 1]));
            item.setPreco(Double.parseDouble(aux[i + 2]));
            listaItens.add(item);
        }

        AdapterItem adapter = new AdapterItem(listaItens, this);

        //Configurar RecycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}
