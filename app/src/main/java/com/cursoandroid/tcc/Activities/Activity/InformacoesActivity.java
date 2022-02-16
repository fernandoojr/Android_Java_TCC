package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.tcc.Activities.Task.AsyncResponse;
import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class InformacoesActivity extends AppCompatActivity implements AsyncResponse {

    EditText txtNome, txtEmail, txtDataNascimento, txtTelefone, txtSenhaAntiga, txtNovaSenha, txtConfirmeNovaSenha;
    Button btnSalvarInfo, btnAlterarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);

        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtDataNascimento = findViewById(R.id.txtDataNascimento);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtSenhaAntiga = findViewById(R.id.txtSenhaAntiga);
        txtNovaSenha = findViewById(R.id.txtNovaSenha);
        txtConfirmeNovaSenha = findViewById(R.id.txtConfirmeNovaSenha);
        btnSalvarInfo = findViewById(R.id.btnSalvarInfo);
        btnAlterarSenha = findViewById(R.id.btnAlterarSenha);

        SimpleMaskFormatter smfTel = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTel = new MaskTextWatcher(txtTelefone, smfTel);
        txtTelefone.addTextChangedListener(mtwTel);

        txtNome.setText(((VariaveisGlobais) getApplication()).getUsuarioLogado().getNome());
        txtNome.setEnabled(false);
        txtEmail.setText(((VariaveisGlobais) getApplication()).getUsuarioLogado().getEmail());
        txtEmail.setEnabled(false);

        String data = ((VariaveisGlobais) getApplication()).getUsuarioLogado().getDataNascimento();
        String aux[] = data.split("-");
        String ano = aux[0];
        String mes = aux[1];
        String dia = aux[2];
        data = dia+"/"+mes+"/"+ano;
        txtDataNascimento.setText(data);
        txtDataNascimento.setEnabled(false);
        txtTelefone.setText(((VariaveisGlobais) getApplication()).getUsuarioLogado().getTelefone());

        btnSalvarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtTelefone.getText().equals(((VariaveisGlobais) getApplication()).getUsuarioLogado().getTelefone()) && txtTelefone.getText().length() == 14){
                    String method = "updateTelefone";
                    String telefone = txtTelefone.getText().toString();
                    String idUsuario = String.valueOf(((VariaveisGlobais) getApplication()).getUsuarioLogado().getId());
                    BackgroundTask backgroundTask = new BackgroundTask(InformacoesActivity.this);
                    backgroundTask.execute(method, idUsuario, telefone);
                    ((VariaveisGlobais) getApplication()).getUsuarioLogado().setTelefone(telefone);
                }
            }
        });

        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senhaAntiga = txtSenhaAntiga.getText().toString();
                String senhaNova = txtNovaSenha.getText().toString();
                String confirmeSenhaNova = txtConfirmeNovaSenha.getText().toString();
                if(senhaNova.equals(confirmeSenhaNova) && senhaNova.length() >= 8){
                    String method = "updateSenha";
                    String idUsuario = String.valueOf(((VariaveisGlobais) getApplication()).getUsuarioLogado().getId());
                    BackgroundTask backgroundTask = new BackgroundTask(InformacoesActivity.this);
                    backgroundTask.delegate = InformacoesActivity.this;
                    backgroundTask.execute(method, idUsuario, senhaNova, senhaAntiga);
                }else{
                    if(!senhaNova.equals(confirmeSenhaNova)){
                        Toast.makeText(InformacoesActivity.this, "As senhas não conferem!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(InformacoesActivity.this, "A senha deve possuir pelo menos 8 caracteres!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    public void processFinish(String output) {
            Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
    }
}
