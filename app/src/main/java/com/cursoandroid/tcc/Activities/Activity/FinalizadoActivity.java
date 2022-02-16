package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;

public class FinalizadoActivity extends AppCompatActivity {

    private TextView txtTitulo;
    Button btnPedido, btnNovaCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizado);

        txtTitulo = findViewById(R.id.txtTitulo);
        btnPedido = findViewById(R.id.btnPedido);
        btnNovaCompra = findViewById(R.id.btnNovaCompra);

        int pedido = ((VariaveisGlobais) getApplication()).getUltimoPedidoCadastrado();
        txtTitulo.setText("Pedido N° "+pedido+" enviado com sucesso ao estabelecimento!");

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PedidosActivity.class);
                startActivity(intent);
            }
        });

        btnNovaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelecionarSupermercadoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
