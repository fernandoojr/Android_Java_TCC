package com.cursoandroid.tcc.Activities.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String, Void, String> {
    Context ctx;

    public AsyncResponse delegate = null;

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        if ( method.equals("insertItens") || method.equals("resgistroEndereco") || method.equals("updateTelefone")) {
            String data="";
            String reg_url = "";
            if (method.equals("resgistroEndereco")){
                reg_url = "https://mercadomobileonline.com.br/android/tcc/registroEndereco.php";
                String idUsuario = params[1];
                String rua = params[2];
                String numero = params[3];
                String bairro = params[4];
                String cidade = params[5];
                String complemento = params[6];
                String nome = params[7];
                try{
                    data = URLEncoder.encode("idUsuario", "UTF-8") + "=" + URLEncoder.encode(idUsuario, "UTF-8") + "&" +
                            URLEncoder.encode("rua", "UTF-8") + "=" + URLEncoder.encode(rua, "UTF-8") + "&" +
                            URLEncoder.encode("numero", "UTF-8") + "=" + URLEncoder.encode(numero, "UTF-8") + "&" +
                            URLEncoder.encode("bairro", "UTF-8") + "=" + URLEncoder.encode(bairro, "UTF-8") + "&" +
                            URLEncoder.encode("cidade", "UTF-8") + "=" + URLEncoder.encode(cidade, "UTF-8") + "&" +
                            URLEncoder.encode("complemento", "UTF-8") + "=" + URLEncoder.encode(complemento, "UTF-8") + "&" +
                            URLEncoder.encode("nome", "UTF-8") + "=" + URLEncoder.encode(nome, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(method.equals("insertItens")){
                reg_url = "https://mercadomobileonline.com.br/android/tcc/insertItens.php";
                String itens = params[1];
                String baixa = params[2];
                try {
                    data = URLEncoder.encode("itens", "UTF-8") + "=" + URLEncoder.encode(itens, "UTF-8") + "&" +
                            URLEncoder.encode("baixa", "UTF-8") + "=" + URLEncoder.encode(baixa, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(method.equals("updateTelefone")){
                reg_url = "https://mercadomobileonline.com.br/android/tcc/updateTelefone.php";
                String idUsuario = params[1];
                String telefone = params[2];
                try {
                    data = URLEncoder.encode("idUsuario", "UTF-8") + "=" + URLEncoder.encode(idUsuario, "UTF-8") + "&" +
                            URLEncoder.encode("telefone", "UTF-8") + "=" + URLEncoder.encode(telefone, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                httpURLConnection.disconnect();
                if(method.equals("insertItens")){
                    return "Pedido enviado com sucesso";
                }else if(method.equals("updateTelefone")){
                    return "Telefone atualizado com sucesso";
                }else{
                    return "Registrado com Sucesso";
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (method.equals("updateSenha") || method.equals("deleteEndereco") || method.equals("selectPedido") || method.equals("selectPedidos") || method.equals("verificaItens") || method.equals("insertPedido") || method.equals("selectFormas") || method.equals("registro") || method.equals("login") || method.equals("selectEnderecos") || method.equals("selectLojas") || method.equals("selectProdutos")){
            String select__url = "";
            String data ="";
            if(method.equals("login")){
                select__url = "https://mercadomobileonline.com.br/android/tcc/loginCriptografado.php";
                String email = params[1];
                String senha = params[2];
                try {
                    data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                            URLEncoder.encode("senha", "UTF-8") + "=" + URLEncoder.encode(senha, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(method.equals("selectEnderecos")){
                select__url = "https://mercadomobileonline.com.br/android/tcc/selectEnderecos.php";
                String idUsuario = params[1];
                try{
                    data = URLEncoder.encode("idUsuario", "UTF-8") + "=" + URLEncoder.encode(idUsuario, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(method.equals("selectLojas")){
                select__url = "https://mercadomobileonline.com.br/android/tcc/selectLojas.php";
                try{
                    data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if(method.equals("selectProdutos")){
                select__url = "https://mercadomobileonline.com.br/android/tcc/selectProdutos.php";
                String idLoja = params[1];
                try{
                    data = URLEncoder.encode("idLoja", "UTF-8") + "=" + URLEncoder.encode(idLoja, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if(method.equals("registro")){
                select__url = "https://mercadomobileonline.com.br/android/tcc/registroCriptografado.php";
                String nome = params[1];
                String email = params[2];
                String telefone = params[3];
                String dataNascimento = params[4];
                String senha = params[5];
                String isAdmin = params[6];
                String criadoEm = params[7];
                try {
                    data = URLEncoder.encode("nome", "UTF-8") + "=" + URLEncoder.encode(nome, "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                            URLEncoder.encode("telefone", "UTF-8") + "=" + URLEncoder.encode(telefone, "UTF-8") + "&" +
                            URLEncoder.encode("dataNascimento", "UTF-8") + "=" + URLEncoder.encode(dataNascimento, "UTF-8") + "&" +
                            URLEncoder.encode("senha", "UTF-8") + "=" + URLEncoder.encode(senha, "UTF-8") + "&" +
                            URLEncoder.encode("isAdmin", "UTF-8") + "=" + URLEncoder.encode(isAdmin, "UTF-8") + "&" +
                            URLEncoder.encode("criadoEm", "UTF-8") + "=" + URLEncoder.encode(criadoEm, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if(method.equals("insertPedido")){
                select__url = "https://mercadomobileonline.com.br/android/tcc/insertPedido.php";
                String idLoja = params[1];
                String idUsuario = params[2];
                String idEndereco = params[3];
                String idFormaPagamento = params[4];
                String valorProdutos = params[5];
                String valorEntrega = params[6];
                String valorTotal = params[7];
                String criadoEm = params[8];
                try {
                    data = URLEncoder.encode("idLoja", "UTF-8") + "=" + URLEncoder.encode(idLoja, "UTF-8") + "&" +
                            URLEncoder.encode("idUsuario", "UTF-8") + "=" + URLEncoder.encode(idUsuario, "UTF-8") + "&" +
                            URLEncoder.encode("idEndereco", "UTF-8") + "=" + URLEncoder.encode(idEndereco, "UTF-8") + "&" +
                            URLEncoder.encode("idFormaPagamento", "UTF-8") + "=" + URLEncoder.encode(idFormaPagamento, "UTF-8") + "&" +
                            URLEncoder.encode("valorProdutos", "UTF-8") + "=" + URLEncoder.encode(valorProdutos, "UTF-8") + "&" +
                            URLEncoder.encode("valorEntrega", "UTF-8") + "=" + URLEncoder.encode(valorEntrega, "UTF-8") + "&" +
                            URLEncoder.encode("valorTotal", "UTF-8") + "=" + URLEncoder.encode(valorTotal, "UTF-8") + "&" +
                            URLEncoder.encode("criadoEm", "UTF-8") + "=" + URLEncoder.encode(criadoEm, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if(method.equals("verificaItens")){
                select__url = "https://mercadomobileonline.com.br/android/tcc/verificaItens.php";
                String idLoja = params[1];
                try{
                    data = URLEncoder.encode("idLoja", "UTF-8") + "=" + URLEncoder.encode(idLoja, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if(method.equals("selectPedidos")){
                select__url = "https://mercadomobileonline.com.br/android/tcc/selectPedidos.php";
                String idUsuario = params[1];
                try{
                    data = URLEncoder.encode("idUsuario", "UTF-8") + "=" + URLEncoder.encode(idUsuario, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if(method.equals("selectPedido")){
                select__url = "https://mercadomobileonline.com.br/android/tcc/selectPedido.php";
                String idPedido = params[1];
                try{
                    data = URLEncoder.encode("idPedido", "UTF-8") + "=" + URLEncoder.encode(idPedido, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(method.equals("deleteEndereco")) {
                select__url = "https://mercadomobileonline.com.br/android/tcc/deleteEndereco.php";
                String idEndereco = params[1];
                try {
                    data = URLEncoder.encode("idEndereco", "UTF-8") + "=" + URLEncoder.encode(idEndereco, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            else if(method.equals("updateSenha")) {
                select__url = "https://mercadomobileonline.com.br/android/tcc/updateSenha.php";
                String idUsuario = params[1];
                String senhaNova = params[2];
                String senhaAntiga = params[3];
                try {
                    data = URLEncoder.encode("idUsuario", "UTF-8") + "=" + URLEncoder.encode(idUsuario, "UTF-8")+ "&" +
                            URLEncoder.encode("senhaNova", "UTF-8") + "=" + URLEncoder.encode(senhaNova, "UTF-8")+ "&" +
                            URLEncoder.encode("senhaAntiga", "UTF-8") + "=" + URLEncoder.encode(senhaAntiga, "UTF-8");
                ;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            try{
                URL url = new URL(select__url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String response = "";
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Registrado com Sucesso") || result.equals("Pedido enviado com sucesso") || result.equals("Telefone atualizado com sucesso")){
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }else{
            delegate.processFinish(result);
        }
    }
}