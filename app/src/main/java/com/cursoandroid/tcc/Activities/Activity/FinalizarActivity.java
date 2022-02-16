package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoandroid.tcc.Activities.Task.AsyncResponse;
import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FinalizarActivity extends AppCompatActivity implements AsyncResponse {
    private DecimalFormat df = new DecimalFormat("0.00");
    private Double valorProdutos = 0.0;
    private Double valorEntrega = 0.0;
    private Double valorTotal = 0.0;
    private TextView txtResultado;
    private RadioGroup radioGroup;
    private Button btnFinalizar;
    int auxint = 0;
    boolean ok = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar);

        txtResultado = findViewById(R.id.txtResultado);
        radioGroup = findViewById(R.id.radioGroup);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        //Carregando formas de pagamento
        RadioButton button;
        for(int i = 0; i < ((VariaveisGlobais) getApplication()).getListaFormasPagamentoSelecionada().size(); i++) {
            button = new RadioButton(this);
            button.setTextColor(getResources().getColor(R.color.branco));
            if(Build.VERSION.SDK_INT>=21)
            {
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{-android.R.attr.state_enabled}, //disabled
                                new int[]{android.R.attr.state_enabled} //enabled
                        },
                        new int[] {
                                Color.WHITE //disabled
                                ,Color.WHITE //enabled

                        }
                );
                button.setButtonTintList(colorStateList);
            }
            button.setText(((VariaveisGlobais) getApplication()).getListaFormasPagamentoSelecionada().get(i).getNome());
            button.setId(((VariaveisGlobais) getApplication()).getListaFormasPagamentoSelecionada().get(i).getId());
            radioGroup.addView(button);
        }

        //Pegando valor dos produtos
        for(int i=0; i<((VariaveisGlobais) getApplication()).getCarrinho().size(); i++){
            valorProdutos += ((VariaveisGlobais) getApplication()).getCarrinho().get(i).getPreco() * ((VariaveisGlobais) getApplication()).getCarrinho().get(i).getQuantidade();
        }

        //Pegando valor da entrega
        if (((VariaveisGlobais) getApplication()).getLojaSelecionada().getEntregaGratis() == 0){
            valorEntrega = ((VariaveisGlobais) getApplication()).getLojaSelecionada().getValorEntregaPadrao();
        }else{
            if(valorProdutos < ((VariaveisGlobais) getApplication()).getLojaSelecionada().getValorEntregaGratis()){
                valorEntrega = ((VariaveisGlobais) getApplication()).getLojaSelecionada().getValorEntregaPadrao();
            }
        }

        valorTotal = valorEntrega + valorProdutos;

        //Montando string das informações
        String info = "Entregar em: ";
        info += ((VariaveisGlobais) getApplication()).getEnderecoSelecionado().getApelido()
                +"\n\nRua: "+((VariaveisGlobais) getApplication()).getEnderecoSelecionado().getRua() + ", "
                +((VariaveisGlobais) getApplication()).getEnderecoSelecionado().getNumero()+"\n"
                +"Bairro: "+((VariaveisGlobais) getApplication()).getEnderecoSelecionado().getBairro()+"\n"
                +"\n\nValor total dos itens: R$ " + df.format(valorProdutos).replace('.',',')+"\n"
                +"Valor da entrega: R$ " + df.format(valorEntrega).replace('.',',')+"\n"
                +"\nValor total: R$ " +df.format(valorTotal).replace('.',',');

        txtResultado.setText(info);
    }

    public void finalizar(View view){
        if(radioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Selecione uma forma de pagamento!", Toast.LENGTH_LONG).show();
        }else {
            String method1 = "verificaItens";
            BackgroundTask backgroundTask1 = new BackgroundTask(this);
            backgroundTask1.delegate = this;
            backgroundTask1.execute(method1, String.valueOf(((VariaveisGlobais) getApplication()).getLojaSelecionada().getId()));
        }
    }

    @Override
    public void processFinish(String output) {
        if (auxint == 0) {
            String aux[] = output.split(";;;;");
            for (int i = 0; i < aux.length; i = i + 2) {
                for (int j = 0; j < ((VariaveisGlobais) getApplication()).getListaProdutos().size(); j++) {
                    if (Integer.parseInt(aux[i]) == ((VariaveisGlobais) getApplication()).getListaProdutos().get(j).getId()) {
                        ((VariaveisGlobais) getApplication()).getListaProdutos().get(j).setQuantidade(Integer.parseInt(aux[i + 1]));
                        break;
                    }
                }
            }

            for (int i = 0; i < ((VariaveisGlobais) getApplication()).getCarrinho().size(); i++) {
                for (int j = 0; j < ((VariaveisGlobais) getApplication()).getListaProdutos().size(); j++) {
                    if (((VariaveisGlobais) getApplication()).getCarrinho().get(i).getId() == ((VariaveisGlobais) getApplication()).getListaProdutos().get(j).getId()) {
                        if (((VariaveisGlobais) getApplication()).getCarrinho().get(i).getQuantidade() > ((VariaveisGlobais) getApplication()).getListaProdutos().get(j).getQuantidade()) {
                            ok = false;
                        }
                        break;
                    }
                }
            }

            if (ok == true) {

                auxint += 1;
                String method = "insertPedido";
                String idLoja = String.valueOf(((VariaveisGlobais) getApplication()).getLojaSelecionada().getId());
                String idUsuario = String.valueOf(((VariaveisGlobais) getApplication()).getUsuarioLogado().getId());
                String idEndereco = String.valueOf(((VariaveisGlobais) getApplication()).getEnderecoSelecionado().getId());
                String idFormaPagamento = String.valueOf(radioGroup.getCheckedRadioButtonId());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date data = Calendar.getInstance().getTime();
                String criadoEm = sdf.format(data);


                output = "";

                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.delegate = this;
                backgroundTask.execute(method, idLoja, idUsuario, idEndereco, idFormaPagamento, valorProdutos.toString(), valorEntrega.toString(), valorTotal.toString(), criadoEm);
            }else{
                Toast.makeText(this, "Algum item no seu carrinho excedeu o limite! Retorne ao carrinho para que a quantidade seja ajudasta automaticamente",Toast.LENGTH_LONG).show();
            }
        }

        if (auxint == 1) {
            if(output != "") {
                ((VariaveisGlobais) getApplication()).setUltimoPedidoCadastrado(Integer.parseInt(output));
                String itens = "";
                for (int i = 0; i < ((VariaveisGlobais) getApplication()).getCarrinho().size(); i++) {
                    if (i != 0) {
                        itens += ",";
                    }
                    itens += "(" + output + "," + ((VariaveisGlobais) getApplication()).getCarrinho().get(i).getId() + "," + ((VariaveisGlobais) getApplication()).getCarrinho().get(i).getQuantidade() + "," + ((VariaveisGlobais) getApplication()).getCarrinho().get(i).getPreco() + ")";
                }

                String baixa = "";
                for (int i = 0; i < ((VariaveisGlobais) getApplication()).getCarrinho().size(); i++) {
                    int idProduto = ((VariaveisGlobais) getApplication()).getCarrinho().get(i).getId();
                    int qt = ((VariaveisGlobais) getApplication()).getCarrinho().get(i).getQuantidade();
                    for (int j = 0; j < ((VariaveisGlobais) getApplication()).getListaProdutos().size(); j++) {
                        if (idProduto == ((VariaveisGlobais) getApplication()).getListaProdutos().get(j).getId()) {
                            int qtNova = ((VariaveisGlobais) getApplication()).getListaProdutos().get(j).getQuantidade() - qt;
                            baixa += "UPDATE produto_loja SET quantidade = " + qtNova + " WHERE id_loja = " + ((VariaveisGlobais) getApplication()).getLojaSelecionada().getId() + " and id_produto = " + idProduto + ";;;;;";
                            break;
                        }
                    }
                }

                String method = "insertItens";
                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.execute(method, itens, baixa);

                Intent intent = new Intent(getApplicationContext(), FinalizadoActivity.class);
                startActivity(intent);
                ((VariaveisGlobais) getApplication()).getCarrinho().clear();
                finish();
            }
        }
    }
}
