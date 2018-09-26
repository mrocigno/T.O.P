package com.example.jarvis.top.Main.Adapters.Chamados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jarvis.top.R;
import com.example.jarvis.top.WebService.Models.Chamados.ResultChamadosModel;

import java.util.ArrayList;

public class AdapterGridChamados extends BaseAdapter {

    private ArrayList<ResultChamadosModel> itens;
    private Context context;
    private int resource;

    private class ItensHolder{
        TextView adapterGrid_titulo, adapterGrid_conteudo;

        TextView adapter_status, adapter_titulo, adapter_conteudo;
    }

    public AdapterGridChamados(Context context, int resource, ArrayList<ResultChamadosModel> itens){
        this.itens = itens;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itens.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResultChamadosModel modelChamados = itens.get(position);
        ItensHolder holder;
        if(convertView == null){
            holder = new ItensHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            switch (resource){
                case R.layout.adapter_chamdos_main_grid:{
                    holder.adapterGrid_titulo = convertView.findViewById(R.id.adapterGrid_titulo);
                    holder.adapterGrid_conteudo = convertView.findViewById(R.id.adapterGrid_conteudo);
                    break;
                }
                case R.layout.adapter_chamados_main:{
                    holder.adapter_conteudo = convertView.findViewById(R.id.adapter_conteudo);
                    holder.adapter_status = convertView.findViewById(R.id.adapter_status);
                    holder.adapter_titulo = convertView.findViewById(R.id.adapter_titulo);
                    break;
                }
            }

            convertView.setTag(holder);
        }else{
            holder = (ItensHolder) convertView.getTag();
        }

        switch (resource){
            case R.layout.adapter_chamdos_main_grid:{
                holder.adapterGrid_titulo.setText(modelChamados.getTitulo());
                holder.adapterGrid_conteudo.setText(modelChamados.getConteudo());
                break;
            }
            case R.layout.adapter_chamados_main:{
                holder.adapter_conteudo.setText(modelChamados.getConteudo());
                holder.adapter_titulo.setText(modelChamados.getTitulo());
                break;
            }
        }

        return convertView;
    }
}
