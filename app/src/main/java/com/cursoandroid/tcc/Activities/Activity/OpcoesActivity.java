package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cursoandroid.tcc.R;

public class OpcoesActivity extends AppCompatActivity {

    private Button btnInfo, btnCompra, btnPedidos, btnLoja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        btnInfo = findViewById(R.id.btnInfo);
        btnCompra = findViewById(R.id.btnCompra);
        btnPedidos = findViewById(R.id.btnPedidos);
        btnLoja = findViewById(R.id.btnLoja);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InformacoesActivity.class);
                startActivity(intent);
            }
        });

        btnCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelecaoEnderecoActivity.class);
                startActivity(intent);
            }
        });

         btnPedidos.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(), PedidosActivity.class);
                 startActivity(intent);
             }
         });

        btnLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastroLojaActivity.class);
                startActivity(intent);
            }
        });
    }
}
