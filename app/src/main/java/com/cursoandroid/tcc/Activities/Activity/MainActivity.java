package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cursoandroid.tcc.Activities.Models.Usuario;
import com.cursoandroid.tcc.Activities.Task.AsyncResponse;
import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.Activities.VariaveisGlobais;
import com.cursoandroid.tcc.R;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private TextInputEditText txtEmail, txtSenha;
    private String email, senha;
    private Button btnLogin, btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmail = findViewById(R.id.txtNome);
        txtSenha = findViewById(R.id.txtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCadastro);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnLogin.setEnabled(true);
        btnCadastro.setEnabled(true);
    }

    public void login(View view){
        btnLogin.setEnabled(false);
        btnCadastro.setEnabled(false);

        email = txtEmail.getText().toString();
        senha = txtSenha.getText().toString();

        String method = "login";

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        backgroundTask.execute(method, email, senha);
    }

    public void cadastro(View view){
        btnLogin.setEnabled(false);
        btnCadastro.setEnabled(false);

        Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(intent);
    }

    @Override
    public void processFinish(String output) {
        if(output.equals("n")){
            Toast.makeText(getApplicationContext(), "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
            btnLogin.setEnabled(true);
            btnCadastro.setEnabled(true);
        }else{
            Usuario usuario = new Usuario();

            String aux[] = output.split(";;;;");

            for(int i = 0; i < aux.length; i = i+5){
                usuario.setId(Integer.parseInt(aux[i]));
                usuario.setEmail(aux[i+1]);
                usuario.setNome(aux[i+2]);
                usuario.setTelefone(aux[i+3]);
                usuario.setDataNascimento(aux[i+4]);
            }
            ((VariaveisGlobais) this.getApplication()).setUsuarioLogado(usuario);

            Intent intent = new Intent(getApplicationContext(), OpcoesActivity.class);
            startActivity(intent);

        }

    }
}