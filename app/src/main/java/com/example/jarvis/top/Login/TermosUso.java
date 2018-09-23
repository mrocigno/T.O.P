package com.example.jarvis.top.Login;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jarvis.top.Login.Sessao.CustomLoginVerifier;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Splash;
import com.master.killercode.loginverifier.ListActivity.GetDataBase;
import com.master.killercode.loginverifier.ListActivity.UserModel;
import com.master.killercode.loginverifier.LoginVerifier;

import java.util.Arrays;
import java.util.List;

public class TermosUso extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_uso);

        this.activity = TermosUso.this;
        final CustomLoginVerifier lv = new CustomLoginVerifier(activity);

        ((Button) findViewById(R.id.btnTeste)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                lv.inLogout();
                lv.inLogout(Login.class);
            }
        });
    }
}
