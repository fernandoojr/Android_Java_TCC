package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cursoandroid.tcc.Activities.Adapter.AdapterLojas;
import com.cursoandroid.tcc.Activities.Models.Loja;
import com.cursoandroid.tcc.Activities.RecyclerItemClickListener;
import com.cursoandroid.tcc.Activities.Task.AsyncResponse;
import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;

import java.util.ArrayList;
import java.util.List;

public class SelecionarSupermercadoActivity extends AppCompatActivity implements AsyncResponse {

    private List<Loja> listaLojas = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView txtN;

    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_selecionar_supermercado);

        recyclerView = findViewById(R.id.recyclerView);
        txtN = findViewById(R.id.txtN);

        listaLojas.clear();

        String method = "selectLojas";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        backgroundTask.execute(method);
    }

    @Override
    public void processFinish(String output) {
        if(output.equals("n") || output.equals("")){
            txtN.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else{
            txtN.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            String aux[] = output.split(";;;;");


            for(int i = 0; i < aux.length; i = i+12){
                Loja loja = new Loja();
                loja.setId(Integer.parseInt(aux[i]));
                loja.setRua(aux[i+1]);
                loja.setNumero(aux[i+2]);
                loja.setBairro(aux[i+3]);
                loja.setCidade(aux[i+4]);
                loja.setComplemento(aux[i+5]);
                loja.setNome(aux[i+6]);

                byte[] decodedString = Base64.decode(aux[i+7], Base64.DEFAULT);
                Bitmap imagem = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                loja.setLogo(imagem);

                loja.setValorEntregaPadrao(Double.parseDouble(aux[i+8]));
                loja.setEntregaGratis(Integer.parseInt(aux[i+9]));
                loja.setValorEntregaGratis(Double.parseDouble(aux[i+10]));
                loja.setPrevisao(Integer.parseInt(aux[i+11]));
                listaLojas.add(loja);
            }

            AdapterLojas adapter = new AdapterLojas(listaLojas);

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
                                    Loja loja = listaLojas.get(position);
                                    setVariavelGlobal(loja);
                                    Intent intent = new Intent(getApplicationContext(), ProdutosActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {
                                }

                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                }
                            }
                    )
            );
        }

    }
    public void setVariavelGlobal(Loja loja){
        try {
            if (loja.getId() != ((VariaveisGlobais) this.getApplication()).getLojaSelecionada().getId()) {
                ((VariaveisGlobais) this.getApplication()).getCarrinho().clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((VariaveisGlobais) this.getApplication()).setLojaSelecionada(loja);

    }
}
