package com.example.jarvis.top.CustomAlert;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;

import com.example.jarvis.top.R;
import com.orhanobut.dialogplus.DialogPlus;

public class AlertTop {
    public static void showlert(final Activity activity, String msg, int img, final int duration){

        CustomHolder customHolder = new CustomHolder(R.layout.base_alert);
        customHolder.setValuesAlert(new ValuesAlert(msg, img, duration), activity);
        final DialogPlus alert = DialogPlus.newDialog(activity)
                .setContentHolder(customHolder)
                .setGravity(Gravity.TOP)
                .create();

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
