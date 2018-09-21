package com.example.jarvis.top.CustomAlert;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jarvis.top.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class AlertTop {
    public static void showlert(final Activity activity, String msg, int img, final int duration){

        ViewHolder holder = new ViewHolder(R.layout.base_alert);

//        customHolder.setValuesAlert(new ValuesAlert(msg, img, duration), activity);
        final DialogPlus alert = DialogPlus.newDialog(activity)
                .setContentHolder(holder)
                .setGravity(Gravity.TOP)
                .create();
        ((TextView)holder.getInflatedView().findViewById(R.id.alert_msg)).setText(msg);
        ((ImageView)holder.getInflatedView().findViewById(R.id.alert_img)).setImageDrawable(activity.getDrawable(img));

        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(holder.getInflatedView().findViewById(R.id.alert_pbh), "progress", 100, 0);
        progressAnimator.setDuration(duration);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();

        alert.show();

        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(duration);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
