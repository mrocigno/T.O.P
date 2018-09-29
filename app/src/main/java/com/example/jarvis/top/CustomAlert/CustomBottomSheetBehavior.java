package com.example.jarvis.top.CustomAlert;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

public class CustomBottomSheetBehavior {
    private BottomSheetBehavior bottomSheetBehavior;

    public static int HIDING = 4;
    public static int SHOWING = 3;

    public CustomBottomSheetBehavior init(View layout){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();
        params.setBehavior(new BottomSheetBehavior());
        layout.requestLayout();
        bottomSheetBehavior = BottomSheetBehavior.from(layout);
        return this;
    }

    public CustomBottomSheetBehavior setPeekHeight(int peek){
        bottomSheetBehavior.setPeekHeight(peek * 3);
        return this;
    }

    public CustomBottomSheetBehavior setHideable(boolean hideable){
        bottomSheetBehavior.setHideable(hideable);
        return this;
    }

    public CustomBottomSheetBehavior setActions(BottomSheetBehavior.BottomSheetCallback callback){
        bottomSheetBehavior.setBottomSheetCallback(callback);
        return this;
    }

    public CustomBottomSheetBehavior setState(int state){
        bottomSheetBehavior.setState(state);
        return this;
    }
}
