package com.example.jarvis.top;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jarvis.top.Login.Login;

public class Splash extends AppCompatActivity {

    protected interface SplashCallBack{
        void onEnd();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = getIntent();
        if(intent.hasExtra("actions")){
            //Executa algo, provavelmente de notificações
            Toast.makeText(Splash.this, "Todos somos tops", Toast.LENGTH_LONG).show();
        }else{
            waitSplash(3000, new SplashCallBack() {
                @Override
                public void onEnd() {
                    //Chama a proxima activity
                    startActivity(new Intent(Splash.this, Login.class));
                    finish();
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
