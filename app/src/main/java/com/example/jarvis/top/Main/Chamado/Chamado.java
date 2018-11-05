package com.example.jarvis.top.Main.Chamado;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.StatusUtil;
import com.example.jarvis.top.WebService.Models.Chamados.ResultChamadosModel;

import lib.rocigno.photopicker.PhotoPicker;

public class Chamado extends AppCompatActivity {

    PhotoPicker chamados_photoPicker;
    ResultChamadosModel item;

    TextView chamados_txtConteudo, chamados_txtPor, chamados_txtPara, chamados_txtData, chamados_txtStatus;
    ImageView chamados_imgStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamado);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chamados_txtPor = findViewById(R.id.chamados_txtPor);
        chamados_txtPara = findViewById(R.id.chamados_txtPara);
        chamados_txtData = findViewById(R.id.chamados_txtData);
        chamados_txtStatus = findViewById(R.id.chamados_txtStatus);
        chamados_imgStatus = findViewById(R.id.chamados_imgStatus);
        chamados_txtConteudo = findViewById(R.id.chamados_txtConteudo);

        Intent intent = getIntent();
        if(intent.hasExtra("item")){
            item = (ResultChamadosModel) intent.getSerializableExtra("item");
            setTitle(item.getTitulo());
            chamados_txtPor.setText(item.getDe());
            chamados_txtPara.setText(item.getPara());
            chamados_txtData.setText(item.getPostadoEm());
            chamados_txtConteudo.setText(item.getConteudo());

            StatusUtil status = new StatusUtil(Integer.parseInt(item.getStatus()));
            chamados_txtStatus.setText(status.getMensagem());
            chamados_txtStatus.setTextColor(getColor(status.getResourceColor()));
            chamados_imgStatus.setImageDrawable(getDrawable(status.getResourceImage()));
            chamados_imgStatus.setColorFilter(getColor(status.getResourceColor()));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chamados_photoPicker = findViewById(R.id.chamados_photoPicker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        chamados_photoPicker.result(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
