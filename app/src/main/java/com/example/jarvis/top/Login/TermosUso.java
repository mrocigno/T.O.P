package com.example.jarvis.top.Login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jarvis.top.Login.Sessao.LoginBuilder.LoginBuilder;
import com.example.jarvis.top.R;


public class TermosUso extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_uso);

        this.activity = TermosUso.this;

    }
}
