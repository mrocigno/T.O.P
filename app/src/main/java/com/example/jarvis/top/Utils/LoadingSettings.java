package com.example.jarvis.top.Utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jarvis.top.R;

public class LoadingSettings {

    private View view;
    private Thread thread;
    private String oldTxt;
    private Drawable oldBackground;
    private Class<TextView> teste;

    public LoadingSettings(View view, Class teste){
        this.view = view;
        this.teste = teste;
    }

    public LoadingSettings txtLoading(Activity activity, int miliSeconds, int repeat, String defaultText, boolean enable){
        this.oldTxt = teste.cast(view).getText().toString().trim();
        view.setEnabled(enable);
        defaultAction(activity, repeat, miliSeconds, defaultText);
        return this;
    }

    public LoadingSettings txtLoading(Activity activity, int miliSeconds, int repeat, String defaultText){
        this.oldTxt = teste.cast(view).getText().toString().trim();
        view.setEnabled(false);
        defaultAction(activity, repeat, miliSeconds, defaultText);
        return this;
    }

    public LoadingSettings txtLoading(Activity activity, int miliSeconds, int repeat, String defaultText, int background){
        this.oldTxt = teste.cast(view).getText().toString().trim();
        this.oldBackground = view.getBackground();
        view.setEnabled(false);
        view.setBackground(activity.getResources().getDrawable(background));
        defaultAction(activity, repeat, miliSeconds, defaultText);
        return this;
    }

    public void threadStart(){
        this.thread.start();
    }

    public void threadClose(){
        this.thread.interrupt();
        this.teste.cast(view).setText(oldTxt);
        if(this.oldBackground != null){
            this.view.setBackground(oldBackground);
        }
        view.setEnabled(true);
    }

    protected void defaultAction(final Activity activity, final int repeat, final int miliSeconds, final String defaultText){
        this.thread = new Thread(){
            @Override
            public void run() {
                try{
                    int loop = (repeat<2?2:repeat);
                    while(true){
                        String loading = defaultText + ".";
                        for (int i = 0; i < loop; i++) {
                            final String finalLoading = loading;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    teste.cast(view).setText(finalLoading);
                                }
                            });

                            loading += ".";
                            sleep(miliSeconds);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }
}
