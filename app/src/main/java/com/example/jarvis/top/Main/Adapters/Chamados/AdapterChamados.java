package com.example.jarvis.top.Main.Adapters.Chamados;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jarvis.top.R;
import com.example.jarvis.top.WebService.Models.Chamados.ResultChamadosModel;

import java.util.ArrayList;

public class AdapterChamados extends ArrayAdapter<ResultChamadosModel> {

    private class ItensHolder{
        TextView adapter_status, adapter_titulo, adapter_conteudo;
    }

    public AdapterChamados(@NonNull Context context, ArrayList<ResultChamadosModel> arrayList) {
        super(context, R.layout.adapter_chamados_main, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ResultChamadosModel modelChamados = getItem(position);
        ItensHolder holder;

        if(convertView == null){
            holder = new ItensHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_chamados_main, parent, false);
            holder.adapter_conteudo = convertView.findViewById(R.id.adapter_conteudo);
            holder.adapter_status = convertView.findViewById(R.id.adapter_status);
            holder.adapter_titulo = convertView.findViewById(R.id.adapter_titulo);

            convertView.setTag(holder);
        }else{
            holder = (ItensHolder) convertView.getTag();
        }

//        holder.adapter_status.setText(modelChamados.getStatus());
        holder.adapter_conteudo.setText(modelChamados.getConteudo());
        holder.adapter_titulo.setText(modelChamados.getTitulo());

        return convertView;
    }
}
