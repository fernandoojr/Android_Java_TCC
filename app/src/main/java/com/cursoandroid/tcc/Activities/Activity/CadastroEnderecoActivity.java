package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroEnderecoActivity extends AppCompatActivity {

    private TextInputEditText txtNome, txtRua, txtBairro, txtCidade, txtNumero, txtComplemento;
    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        txtNome = findViewById(R.id.txtNome);
        txtRua = findViewById(R.id.txtRua);
        txtBairro = findViewById(R.id.txtBairro);
        txtCidade = findViewById(R.id.txtCidade);
        txtNumero = findViewById(R.id.txtNumero);
        txtComplemento = findViewById(R.id.txtComplemento);
        btnConfirmar = findViewById(R.id.btnConfirmar);
    }

    public void cadastrarEndereco(View view){
        btnConfirmar.setEnabled(false);

        if(txtRua.getText().toString().equals("") || txtRua.getText().toString().equals(null)){
            Toast.makeText(getApplicationContext(), "Preencha a rua!", Toast.LENGTH_LONG).show();
            btnConfirmar.setEnabled(true);
        }else if(txtNumero.getText().toString().equals("") || txtNumero.getText().toString().equals(null)){
            Toast.makeText(getApplicationContext(), "Preencha o número!", Toast.LENGTH_LONG).show();
            btnConfirmar.setEnabled(true);
        }else if(txtBairro.getText().toString().equals("") || txtBairro.getText().toString().equals(null)){
            Toast.makeText(getApplicationContext(), "Preencha o bairro!", Toast.LENGTH_LONG).show();
            btnConfirmar.setEnabled(true);
        }else if(txtCidade.getText().toString().equals("") || txtCidade.getText().toString().equals(null)){
            Toast.makeText(getApplicationContext(), "Preencha a cidade!", Toast.LENGTH_LONG).show();
            btnConfirmar.setEnabled(true);
        }else if(txtNome.getText().toString().equals("") || txtNome.getText().toString().equals(null)){
            Toast.makeText(getApplicationContext(), "Preencha o nome!", Toast.LENGTH_LONG).show();
            btnConfirmar.setEnabled(true);
        }else{

            String idUsuario = String.valueOf(((VariaveisGlobais) this.getApplication()).getUsuarioLogado().getId());
            String rua = txtRua.getText().toString();
            String numero = txtNumero.getText().toString();
            String bairro = txtBairro.getText().toString();
            String cidade = txtCidade.getText().toString();
            String complemento = txtComplemento.getText().toString();
            if (complemento.equals("") || complemento.equals(null)) {
                complemento = "Sem complemento";
            }
            String nome = txtNome.getText().toString();
            String method = "resgistroEndereco";
            BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
            backgroundTask.execute(method, idUsuario, rua, numero, bairro, cidade, complemento, nome);
            finish();
        }
    }
}
