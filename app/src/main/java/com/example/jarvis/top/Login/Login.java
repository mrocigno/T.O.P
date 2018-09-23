package com.example.jarvis.top.Login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jarvis.top.CustomAlert.AlertTop;
import com.example.jarvis.top.Login.Sessao.CustomLoginVerifier;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Splash;
import com.example.jarvis.top.Utils.LoadingSettings;
import com.example.jarvis.top.Utils.SafeLog;
import com.example.jarvis.top.Utils.Utils;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.Teste;
import com.example.jarvis.top.WebService.Network;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    TextView txtTrms, txtFgtp;

    EditText edtUser, edtPass;

    Button btnLogn;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVars();
        initActions();
    }

    protected void initVars(){
        activity = Login.this;

        txtFgtp = findViewById(R.id.login_txtFgtp);
        txtTrms = findViewById(R.id.login_txtTrms);

        edtUser = findViewById(R.id.login_edtUser);
        edtPass = findViewById(R.id.login_edtPass);

        btnLogn = findViewById(R.id.login_btnLogn);
    }

    protected void initActions(){
        btnLogn.setOnClickListener(btnLognAction);
        edtPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                loginAction();
                return false;
            }
        });
    }

    View.OnClickListener btnLognAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginAction();
        }
    };

    /**
     * Valida os campos do Login
     * @return
     */
    protected boolean validateFields(){
        boolean[] ok = {
//                Utils.verifieEmail(edtUser, "O usuário é invalido"),
                Utils.verifieEmptyField(edtUser, "Esse campo não pode ficar em branco"),
                Utils.verifieEmptyField(edtPass,"Esse campo não pode ficar em branco")
        };

        for(boolean nOk:ok){
            if(!nOk){
                return false;
            }
        }
        return true;
    }

    protected void loginAction(){
        if(validateFields()){
            //Validações feitas
            executeLogin(edtUser.getText().toString().trim(),edtPass.getText().toString().trim());

        }else{
            AlertTop.CustomTopSimpleAlert(Login.this, "Houve algum problema na hora de logar, verifique os erros e tente novamente", R.drawable.ic_warning_theme_24dp, 4000);
        }
    }

    protected void executeLogin(String user, String password){
        //Aqui vai ter a conexão com o WebService pra validar o login
        //Por enquanto vai ter só teste mesmo

        final LoadingSettings ls = new LoadingSettings(btnLogn);
        ls.txtLoading(activity, 1000, 4).threadStart();

        Retrofit retrofit = Network.teste();

        Connects con = retrofit.create(Connects.class);

        con.loginPOST(user, password).enqueue(new Callback<Teste>() {
            @Override
            public void onResponse(Call<Teste> call, Response<Teste> response) {
                Teste teste = response.body();
                if(teste.getStatus() == 1){
//                    AlertTop.CustomTopSimpleAlert(activity, "Logado", R.drawable.ic_launcher_background, 4000);

                    CustomLoginVerifier loginVerifier = new CustomLoginVerifier(activity);

                    loginVerifier.inLogin(teste.getResultado().getNome_Completo(), teste.getResultado().getNick(), teste.getResultado().getSenha());

                    Intent i = new Intent(activity, Splash.class);
                    i.putExtra("action", "logar");
                    startActivity(i);
                }else{
                    AlertTop.CustomTopSimpleAlert(activity, teste.getMensagem(), R.drawable.ic_launcher_background, 4000);
                }
                ls.threadClose();
            }

            @Override
            public void onFailure(Call<Teste> call, Throwable t) {
                SafeLog.Loge(t.getMessage(), t);
                AlertTop.CustomTopSimpleAlert(Login.this, "Houve algum problema na hora de logar, verifique se está conectado a rede e tente novamente", R.drawable.ic_warning_theme_24dp, 4000);
                ls.threadClose();
            }
        });
    }

}
