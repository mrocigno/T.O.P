package com.example.jarvis.top.Main.Chamado;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.LoadingSettings;
import com.example.jarvis.top.Utils.StatusUtil;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.Chamados.ChamadosModel;
import com.example.jarvis.top.WebService.Models.Chamados.ResultChamadosModel;
import com.example.jarvis.top.WebService.Network;
import com.google.gson.Gson;

import java.io.File;

import lib.rocigno.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Chamado extends AppCompatActivity {

    PhotoPicker chamados_photoPicker;
    ResultChamadosModel item;

    TextView chamados_txtConteudo, chamados_txtPor, chamados_txtPara, chamados_txtData, chamados_txtStatus;
    ImageView chamados_imgStatus;

    Button chamados_btnFinalizar;
    EditText chamados_edtDetalhes;

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
        chamados_photoPicker = findViewById(R.id.chamados_photoPicker);
        chamados_edtDetalhes = findViewById(R.id.chamados_edtDetalhes);
        chamados_btnFinalizar = findViewById(R.id.chamados_btnFinalizar);


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

        chamados_btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LoadingSettings ls = new LoadingSettings(chamados_btnFinalizar, Button.class);
                ls.txtLoading(Chamado.this, 500, 4, "Carregando", false);
                ls.threadStart();

                Retrofit service = Network.teste();

                MultipartBody.Part img1 = null;
                MultipartBody.Part img2 = null;
                MultipartBody.Part img3 = null;
                RequestBody reqFile;
                File file;

                try{
                    file = new File(getPath(chamados_photoPicker.getArrayList().get(0).getImg()));
                    reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                    img1 = MultipartBody.Part.createFormData("img1", file.getName(), reqFile);

                    file = new File(getPath(chamados_photoPicker.getArrayList().get(1).getImg()));
                    reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                    img2 = MultipartBody.Part.createFormData("img2", file.getName(), reqFile);

                    file = new File(getPath(chamados_photoPicker.getArrayList().get(2).getImg()));
                    reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                    img3 = MultipartBody.Part.createFormData("img3", file.getName(), reqFile);
                }catch (Exception ignore){}


                RequestBody id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getID()));
                RequestBody detalhes = RequestBody.create(MediaType.parse("text/plain"), chamados_edtDetalhes.getText().toString().trim());

                Connects connects = service.create(Connects.class);
                connects.setResult(id, detalhes, img1, img2, img3).enqueue(new Callback<ChamadosModel>() {
                    @Override
                    public void onResponse(Call<ChamadosModel> call, Response<ChamadosModel> response) {
                        Toast.makeText(Chamado.this, "Finalizado com sucesso", Toast.LENGTH_SHORT).show();
                        ls.threadClose();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ChamadosModel> call, Throwable t) {
                        ls.threadClose();
                        finish();
                    }
                });
            }
        });
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
    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

}
