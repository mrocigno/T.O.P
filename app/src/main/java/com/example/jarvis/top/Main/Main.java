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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.top.CustomAlert.CustomBottomSheet;
import com.example.jarvis.top.Login.Login;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.Main.Adapters.Chamados.AdapterGridChamados;
import com.example.jarvis.top.Main.Menu.Configuracoes;
import com.example.jarvis.top.Main.Menu.DataBaseConfig;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.LoadingSettings;
import com.example.jarvis.top.Utils.Utils;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.Chamados.ChamadosModel;
import com.example.jarvis.top.WebService.Models.Chamados.ComentariosChamadosModel;
import com.example.jarvis.top.WebService.Models.Chamados.ResultChamadosModel;
import com.example.jarvis.top.WebService.Models.Default;
import com.example.jarvis.top.WebService.Network;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnDismissListener;
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
    LinearLayout lnlMin;
    GridView grdLot;
    TextView noConection;

    ArrayList<ResultChamadosModel> list;

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

            final ResultChamadosModel resultChamadosModel = list.get(position);

            bottomSheet.add(R.drawable.ic_add_black_24dp, "Mais detalhes", new CustomBottomSheet.onClickAction() {
                @Override
                public void onItemSelected() {
                    Toast.makeText(activity, resultChamadosModel.getStatus(), Toast.LENGTH_LONG).show();
                }
            });

            if(resultChamadosModel.getTem_comentario() > 0){
                bottomSheet.add(R.drawable.ic_chat_bubble_outline_black_24dp, "ler comentario", new CustomBottomSheet.onClickAction() {
                    @Override
                    public void onItemSelected() {
                        //Toast.makeText(activity, resultChamadosModel.getStatus(), Toast.LENGTH_LONG).show();
                        lerComentario(resultChamadosModel.getID());
                    }
                });
            }

            bottomSheet.add(R.drawable.ic_insert_comment_black_24dp, "Adicionar comentario", new CustomBottomSheet.onClickAction() {
                @Override
                public void onItemSelected() {
                    //Toast.makeText(activity, resultChamadosModel.getStatus(), Toast.LENGTH_LONG).show();
                    addComentario(resultChamadosModel);
                }
            });
            bottomSheet.add(R.drawable.ic_delete_forever_black_24dp, "Apagar chamado", new CustomBottomSheet.onClickAction() {
                @Override
                public void onItemSelected() {
                    Toast.makeText(activity, resultChamadosModel.getStatus(), Toast.LENGTH_LONG).show();
                }
            });

            bottomSheet.show();

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
                assert response.body() != null;
                list = response.body().getResultado();
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

    public void lerComentario(final int id){
        ViewHolder holder = new ViewHolder(R.layout.alert_ler_comentario);
        final DialogPlus alert = DialogPlus.newDialog(activity)
                .setContentHolder(holder)
                .setGravity(Gravity.TOP)
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {
                        Utils.hideKeyboard(activity, dialog.getHolderView());
                    }
                })
                .create();

        final TextView txt = holder.getInflatedView().findViewById(R.id.alertComentLer_txtVew);
        final LoadingSettings ls = new LoadingSettings(txt, TextView.class);
        ls.txtLoading(activity, 100, 6, "Carregando", true).threadStart();

        Connects con = Network.teste().create(Connects.class);
        con.getComentarioPOST(String.valueOf(id)).enqueue(new Callback<ComentariosChamadosModel>() {
            @Override
            public void onResponse(Call<ComentariosChamadosModel> call, Response<ComentariosChamadosModel> response) {
                ComentariosChamadosModel resultado = response.body();
                ls.threadClose();
                if(resultado.getStatus() == 1){
                    ArrayList<ComentariosChamadosModel.Resultado> comentarios = resultado.getResultado();
                    if(comentarios != null){
                        String coments = "";
                        for (int i = 0; i < comentarios.size(); i++) {
                            coments += (i==0?"":"\n\n") + "Comentário "+ (i+1) +": \n" + comentarios.get(i).getComentario();
                        }
                        txt.setText(coments);
                    }
                }
            }

            @Override
            public void onFailure(Call<ComentariosChamadosModel> call, Throwable t) {
                ls.threadClose();
            }
        });

        holder.getInflatedView().findViewById(R.id.alertComentLer_btnCir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        alert.show();
    }

    public void addComentario(final ResultChamadosModel model){
        final ViewHolder holder = new ViewHolder(R.layout.alert_add_comentario);
        final DialogPlus alert = DialogPlus.newDialog(activity)
                .setContentHolder(holder)
                .setGravity(Gravity.TOP)
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {
                        //Não sei por que, mas não estava funcionando colocar dialog.getHolderView, então coloquei qualquer coisa mesmo
                        Utils.hideKeyboard(activity, findViewById(R.id.header_pb));
                    }
                })
                .create();

        final EditText edtCio = holder.getInflatedView().findViewById(R.id.alertComent_edtCio);
        holder.getInflatedView().findViewById(R.id.alertComent_btnCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        holder.getInflatedView().findViewById(R.id.alertComent_btnCir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar(model, edtCio.getText().toString().trim());
                alert.dismiss();
            }
        });

        alert.show();
    }

    public void salvar(final ResultChamadosModel model, String comentario){
        Retrofit retrofit = Network.teste();
        Connects add = retrofit.create(Connects.class);

        add.addComentarioPOST(String.valueOf(model.getID()), comentario).enqueue(new Callback<Default>() {
            @Override
            public void onResponse(Call<Default> call, Response<Default> response) {
                Toast.makeText(activity, response.body().getMensagem(), Toast.LENGTH_LONG).show();
                model.setTem_comentario(1);
            }

            @Override
            public void onFailure(Call<Default> call, Throwable t) {
                Toast.makeText(activity, "Parece que n foi", Toast.LENGTH_LONG).show();
                Log.e("TESTE", t.getMessage());
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

        switch (id){
            case R.id.nav_itemCos: {

                break;
            }
            case R.id.nav_itemHco:{

                break;
            }
            case R.id.nav_itemOes: {
                Utils.initActivity(activity, new Intent(activity, Configuracoes.class), false);
                break;
            }
            case R.id.nav_itemCta:{

                break;
            }
            case R.id.nav_itemSir:{
                Sessao.deslogar(activity, Login.class);
                break;
            }
            default:{
                drawer.setSelected(false);
                break;
            }
        }
        drawer.setSelected(false);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
