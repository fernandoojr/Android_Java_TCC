package com.cursoandroid.tcc.Activities.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.cursoandroid.tcc.Activities.Adapter.AdapterEnderecos;
import com.cursoandroid.tcc.Activities.Models.Endereco;
import com.cursoandroid.tcc.Activities.RecyclerItemClickListener;
import com.cursoandroid.tcc.Activities.Task.AsyncResponse;
import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cursoandroid.tcc.R;
import java.util.ArrayList;
import java.util.List;

public class SelecaoEnderecoActivity extends AppCompatActivity implements AsyncResponse {

    private List<Endereco> listaEnderecos = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView txtTexto;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_selecao_endereco);
        recyclerView = findViewById(R.id.recyclerView);
        txtTexto = findViewById(R.id.txtTexto);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setEnabled(false);
                Intent intent = new Intent(getApplicationContext(), CadastroEnderecoActivity.class);
                startActivity(intent);
            }
        });

        listaEnderecos.clear();

        String method = "selectEnderecos";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        int idUsuario = ((VariaveisGlobais) this.getApplication()).getUsuarioLogado().getId();
        backgroundTask.execute(method, String.valueOf(idUsuario));
    }

    @Override
    public void processFinish(String output) {
        if(! output.contains("Removido com sucesso")) {
            if (output.equals("n")) {
                txtTexto.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            } else {
                txtTexto.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                final String[][] aux = {output.split(";;;;")};


                for (int i = 0; i < aux[0].length; i = i + 7) {
                    Endereco endereco = new Endereco();
                    endereco.setId(Integer.parseInt(aux[0][i]));
                    endereco.setRua(aux[0][i + 1]);
                    endereco.setNumero(aux[0][i + 2]);
                    endereco.setBairro(aux[0][i + 3]);
                    endereco.setCidade(aux[0][i + 4]);
                    endereco.setComplemento(aux[0][i + 5]);
                    endereco.setApelido(aux[0][i + 6]);
                    listaEnderecos.add(endereco);
                }

                AdapterEnderecos adapter = new AdapterEnderecos(listaEnderecos);

                //Configurar RecycleView
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
                recyclerView.setAdapter(adapter);

                //evento de Click
                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(
                                getApplicationContext(),
                                recyclerView,
                                new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Endereco endereco = listaEnderecos.get(position);
                                        setVariavelGlobal(endereco);
                                        Intent intent = new Intent(getApplicationContext(), SelecionarSupermercadoActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        mostrarAlert(listaEnderecos.get(position).getId());
                                    }

                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    }
                                }
                        )
                );
            }
        } else {
            onResume();
        }
    }
    public void setVariavelGlobal(Endereco endereco){
        ((VariaveisGlobais) this.getApplication()).setEnderecoSelecionado(endereco);
    }

    public void mostrarAlert(final int idPedido){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Deseja remover o endereço?");
        dialog.setMessage("Ao confirmar não será possível recuperá-lo");
        dialog.setIcon(R.drawable.ic_delete_forever_black_24dp);
        dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String method = "deleteEndereco";
                BackgroundTask backgroundTask = new BackgroundTask(SelecaoEnderecoActivity.this);
                backgroundTask.delegate = SelecaoEnderecoActivity.this;
                backgroundTask.execute(method, String.valueOf(idPedido));
            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.create();
        dialog.show();
    }
}
