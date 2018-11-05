package com.example.jarvis.top;

//Será que está funcionando agora?
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jarvis.top.CustomAlert.AlertTop;
import com.example.jarvis.top.Helpers.FirebaseSettings;
import com.example.jarvis.top.Login.Login;
import com.example.jarvis.top.Login.Sessao.LoginBuilder.LoginBuilder;
import com.example.jarvis.top.Login.Sessao.LoginBuilder.UserModel;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.Login.TermosUso;
import com.example.jarvis.top.Main.Main;
import com.example.jarvis.top.Utils.NotificationUtil;
import com.example.jarvis.top.Utils.SafeLog;
import com.example.jarvis.top.Utils.Utils;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.Login.LoginModel;
import com.example.jarvis.top.WebService.Network;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Splash extends AppCompatActivity {

    private Activity activity;
    private TextView txtApre;
    private boolean logado = false;
    private String Token;

    protected interface SplashCallBack{
        void onEnd();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.activity = Splash.this;

        Token = FirebaseInstanceId.getInstance().getToken();

        initNotificationSttings();

        txtApre = findViewById(R.id.splash_txtApre);
        LoginBuilder lb = new LoginBuilder(activity);
        lb.isLoged(new LoginBuilder.isLogedCallback() {
            @Override
            public void onIsLoged(UserModel userModel) {
                if(Token != null && Token.equals(userModel.getToken())){
                    verifieLogin(userModel.getUser(), userModel.getPassword(), userModel.getToken());
                }else{
                    verifieLogin(userModel.getUser(), userModel.getPassword(), Token);
                }
            }

            @Override
            public void onIsNotLoged() {
                logado = false;
                resumeSplashActions();
            }
        });
    }

    protected void waitSplash(final int mileSecs, final SplashCallBack callBack)  {
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(mileSecs);
                    callBack.onEnd();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

        }.start();
    }

    protected void verifieLogin(String user, String password, String token){
        Retrofit retrofit = Network.teste();
        Connects con = retrofit.create(Connects.class);

        con.loginPOST(user, password, token).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                LoginModel loginModel = response.body();
                assert loginModel != null;
                if(loginModel.getStatus() == 1){
                    Sessao.setId(loginModel.getResultado().getID());
                    Sessao.setToken(loginModel.getResultado().getToken());
                    Sessao.setNick(loginModel.getResultado().getNick());
                    Sessao.setNome_completo(loginModel.getResultado().getNome_Completo());

                    txtApre.setVisibility(View.VISIBLE);
                    txtApre.setText("Olá, " + loginModel.getResultado().getNome_Completo());
                    logado = true;
                    resumeSplashActions();
                }else{
                    logado = false;
                    resumeSplashActions();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                logado = false;
                resumeSplashActions();
            }
        });
    }

    public void resumeSplashActions(){
        final Intent intent = getIntent();
        if(!logado){
            if(getIntent().hasExtra("action")){
                getIntent().removeExtra("action");
            }
        }
        if(intent.hasExtra("action")){
            //Executa ações da intent
            switch(intent.getStringExtra("action")){
                case "logar":{
                    waitSplash(500, new SplashCallBack() {
                        @Override
                        public void onEnd() {
                            Utils.initActivity(activity, new Intent(Splash.this, Main.class), true);
                        }
                    });
                    break;
                }
                case "NovoChamado": {
                    txtApre.setText("Abrindo o chamado");
                    waitSplash(500, new SplashCallBack() {
                        @Override
                        public void onEnd() {
                            Intent main = new Intent(activity, Main.class);
                            main.putExtra("ID_Chamado", intent.getStringExtra("ID_Chamado"));
                            Utils.initActivity(activity, main, true);
                        }
                    });
                    break;
                }
            }
        }else{
            waitSplash(500, new SplashCallBack() {
                @Override
                public void onEnd() {
                    //Chama a proxima activity
                    Intent i;
                    if(logado){
                        i = new Intent(activity, Main.class);
                    }else{
                        i = new Intent(activity, Login.class);
                    }
                    Utils.initActivity(activity, i, true);
                }
            });
        }
    }

    protected void initNotificationSttings(){
        //Só criará se for acima da versão 8 (Oreo)
        NotificationUtil.createNotificationChannel(
                activity,
                getString(R.string.channel_chamados_id),
                getString(R.string.channel_chamados_nome),
                getString(R.string.channel_chamados_descricao),
                NotificationManager.IMPORTANCE_MAX,
                "1",
                "Chamados");
    }



}
