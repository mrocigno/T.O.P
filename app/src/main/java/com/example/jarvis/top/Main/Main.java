package com.example.jarvis.top.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jarvis.top.Login.Login;
import com.example.jarvis.top.Login.Sessao.LoginBuilder.LoginBuilder;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.Main.Adapters.Chamados.AdapterChamados;
import com.example.jarvis.top.Main.Adapters.Chamados.ModelChamados;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.LoadingSettings;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.Chamados.ChamadosModel;
import com.example.jarvis.top.WebService.Models.Login.LoginModel;
import com.example.jarvis.top.WebService.Network;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Activity activity;
    DrawerLayout drawer;
    ListView lstCds;
    ImageView refresh;
    TextView txtCds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDefault();
        initVars();
        initActions();
    }

    protected void initVars(){
        activity = Main.this;
        lstCds = findViewById(R.id.main_lstCds);
        refresh = findViewById(R.id.main_imgAtt);
        txtCds = findViewById(R.id.main_txtCds);
    }

    protected void initActions(){
        refreshChamados();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshChamados();
            }
        });
    }







    protected void callFilter(){
        ViewHolder holder = new ViewHolder(R.layout.alert_filter);
        final DialogPlus alert = DialogPlus.newDialog(activity)
                .setContentHolder(holder)
                .setGravity(Gravity.TOP)
                .create();

        (holder.getInflatedView().findViewById(R.id.filter_btnSch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                refreshChamados();
            }
        });

        alert.show();
    }

    private void refreshChamados(){
        final LoadingSettings ls = new LoadingSettings(txtCds, TextView.class);
        ls.txtLoading(activity, 100, 4, "Carregando", true).threadStart();

        lstCds.setAdapter(null);

        Retrofit retrofit = Network.teste();
        Connects con = retrofit.create(Connects.class);
        con.getChamados().enqueue(new Callback<ChamadosModel>() {
            @Override
            public void onResponse(Call<ChamadosModel> call, Response<ChamadosModel> response) {
                lstCds.setAdapter(new AdapterChamados(activity, response.body().getResultado()));
                ls.threadClose();
            }

            @Override
            public void onFailure(Call<ChamadosModel> call, Throwable t) {

            }
        });
    }








































    /////////////////////////////////////////////////////////////////////////////////////////////////
    /***********************************************************************************************
     ****************************CRIADOS PELO SISTEMA***********************************************
     ***********************************************************************************************/
    ////////////////////////////////////////////////////////////////////////////////////////////////
    protected void initDefault(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.nav_txtAon)).setText("Olá, " + Sessao.getNome_completo());

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_atualizar) {
            refreshChamados();
            return true;
        }else if (id == R.id.action_filtrar) {
            callFilter();
            return true;
        }else if (id == R.id.action_configuracoes) {

            return true;
        }else if (id == R.id.action_sair) {
            Sessao.deslogar(activity, Login.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Sessao.deslogar(activity, Login.class);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
