package com.cursoandroid.tcc.Activities.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.cursoandroid.tcc.Activities.Adapter.AdapterProdutos;
import com.cursoandroid.tcc.Activities.Models.FormasPagamento;
import com.cursoandroid.tcc.Activities.Models.Produto;
import com.cursoandroid.tcc.Activities.Task.AsyncResponse;
import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.tcc.R;

import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity implements AsyncResponse {


    private RecyclerView recyclerView;
    private List<Produto> listaProdutos = new ArrayList<>();
    private List<FormasPagamento> listaFormasPagamento = new ArrayList<>();
    AdapterProdutos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_produtos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        listaProdutos.clear();
        listaFormasPagamento.clear();

        String method = "selectProdutos";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        int idLoja = ((VariaveisGlobais) this.getApplication()).getLojaSelecionada().getId();
        backgroundTask.execute(method, String.valueOf(idLoja));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_produtos, menu);

        MenuItem busca = menu.findItem(R.id.busca);
        SearchView editBusca = (SearchView)busca.getActionView();

        editBusca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId() ){
            case R.id.itemCarrinho:
                Intent intent = new Intent(getApplicationContext(), CarrinhoActivity.class);
                startActivity(intent);
                break;

            case R.id.itemMeusPedidos:
                Intent intent1 = new Intent(getApplicationContext(), PedidosActivity.class);
                startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void processFinish(String output) {
        String aux[] = output.split(",,,,,");

        String produtosStringAux = aux[0];
        String produtosString[] = produtosStringAux.split(";;;;");

        String formasStringAux = aux[1];
        String formasString[] = formasStringAux.split(";;;;");

        for(int i = 0; i < produtosString.length; i = i+6){
            final Produto produto = new Produto();
            produto.setId(Integer.parseInt(produtosString[i]));
            produto.setCategoria(produtosString[i+1]);

            byte[] decodedString = Base64.decode(produtosString[i+2], Base64.DEFAULT);
            Bitmap imagem = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            produto.setImagem(imagem);

            produto.setNome(produtosString[i+3]);
            produto.setPreco(Double.parseDouble(produtosString[i+4]));
            produto.setQuantidade(Integer.parseInt(produtosString[i+5]));
            listaProdutos.add(produto);
        }


        for(int i = 0; i < formasString.length; i = i+2){
            FormasPagamento formasPagamento = new FormasPagamento();
            formasPagamento.setId(Integer.parseInt(formasString[i]));
            formasPagamento.setNome(formasString[i+1]);
            listaFormasPagamento.add(formasPagamento);
        }

        ((VariaveisGlobais) getApplication()).setListaFormasPagamentoSelecionada(listaFormasPagamento);
        ((VariaveisGlobais) getApplication()).setListaProdutos(listaProdutos);



        adapter = new AdapterProdutos(listaProdutos, this);
        //Configurar RecycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}
