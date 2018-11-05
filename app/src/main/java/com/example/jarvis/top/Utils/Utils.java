package com.example.jarvis.top.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

public class Utils {
    public static boolean verifieEmail(EditText etField, String msgErro){
        String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        if(!etField.getText().toString().trim().matches(emailPattern)){
            etField.setError(msgErro);
            return false;
        }
        return true;
    }

    public static boolean verifieEmptyField(EditText editText, String msgErro){
        String vle = editText.getText().toString().trim();
        if(vle.equals("")){
            editText.setError(msgErro);
            return false;
        }
        return true;
    }

    public static void initActivity(Activity activity, Intent intent, boolean closeOldActivity){
        activity.startActivity(intent);
        if(closeOldActivity){
            activity.finish();
        }
    }

    public static void hideKeyboard(Activity activity, View teste){
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(teste.getWindowToken(), 0);
    }

    public static void rotateRealTime(float toRotate, View view){
        android.view.animation.RotateAnimation rotate = new android.view.animation.RotateAnimation(toRotate, toRotate,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setDuration(100);
        rotate.setFillAfter(true);

        view.startAnimation(rotate);
    }

    public static boolean verifiePermissions(Activity activity) {
        String[] permisions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        ArrayList<String> denied = new ArrayList<>();

        for (String permision : permisions) {
            if (ContextCompat.checkSelfPermission(activity, permision) == PackageManager.PERMISSION_DENIED) {
                denied.add(permision);
            }
        }

        if (denied.size() > 0) {
            String[] permisionsDenied = new String[denied.size()];
            permisionsDenied = denied.toArray(permisionsDenied);

            ActivityCompat.requestPermissions(activity, permisionsDenied, 0);
            return false;
        }
        return true;
    }
}
