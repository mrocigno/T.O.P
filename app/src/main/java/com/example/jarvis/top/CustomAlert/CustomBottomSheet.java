package com.example.jarvis.top.CustomAlert;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jarvis.top.R;

import java.util.ArrayList;

public class CustomBottomSheet {

    private ArrayList<ItensBottomView> localitens;
    private Activity activity;
    private BottomSheetDialog dialog;


    public CustomBottomSheet(Activity activity) {
        localitens = new ArrayList<>();
        this.activity = activity;
    }

    //Para criar o evento onClick de cada item
    public interface onClickAction{
        void onItemSelected();
    }

    //Itens do ListView
    public static class ItensBottomView{
        private int icon;
        private String texto;
        private onClickAction onClickAction;

        public ItensBottomView(int icon, String texto, onClickAction onClickAction) {
            this.icon = icon;
            this.texto = texto;
            this.onClickAction = onClickAction;
        }

        public onClickAction getOnClickAction() { return onClickAction; }

        public int getIcon() { return icon; }

        public void setIcon(int icon) { this.icon = icon; }

        public String getTexto() { return texto; }

        public void setTexto(String texto) { this.texto = texto; }
    }

    //Adapter do ListView
    private class bnsItensAdapter extends ArrayAdapter<ItensBottomView> {

        //Itens do Layout do ListView
        //Cada campo novo adicionado no layout "bns_list_itens" deve ser adicionado aqui
        private class ItensHolder{
            ImageView imageView;
            TextView textView;
        }

        bnsItensAdapter(Context context, ArrayList<ItensBottomView> objects) {
            super(context, R.layout.bs_list_itens, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //É no "getView" q será criada cada linha, então ele vai rodar nas linhas criadas no ArrayList (q é um objeto ItensBottomView)
            //Então essa função (ItensBottomView itensbv = getItem(position);) serve para pegar os dados de sua respectiva linha
            ItensBottomView itensbv = getItem(position);
            ItensHolder itensHolder;

            if(convertView == null){
                itensHolder = new ItensHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.bs_list_itens, parent, false);

                //Aqui se define os id dos campos (definidos na sub-classe "ItensHolder")
                //Depois de ter inflado o layout (no caso o que estou usando é o "bns_list_itens")
                itensHolder.imageView = convertView.findViewById(R.id.bnsImg);
                itensHolder.textView = convertView.findViewById(R.id.bnsTexto);

                convertView.setTag(itensHolder);
            }else{
                itensHolder = (ItensHolder) convertView.getTag();
            }

            //Aqui é onde será definido o valor de cada linha no ListView
            //Pegando o valor do ArrayList criado a partir da sub-classe ItensBottomView
            assert itensbv != null;
            itensHolder.imageView.setImageDrawable(convertView.getResources().getDrawable(itensbv.icon));
            itensHolder.textView.setText(itensbv.texto);

            return convertView;
        }
    }


    public CustomBottomSheet add(int icon, String texto, onClickAction onClick){
        localitens.add(new ItensBottomView(icon, texto, onClick));
        return this;
    }


    public void show() {
        //Infla o layout criado para o BottomSheet (sheet_main)
        //Lá é onde está o listview onde será adicionado os itens
        @SuppressLint("InflateParams")
        View view = activity.getLayoutInflater().inflate(R.layout.bs_main, null);
        dialog = new BottomSheetDialog(activity);
        dialog.setContentView(view);

        //Pega o ID do listview e seta o adapter a partir do ArrayList<ItensBottomView> passado como parâmetro
        ListView listView = view.findViewById(R.id.bnsListView);
        listView.setAdapter(new bnsItensAdapter(activity, localitens));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItensBottomView item = localitens.get(position);
                item.getOnClickAction().onItemSelected();
                dialog.dismiss();
            }
        });

        //Enfim exibe o BottomSheetView
        dialog.show();
    }

    public void close(){
        dialog.dismiss();
    }
}
