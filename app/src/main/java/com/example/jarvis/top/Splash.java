package com.example.jarvis.top;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jarvis.top.Login.Login;
import com.example.jarvis.top.Login.Sessao.CustomLoginVerifier;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.Login.TermosUso;

public class Splash extends AppCompatActivity {

    private Activity activity;

    protected interface SplashCallBack{
        void onEnd();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.activity = Splash.this;

//        Toast.makeText(activity, Sessao.getLogin(), Toast.LENGTH_LONG).show();

        Intent intent = getIntent();
        if(intent.hasExtra("action")){
            //Executa algo, provavelmente de notificações
            switch(intent.getStringExtra("action")){
                case "logar":{
                    waitSplash(3000, new SplashCallBack() {
                        @Override
                        public void onEnd() {
                            startActivity(new Intent(Splash.this, TermosUso.class));
                            finish();
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
                    CustomLoginVerifier loginVerifier = new CustomLoginVerifier(activity);
                    loginVerifier.getLogin(TermosUso.class, Login.class);
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
