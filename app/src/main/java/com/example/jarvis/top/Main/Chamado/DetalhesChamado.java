package com.example.jarvis.top.Main.Chamado;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.LoadingSettings;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.Chamados.ChamadoDetalhes;
import com.example.jarvis.top.WebService.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesChamado extends AppCompatActivity {

    WebView detalhes_webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_chamado);

        detalhes_webView = findViewById(R.id.detalhes_webView);
        detalhes_webView.getSettings().setJavaScriptEnabled(true);

        detalhes_webView.setWebViewClient(new WebViewClient() {
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LoadingSettings.showProgressBar(true, findViewById(R.id.header_pb));
            }

            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LoadingSettings.showProgressBar(false, findViewById(R.id.header_pb));
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                // Carregando um recurso
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Detalhes do chamado");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if(intent.hasExtra("id_chamado")){
            String id = intent.getStringExtra("id_chamado");

            detalhes_webView.loadUrl("http://tarefas-operacionais.esy.es/wsVizualizar_chamado.php?id=" + id);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
