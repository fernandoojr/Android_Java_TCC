package com.cursoandroid.tcc.Activities.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.cursoandroid.tcc.Activities.Adapter.AdapterCarrinho;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoandroid.tcc.R;

public class CarrinhoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public TextView txtVazio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_carrinho);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        txtVazio = findViewById(R.id.txtVazio);

        AdapterCarrinho adapter = new AdapterCarrinho(((VariaveisGlobais) getApplication()).getCarrinho(), this);

        for(int i=0; i<((VariaveisGlobais) getApplication()).getCarrinho().size(); i++){
            if(((VariaveisGlobais) getApplication()).getCarrinho().get(i).getQuantidade() == 0){
                ((VariaveisGlobais) getApplication()).getCarrinho().remove(i);
                i--;
            }
        }

        for (int i = 0; i < ((VariaveisGlobais) getApplication()).getCarrinho().size(); i++) {
            for (int j = 0; j < ((VariaveisGlobais) getApplication()).getListaProdutos().size(); j++) {
                if (((VariaveisGlobais) getApplication()).getCarrinho().get(i).getId() == ((VariaveisGlobais) getApplication()).getListaProdutos().get(j).getId()) {
                    if (((VariaveisGlobais) getApplication()).getCarrinho().get(i).getQuantidade() > ((VariaveisGlobais) getApplication()).getListaProdutos().get(j).getQuantidade()) {
                        ((VariaveisGlobais) getApplication()).getCarrinho().get(i).setQuantidade(((VariaveisGlobais) getApplication()).getListaProdutos().get(j).getQuantidade());
                    }
                    break;
                }
            }
        }

        //Configurar RecycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

        if( ((VariaveisGlobais) getApplication()).getCarrinho().isEmpty()){
            txtVazio.setVisibility(View.VISIBLE);
        }else{
            txtVazio.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_carrinho, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId() ){
            case R.id.itemFinalizar:
                if(((VariaveisGlobais) getApplication()).getCarrinho().isEmpty()){
                    Toast.makeText(this, "Seu carrinho está vazio", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), FinalizarActivity.class);
                    startActivity(intent);
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
