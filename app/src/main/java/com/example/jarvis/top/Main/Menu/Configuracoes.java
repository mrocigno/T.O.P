package com.example.jarvis.top.Main.Menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.top.CustomAlert.AlertTop;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.Utils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;

public class Configuracoes extends AppCompatActivity {

    Activity activity;
    LinearLayout lnlFes;
    TextView lnlFes_txt;

    LinearLayout lnlMao;
    ImageView    lnlMao_img;

    DataBaseConfig dbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        this.activity = Configuracoes.this;
        dbc = new DataBaseConfig(activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initVars();
        initActions();
        initData();
    }



    protected void initVars(){
        lnlFes = findViewById(R.id.config_lnlFes);
        lnlFes_txt = findViewById(R.id.config_lnlFes_txt);

        lnlMao = findViewById(R.id.config_lnlMao);
        lnlMao_img = findViewById(R.id.config_lnlMao_img);
    }

    protected void initActions(){
        lnlFes.setOnClickListener(lnlFesAction);

        lnlMao.setOnClickListener(lnlMaoAction);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /***********************************************************************************************
     *******************************ACTIONS*********************************************************
     **********************************************************************************************/
    ////////////////////////////////////////////////////////////////////////////////////////////////

    View.OnClickListener lnlFesAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder holder = new ViewHolder(R.layout.alert_config_frequencia);
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

            final EditText text = holder.getInflatedView().findViewById(R.id.alertConfig_edtLop);
            text.setText(lnlFes_txt.getText().toString().trim().replace(" min", ""));

            (holder.getInflatedView().findViewById(R.id.alertConfig_btnCar)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                }
            });
            (holder.getInflatedView().findViewById(R.id.alertConfig_btnCir)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if(!text.getText().toString().trim().equals("")){
                            int vle = Integer.parseInt(text.getText().toString().trim());
                            dbc.setUpdate(vle);
                            initData();
                            alert.dismiss();
                        }else{
                            text.setError("Preencha este campo");
                        }

                    }catch (Exception e){
                        text.setError("Certifique-se de usar somente números neste campo");
                    }
                }
            });
            alert.show();

        }
    };

    View.OnClickListener lnlMaoAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder holder = new ViewHolder(R.layout.alert_config_exibicao);
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

            View.OnClickListener btnsExibicaoAction = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbc.setUpdate(String.valueOf(v.getTag()));
                    initData();
                    alert.dismiss();
                }
            };

            holder.getInflatedView().findViewById(R.id.alertConfig_ibtLta).setOnClickListener(btnsExibicaoAction);
            holder.getInflatedView().findViewById(R.id.alertConfig_ibtGid).setOnClickListener(btnsExibicaoAction);
            holder.getInflatedView().findViewById(R.id.alertConfig_ibtRle).setOnClickListener(btnsExibicaoAction);

            alert.show();
        }
    };


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /**---------------------------------------------------------------------------------------------
     -------------------------------ACTIONS---------------------------------------------------------
     ---------------------------------------------------------------------------------------------*/
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("SetTextI18n")
    protected void initData() {
        String[] str = dbc.getData();
        if(dbc.getData() == null){
            dbc.setDefault();
            initData();
        }else{
            lnlFes_txt.setText(str[0] + " min");
            switch (str[1]){
                case "LISTA":{
                    lnlMao_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_format_list_bulleted_black_24dp));
                    break;
                }
                case "GRID":{
                    lnlMao_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_grid_on_black_24dp));
                    break;
                }
                case "RECYCLER":{
                    lnlMao_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_recycle_black_24dp));
                    break;
                }
            }
        }
    }

    protected void lnlDesClick(LinearLayout lnl){
        lnl.setBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
