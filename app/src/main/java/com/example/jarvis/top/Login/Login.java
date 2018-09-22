package com.example.jarvis.top.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jarvis.top.CustomAlert.AlertTop;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.SafeLog;
import com.example.jarvis.top.Utils.Utils;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.LoginModel;
import com.example.jarvis.top.WebService.Models.Teste;
import com.example.jarvis.top.WebService.Network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    TextView txtTrms, txtFgtp;

    EditText edtUser, edtPass;

    Button btnLogn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVars();
        initActions();
    }

    protected void initVars(){
        txtFgtp = findViewById(R.id.login_txtFgtp);
        txtTrms = findViewById(R.id.login_txtTrms);

        edtUser = findViewById(R.id.login_edtUser);
        edtPass = findViewById(R.id.login_edtPass);

        btnLogn = findViewById(R.id.login_btnLogn);
    }

    protected void initActions(){
        btnLogn.setOnClickListener(btnLognAction);
    }

    View.OnClickListener btnLognAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // if(validateFields()){
                //Validações feitas
                executeLogin("","");

//            }else{
//                AlertTop.CustomTopSimpleAlert(Login.this, "Houve algum problema na hora de logar, verifique os erros e tente novamente", R.drawable.ic_warning_theme_24dp, 3000);
//            }
        }
    };

    /**
     * Valida os campos do Login
     * @return
     */
    protected boolean validateFields(){
        boolean[] ok = {
                Utils.verifieEmail(edtUser, "O usuário é invalido"),
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

    protected void executeLogin(String user, String Password){
        //Aqui vai ter a conexão com o WebService pra validar o login
        //Por enquanto vai ter só teste mesmo

        Retrofit retrofit = Network.teste();

        Connects con = retrofit.create(Connects.class);

        con.loginPOST("rocignom","MadrinhA!!1").enqueue(new Callback<Teste>() {
            @Override
            public void onResponse(Call<Teste> call, Response<Teste> response) {
                SafeLog.Logd(response.body().getMensagem());
                SafeLog.Logd("TESTEEEEEE");
            }

            @Override
            public void onFailure(Call<Teste> call, Throwable t) {
                SafeLog.Loge(t.getMessage(), t);
            }
        });

//        con.login().enqueue(new Callback<Teste>() {
//            @Override
//            public void onResponse(Call<Teste> call, Response<Teste> response) {
//                SafeLog.Logd(response.body().getMensagem());
//                SafeLog.Logd("TESTEEEEEE");
//            }
//
//            @Override
//            public void onFailure(Call<Teste> call, Throwable t) {
//                SafeLog.Loge(t.getMessage(), t);
//            }
//        });

//        con.teesstee().enqueue(new Callback<ArrayList<LoginModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<LoginModel>> call, Response<ArrayList<LoginModel>> response) {
//                ArrayList<LoginModel> teste = response.body();
//                SafeLog.Logd(teste.get(0).getNick() + "");
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<LoginModel>> call, Throwable t) {
//
//            }
//        });
    }

}
