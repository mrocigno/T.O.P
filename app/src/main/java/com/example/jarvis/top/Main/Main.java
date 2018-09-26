package com.example.jarvis.top.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.top.CustomAlert.AlertTop;
import com.example.jarvis.top.CustomAlert.CustomBottomSheet;
import com.example.jarvis.top.Login.Login;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.Main.Adapters.Chamados.AdapterGridChamados;
import com.example.jarvis.top.Main.Adapters.Chamados.AdapterListChamados;
import com.example.jarvis.top.Main.Menu.Configuracoes;
import com.example.jarvis.top.Main.Menu.DataBaseConfig;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.LoadingSettings;
import com.example.jarvis.top.Utils.Utils;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.Chamados.ChamadosModel;
import com.example.jarvis.top.WebService.Network;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Activity activity;
    DrawerLayout drawer;
    ListView lstCds;
    LinearLayout lnlMin;
    GridView grdLot;
    TextView noConection;

    //Padrão será a lista
    View lista;
    int layout;

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

        lnlMin = findViewById(R.id.main_lnlMin);

        grdLot = createGrid();
        lstCds = createList();
        noConection = Network.createNoConection(activity);

        //por padrão a lista será "lista"
        this.lista = lstCds;
        this.layout = R.layout.adapter_chamados_main;
    }


    protected void initActions(){

//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                refreshChamados();
//            }
//        });
//        lstCds = new ListView(activity);
//        lnlMin.addView(lstCds);

    }

    AdapterView.OnItemLongClickListener listaAction = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            CustomBottomSheet bottomSheet = new CustomBottomSheet(activity);
            bottomSheet.add(R.drawable.ic_add_black_24dp, "Mais detalhes", new CustomBottomSheet.onClickAction() {
                @Override
                public void onItemSelected() {
                    Toast.makeText(activity, "Mais detalhes action", Toast.LENGTH_LONG).show();
                }
            }).add(R.drawable.ic_insert_comment_black_24dp, "Adicionar comentario", new CustomBottomSheet.onClickAction() {
                @Override
                public void onItemSelected() {
                    Toast.makeText(activity, "Adicionar comentario action", Toast.LENGTH_LONG).show();
                }
            }).add(R.drawable.ic_delete_forever_black_24dp, "Apagar chamado", new CustomBottomSheet.onClickAction() {
                @Override
                public void onItemSelected() {
                    Toast.makeText(activity, "Apagar chamado action", Toast.LENGTH_LONG).show();
                }
            }).show();
            return false;
        }
    };

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
                refreshChamados(lista, layout);
            }
        });

        alert.show();
    }

    private void refreshChamados(final View view, final int resource){
        LoadingSettings.showProgressBar(true, findViewById(R.id.header_pb));
        AbsListView.class.cast(view).setAdapter(null);

        lnlMin.removeView(noConection);
        final Retrofit retrofit = Network.teste();
        Connects con = retrofit.create(Connects.class);
        con.getChamados().enqueue(new Callback<ChamadosModel>() {
            @Override
            public void onResponse(Call<ChamadosModel> call, Response<ChamadosModel> response) {
                AbsListView.class.cast(view).setAdapter(new AdapterGridChamados(activity, resource, response.body().getResultado()));
                LoadingSettings.showProgressBar(false, findViewById(R.id.header_pb));
            }

            @Override
            public void onFailure(Call<ChamadosModel> call, Throwable t) {
                LoadingSettings.showProgressBar(false, findViewById(R.id.header_pb));
                lnlMin.addView(noConection);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        DataBaseConfig dbc = new DataBaseConfig(activity);
        String[] str = dbc.getData();
        if(dbc.getData() == null){
            dbc.setDefault();
            onResume();
        }else{
            switch (str[1]) {
                case "LISTA": {
                    addNewView(lstCds);
                    layout = R.layout.adapter_chamados_main;
                    refreshChamados(lista, layout);
                    break;
                }
                case "GRID": {
                    addNewView(grdLot);
                    layout = R.layout.adapter_chamdos_main_grid;
                    refreshChamados(lista, layout);
                    break;
                }
            }
        }
    }

    private GridView createGrid(){
        GridView grid = new GridView(activity);
        grid.setNumColumns(2);
        grid.setBackground(getDrawable(R.drawable.border_top_left));
        grid.setPadding(3,3,0,0);
        grid.setOnItemLongClickListener(listaAction);
        return grid;
    }

    private ListView createList(){
        ListView listView = new ListView(activity);
        listView.setOnItemLongClickListener(listaAction);

        return listView;
    }

    /**
     * Após usar essa função a variavel lista passa a ter o valor de @param view
     * @param view
     */
    private void addNewView(View view){
        lnlMin.removeView(lista);
        lista = view;
        lnlMin.addView(lista);
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
            refreshChamados(lista, layout);
            return true;
        }else if (id == R.id.action_filtrar) {
            callFilter();
            return true;
        }else if (id == R.id.action_configuracoes) {
            Utils.initActivity(activity, new Intent(activity, Configuracoes.class), false);
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

        if (id == R.id.nav_itemCos) {
            // Handle the camera action
        } else if (id == R.id.nav_itemHco) {

        } else if (id == R.id.nav_itemOes) {

        } else if (id == R.id.nav_itemCta) {

        } else if (id == R.id.nav_itemSir) {
            Sessao.deslogar(activity, Login.class);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
