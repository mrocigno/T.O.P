package com.example.jarvis.top.Login.Sessao.LoginBuilder;

import android.app.Activity;
import android.util.Log;

public class LoginBuilder {

    public interface CreateLoginCallback {
        void CallBack(UserModel userModel);
    }

    public interface DestroyLoginCallback {
        void CallBack();
    }

    public interface isLogedCallback{
        void onIsLoged(UserModel userModel);
        void onIsNotLoged();
    }

    Activity activity;
    DataBaseActions db;

    public LoginBuilder(Activity activity) {
        this.activity = activity;
        db = new DataBaseActions(activity);
    }

    public void create(int id, String user, String user_name, String password, String token){
        db.setUpdate(id, user, user_name, password, token);
    }

    public void create(int id, String user, String user_name, String password, String token, CreateLoginCallback callback){
        db.setUpdate(id, user, user_name, password, token);
        callback.CallBack(db.getData());
    }

    public void destroy(DestroyLoginCallback callback){
        db.setUpdate(0,"","","", "");
        callback.CallBack();
    }

    public void isLoged(isLogedCallback callback){
        UserModel userModel = db.getData();
        if(userModel != null) {
            if (userModel.getUser().equals("") && userModel.getUser_name().equals("") && userModel.getPassword().equals("")) {
                callback.onIsNotLoged();
            } else {
                callback.onIsLoged(userModel);
            }
        }else{
            db.setDefault();
            callback.onIsNotLoged();
        }
    }
}
