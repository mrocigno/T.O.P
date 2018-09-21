package com.example.jarvis.top.CustomAlert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jarvis.top.R;

public class AlertBaseAdapter extends BaseAdapter {

    private class ItensHolder{
        TextView msg;
        ImageView img;
    }

    Context context;
    ValuesAlert valuesAlert;

    AlertBaseAdapter(Context context, ValuesAlert valuesAlert){
        this.context = context;
        this.valuesAlert = valuesAlert;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItensHolder holder;

        if(convertView == null){
            holder = new ItensHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.base_alert, parent);

            holder.msg = convertView.findViewById(R.id.alert_msg);
            holder.img = convertView.findViewById(R.id.alert_img);

            convertView.setTag(holder);
        }else{
            holder = (ItensHolder) convertView.getTag();
        }

        holder.msg.setText(valuesAlert.getMsg());
        holder.img.setImageDrawable(context.getResources().getDrawable(valuesAlert.img));
        return null;
    }
}
