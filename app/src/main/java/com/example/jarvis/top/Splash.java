package com.example.jarvis.top;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.jarvis.top.Login.Login;
import com.example.jarvis.top.Login.Sessao.LoginBuilder.LoginBuilder;
import com.example.jarvis.top.Login.Sessao.LoginBuilder.UserModel;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.Login.TermosUso;
import com.example.jarvis.top.Main.Main;
import com.example.jarvis.top.Utils.Utils;

public class Splash extends AppCompatActivity {

    private Activity activity;
    private TextView txtApre;
    private boolean logado = false;

    protected interface SplashCallBack{
        void onEnd();
    }
//testee
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.activity = Splash.this;
        txtApre = findViewById(R.id.splash_txtApre);
        LoginBuilder lb = new LoginBuilder(activity);
        lb.isLoged(new LoginBuilder.isLogedCallback() {
            @Override
            public void onIsLoged(UserModel userModel) {
                Sessao.setId(userModel.getId());
                Sessao.setNick(userModel.getUser());
                Sessao.setNome_completo(userModel.getUser_name());

                txtApre.setVisibility(View.VISIBLE);
                txtApre.setText("Olá, " + userModel.getUser_name());
                logado = true;
            }

            @Override
            public void onIsNotLoged() {
                logado = false;
            }
        });

        final Intent intent = getIntent();
        if(intent.hasExtra("action")){
            //Executa algo, provavelmente de notificações
            switch(intent.getStringExtra("action")){
                case "logar":{
//                    txtApre.setVisibility(View.VISIBLE);
                    waitSplash(3000, new SplashCallBack() {
                        @Override
                        public void onEnd() {
                            Utils.initActivity(activity, new Intent(Splash.this, Main.class), true);
                        }
                    });
                    break;
                }
            }

        }else{
            waitSplash(3000, new SplashCallBack() {
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

}
