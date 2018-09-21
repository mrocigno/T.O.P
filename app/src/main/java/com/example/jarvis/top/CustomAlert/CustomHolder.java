package com.example.jarvis.top.CustomAlert;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jarvis.top.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;

public class CustomHolder implements Holder {

    private static final int INVALID = -1;

    private int backgroundResource;

    private ViewGroup headerContainer;
    private View headerView;

    private ViewGroup footerContainer;
    private View footerView;

    private View.OnKeyListener keyListener;

    private View contentView;
    private int viewResourceId = INVALID;

    private ValuesAlert valuesAlert;
    private Context context;


    public void setValuesAlert(ValuesAlert valuesAlert, Context context) {
        this.valuesAlert = valuesAlert;
        this.context = context;
    }

    public CustomHolder(int viewResourceId) {
        this.viewResourceId = viewResourceId;
    }

    public CustomHolder(View contentView) {
        this.contentView = contentView;
    }

    @Override public void addHeader(View view) {
        if (view == null) {
            return;
        }
        headerContainer.addView(view);
        headerView = view;
    }

    @Override public void addFooter(View view) {
        if (view == null) {
            return;
        }
        footerContainer.addView(view);
        footerView = view;
    }

    @Override public void setBackgroundResource(int colorResource) {
        this.backgroundResource = colorResource;
    }

    @Override public View getView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(com.orhanobut.dialogplus.R.layout.dialog_view, parent, false);
        View outMostView = view.findViewById(com.orhanobut.dialogplus.R.id.dialogplus_outmost_container);
        outMostView.setBackgroundResource(backgroundResource);
        ViewGroup contentContainer = (ViewGroup) view.findViewById(com.orhanobut.dialogplus.R.id.dialogplus_view_container);
        contentContainer.setOnKeyListener(new View.OnKeyListener() {
            @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyListener == null) {
                    throw new NullPointerException("keyListener should not be null");
                }
                return keyListener.onKey(v, keyCode, event);
            }
        });
        addContent(inflater, parent, contentContainer);
        headerContainer = (ViewGroup) view.findViewById(com.orhanobut.dialogplus.R.id.dialogplus_header_container);
        footerContainer = (ViewGroup) view.findViewById(com.orhanobut.dialogplus.R.id.dialogplus_footer_container);
        return view;
    }

    private void addContent(LayoutInflater inflater, ViewGroup parent, ViewGroup container) {
        if (viewResourceId != INVALID) {
            contentView = inflater.inflate(viewResourceId, parent, false);
            if(valuesAlert != null){
                ((TextView) contentView.findViewById(R.id.alert_msg)).setText(valuesAlert.getMsg());
                ((ImageView) contentView.findViewById(R.id.alert_img)).setImageDrawable(context.getResources().getDrawable(valuesAlert.img));
                ObjectAnimator progressAnimator = ObjectAnimator.ofInt(contentView.findViewById(R.id.alert_pbh), "progress", 100, 0);
                progressAnimator.setDuration(valuesAlert.getDuration());
                progressAnimator.setInterpolator(new LinearInterpolator());
                progressAnimator.start();
            }
        } else {
            ViewGroup parentView = (ViewGroup) contentView.getParent();
            if (parentView != null) {
                parentView.removeView(contentView);
            }
        }

        container.addView(contentView);
    }

    @Override public void setOnKeyListener(View.OnKeyListener keyListener) {
        this.keyListener = keyListener;
    }

    @Override public View getInflatedView() {
        return contentView;
    }

    @Override public View getHeader() {
        return headerView;
    }

    @Override public View getFooter() {
        return footerView;
    }
}
