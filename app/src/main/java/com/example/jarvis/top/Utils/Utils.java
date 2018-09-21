package com.example.jarvis.top.Utils;

import android.widget.EditText;

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
}
