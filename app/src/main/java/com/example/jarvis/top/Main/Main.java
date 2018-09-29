package com.example.jarvis.top.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.example.jarvis.top.Main.Fragments.PageList;
import com.example.jarvis.top.Main.Fragments.PageMap;
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

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    PageList pageList;
    PageMap pageMap;

    Activity activity;
    DrawerLayout drawer;

    TextView noConection;


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
    }


    protected void initActions(){

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
            }
        });

        alert.show();
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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewer_teste);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.header_tabs);
        tabLayout.setVisibility(View.VISIBLE);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        tabLayout.addTab(tabLayout.newTab().setText("Lista"));
        tabLayout.addTab(tabLayout.newTab().setText("Mapa"));

        pageList = (PageList) mSectionsPagerAdapter.pages[0];
        pageMap = (PageMap) mSectionsPagerAdapter.pages[1];
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
            pageList.refreshChamados(pageList.lista, pageList.layout);
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public Fragment[] pages = {
                new PageList(),
                new PageMap()
        };

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return pages[position];
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return pages.length;
        }
    }
}
