package com.cursoandroid.tcc.Activities.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cursoandroid.tcc.Activities.Task.AsyncResponse;
import com.cursoandroid.tcc.Activities.Task.BackgroundTask;
import com.cursoandroid.tcc.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroActivity extends AppCompatActivity implements AsyncResponse{

    private TextInputEditText txtNome, txtEmail, txtTelefone, txtDataNascimento, txtSenha, txtConfirmeSenha;
    private Button btnCadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtDataNascimento = findViewById(R.id.txtDataNascimento);
        txtSenha = findViewById(R.id.txtSenha);
        txtConfirmeSenha = findViewById(R.id.txtConfirmeSenha);
        btnCadastro = findViewById(R.id.btnCadastro);

        //Adicionando mascara aos campos telefone e data de nascimento
        SimpleMaskFormatter smfTel = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTel = new MaskTextWatcher(txtTelefone, smfTel);
        txtTelefone.addTextChangedListener(mtwTel);

        SimpleMaskFormatter smfDt = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwDt = new MaskTextWatcher(txtDataNascimento, smfDt);
        txtDataNascimento.addTextChangedListener(mtwDt);
    }

    public void cadastro(View view){
        btnCadastro.setEnabled(false);

        String email = txtEmail.getText().toString();
        String nome = txtNome.getText().toString();
        String telefone = txtTelefone.getText().toString();
        String dataNascimento = txtDataNascimento.getText().toString();
        String senha = txtSenha.getText().toString();
        String confirmeSenha = txtConfirmeSenha.getText().toString();

        if(nome.equals("") || nome.equals(null)){
            Toast.makeText(getApplicationContext(), "Preencha o nome!", Toast.LENGTH_LONG).show();
            btnCadastro.setEnabled(true);
        }else if(email.equals("") || email.equals(null) || !isValidEmailAddressRegex(email)){
            Toast.makeText(getApplicationContext(), "Preencha o e-mail corretamente!", Toast.LENGTH_LONG).show();
            btnCadastro.setEnabled(true);
        }else if(telefone.equals("") || telefone.equals(null) || telefone.length()!=14){
            Toast.makeText(getApplicationContext(), "Preencha o telefone corretamente!", Toast.LENGTH_LONG).show();
            btnCadastro.setEnabled(true);
        }else if(dataNascimento.equals("") || dataNascimento.equals(null) || !isValidDate(dataNascimento)){
            Toast.makeText(getApplicationContext(), "Preencha a data de nascimento corretamente!", Toast.LENGTH_LONG).show();
            btnCadastro.setEnabled(true);
        }else if(senha.equals("") || senha.equals(null) || senha.length() < 8){
            Toast.makeText(getApplicationContext(), "Preencha a senha corretamente com pelo menos 8 caracteres!", Toast.LENGTH_LONG).show();
            btnCadastro.setEnabled(true);
        }else{
            String aux[] = dataNascimento.split("/");
            String auxDt = aux[2] + "-" + aux[1] + "-" + aux[0];

            if (senha.equals(confirmeSenha)) {
                String method = "registro";

                String isAdmin = "0";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date data = Calendar.getInstance().getTime();
                String criadoEm = sdf.format(data);
                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.delegate = this;
                backgroundTask.execute(method, nome, email, telefone, auxDt, senha, isAdmin, criadoEm);
            } else {
                Toast.makeText(getApplicationContext(), "As senhas não conferem", Toast.LENGTH_LONG).show();
                btnCadastro.setEnabled(true);
            }
        }
    }

    @Override
    public void processFinish(String output) {
        if(output.equals("Dados inseridos com sucesso!")){
            Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "E-mail já cadastrado!", Toast.LENGTH_LONG).show();
            btnCadastro.setEnabled(true);
        }
    }

    public static boolean isValidEmailAddressRegex(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

    public static boolean isValidDate(String date) {
        boolean isDateValid = false;
        if(date.length() == 10) {
            String aux[] = date.split("/");
            int dia = Integer.parseInt(aux[0]);
            int mes = Integer.parseInt(aux[1]);
            int ano = Integer.parseInt(aux[2]);

            if (ano <= 2021) {
                if (mes <= 12 && mes >= 1) {
                    if (mes == 2) {
                        if (ano % 4 == 0) {
                            if (dia <= 29)
                                isDateValid = true;
                        }else{
                            if (dia <= 28)
                                isDateValid = true;
                        }

                    }else if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
                        if(dia <= 31)
                            isDateValid = true;
                    }else{
                        if(dia <= 30)
                            isDateValid = true;
                    }
                }
            }
        }
        return isDateValid;
    }
}
