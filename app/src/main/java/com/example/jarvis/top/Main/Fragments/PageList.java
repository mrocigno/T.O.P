package com.example.jarvis.top.Main.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.top.CustomAlert.CustomBottomSheet;
import com.example.jarvis.top.CustomAlert.CustomBottomSheetBehavior;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.Main.Adapters.Chamados.AdapterGridChamados;
import com.example.jarvis.top.Main.Chamado.Chamado;
import com.example.jarvis.top.Main.Main;
import com.example.jarvis.top.Main.Menu.DataBaseConfig;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.LoadingSettings;
import com.example.jarvis.top.Utils.NotificationUtil;
import com.example.jarvis.top.Utils.StatusUtil;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PageList extends Fragment {

    public FrameLayout frameLayout;
    Activity activity;
    ArrayList<ResultChamadosModel> list;

    ListView lstCds;
    GridView grdLot;
    TextView noConection;
    Main.BehaviorItens behaviorItens;

//    CustomBottomSheetBehavior cbsb = new CustomBottomSheetBehavior();

    //Padrão será a lista
    public View lista;
    public int layout;

    public PageList() {
        // Required empty public constructor
    }

    public Main.BehaviorItens getBehaviorItens() {
        return behaviorItens;
    }

    public void setBehaviorItens(Main.BehaviorItens behaviorItens) {
        this.behaviorItens = behaviorItens;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_list, container, false);
        this.activity = getActivity();
        frameLayout = view.findViewById(R.id.pageList_frame);

        grdLot = createGrid();
        lstCds = createList();
        noConection = Network.createNoConection(activity);

        return view;
    }


    public void refreshChamados(final View view, final int resource){
        LoadingSettings.showProgressBar(true, activity.findViewById(R.id.header_pb));
        AbsListView.class.cast(view).setAdapter(null);

        frameLayout.removeView(noConection);
        final Retrofit retrofit = Network.teste();
        Connects con = retrofit.create(Connects.class);
        con.getChamadosExternos(String.valueOf(Sessao.getId())).enqueue(new Callback<ChamadosModel>() {
            @Override
            public void onResponse(Call<ChamadosModel> call, Response<ChamadosModel> response) {
                assert response.body() != null;
                list = response.body().getResultado();
                AbsListView.class.cast(view).setAdapter(new AdapterGridChamados(activity, resource, response.body().getResultado()));
                LoadingSettings.showProgressBar(false, activity.findViewById(R.id.header_pb));
                if(activity.getIntent().hasExtra("ID_Chamado")){
                    String ID_Chamado = activity.getIntent().getStringExtra("ID_Chamado");
                    int position = 0;
                    ArrayList<ResultChamadosModel> result = response.body().getResultado();
                    for (int i = 0; i < result.size(); i++) {
                        if(result.get(i).getID() == Integer.parseInt(ID_Chamado)){
                            position = i;
                        }
                    }
                    showChamado(position);
                    activity.getIntent().removeExtra("ID_Chamado");
                }
            }

            @Override
            public void onFailure(Call<ChamadosModel> call, Throwable t) {
                LoadingSettings.showProgressBar(false, activity.findViewById(R.id.header_pb));
                frameLayout.addView(noConection);
                Log.e("TESTEE", t.getMessage());
            }
        });
    }

    private void addNewView(View view){
        frameLayout.removeView(lista);
        lista = view;
        frameLayout.addView(lista);
    }

    private GridView createGrid(){
        GridView grid = new GridView(activity);
        grid.setNumColumns(2);
        grid.setBackground(activity.getDrawable(R.drawable.border_top_left));
        grid.setPadding(3,3,0,0);
        grid.setOnItemLongClickListener(listaLongClickAction);
        grid.setOnItemClickListener(listaClickAction);
        grid.setNestedScrollingEnabled(true);
        return grid;
    }

    private ListView createList(){
        ListView listView = new ListView(activity);
        listView.setOnItemLongClickListener(listaLongClickAction);
        listView.setOnItemClickListener(listaClickAction);
        listView.setBackground(activity.getDrawable(R.drawable.border_complete));
        listView.setPadding(3,3,3,3);
        listView.setNestedScrollingEnabled(true);
        return listView;
    }

    AdapterView.OnItemLongClickListener listaLongClickAction = new AdapterView.OnItemLongClickListener() {
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
                bottomSheet.add(R.drawable.ic_chat_bubble_outline_black_24dp, "Ler comentario" + (resultChamadosModel.getTem_comentario() > 1? "s":""), new CustomBottomSheet.onClickAction() {
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
            return true;
        }
    };

    AdapterView.OnItemClickListener listaClickAction = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showChamado(position);
        }
    };

    public void showChamado(int position){
        final ResultChamadosModel resultChamadosModel = list.get(position);
        behaviorItens.txtTit.setText(resultChamadosModel.getTitulo());
        behaviorItens.txtCdo.setText(resultChamadosModel.getConteudo());
        behaviorItens.txtByy.setText(resultChamadosModel.getDe());
        behaviorItens.txtFor.setText(resultChamadosModel.getPara());
        behaviorItens.txtDtt.setText(resultChamadosModel.getPostadoEm());

        StatusUtil status = new StatusUtil(Integer.parseInt(resultChamadosModel.getStatus()));
        behaviorItens.behavior_imgStatus.setImageDrawable(activity.getDrawable(status.getResourceImage()));
        behaviorItens.behavior_imgStatus.setColorFilter(activity.getColor(status.getResourceColor()));
        behaviorItens.behavior_txtStatus.setText(status.getMensagem());
        behaviorItens.behavior_txtStatus.setTextColor(activity.getColor(status.getResourceColor()));

        behaviorItens.btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Chamado.class);
                intent.putExtra("item", resultChamadosModel);
                startActivity(intent);
                behaviorItens.cbsb.setState(CustomBottomSheetBehavior.CLOSED);
            }
        });

        behaviorItens.cbsb.setState(CustomBottomSheetBehavior.SHOWING);
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
                        Utils.hideKeyboard(activity, activity.findViewById(R.id.header_pb));
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
                saveComents(model, edtCio.getText().toString().trim());
                alert.dismiss();
            }
        });

        alert.show();
    }

    public void saveComents(final ResultChamadosModel model, String comentario){
        Retrofit retrofit = Network.teste();
        Connects add = retrofit.create(Connects.class);

        add.addComentarioPOST(String.valueOf(model.getID()), comentario).enqueue(new Callback<Default>() {
            @Override
            public void onResponse(Call<Default> call, Response<Default> response) {
                Toast.makeText(activity, response.body().getMensagem(), Toast.LENGTH_LONG).show();
                model.setTem_comentario(model.getTem_comentario() + 1);
            }

            @Override
            public void onFailure(Call<Default> call, Throwable t) {
                Toast.makeText(activity, "Ops, houve um erro na hora de salvar seu comentario", Toast.LENGTH_LONG).show();
                Log.e("TESTE", t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
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

}
