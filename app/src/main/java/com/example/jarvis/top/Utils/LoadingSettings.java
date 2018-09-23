package com.example.jarvis.top.Utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import com.example.jarvis.top.R;

public class LoadingSettings {

    private Button view;
    private Thread thread;
    private String oldTxt;
    private Drawable oldBackground;

    public LoadingSettings(Button view){
        this.view = view;
    }

    public LoadingSettings txtLoading(Activity activity, int miliSeconds, int repeat){
        this.oldTxt = view.getText().toString().trim();
        view.setEnabled(false);
        defaultAction(activity, repeat, miliSeconds);
        return this;
    }

    public LoadingSettings txtLoading(Activity activity, int miliSeconds, int repeat, int background){
        this.oldTxt = view.getText().toString().trim();
        this.oldBackground = view.getBackground();
        view.setEnabled(false);
        view.setBackground(activity.getResources().getDrawable(background));
        defaultAction(activity, repeat, miliSeconds);
        return this;
    }

    public void threadStart(){
        this.thread.start();
    }

    public void threadClose(){
        this.thread.interrupt();
        this.view.setText(oldTxt);
        if(this.oldBackground != null){
            this.view.setBackground(oldBackground);
        }
        view.setEnabled(true);
    }

    protected void defaultAction(final Activity activity, final int repeat, final int miliSeconds){
        this.thread = new Thread(){
            @Override
            public void run() {
                try{
                    int loop = (repeat<2?2:repeat);
                    while(true){
                        String loading = ".";
                        for (int i = 0; i < loop; i++) {
                            final String finalLoading = loading;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.setText(finalLoading);
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
