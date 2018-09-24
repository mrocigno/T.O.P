package com.example.jarvis.top.CustomAlert;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jarvis.top.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class AlertTop {
    public static void CustomTopSimpleAlert(@NonNull final Activity activity, @NonNull String msg, int img, @NonNull final int duration){

        ViewHolder holder = new ViewHolder(R.layout.alert_base);
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

    public interface YesNoCallBack{
        void onClickYes();
        void onClickNo();
    }

    public static void CustomYesNoTopAlert(Activity activity, String title, String msg, int img, final YesNoCallBack yesNoCallBack){
        ViewHolder holder = new ViewHolder(R.layout.alert_yes_no_option);
        final DialogPlus alert = DialogPlus.newDialog(activity)
                .setContentHolder(holder)
                .setGravity(Gravity.TOP)
                .create();

        ((TextView) holder.getInflatedView().findViewById(R.id.alertYesNo_txtTle)).setText(title);
        ((TextView) holder.getInflatedView().findViewById(R.id.alertYesNo_txtCdo)).setText(msg);
        ((ImageView)holder.getInflatedView().findViewById(R.id.alertYesNo_imgIew)).setImageDrawable(activity.getDrawable(img));
        (holder.getInflatedView().findViewById(R.id.alertYesNo_btnYes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                yesNoCallBack.onClickYes();
            }
        });
        (holder.getInflatedView().findViewById(R.id.alertYesNo_btnNo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                yesNoCallBack.onClickNo();
            }
        });

        alert.show();
    }

}
