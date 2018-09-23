package com.example.jarvis.top.Login.Sessao;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import com.master.killercode.loginverifier.Dao.Dao;


import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class CustomLoginVerifier extends Dao {
    private Activity activity;
    private String key = "APPKEY";
    private static String split = "::";

    public CustomLoginVerifier(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    private String getFirstID(String nameDataBase) {
        String rowId = "";
        Cursor cursor = this.db.query(nameDataBase, (String[])null, (String)null, (String[])null, (String)null, (String)null, (String)null);
        if (cursor.moveToFirst()) {
            rowId = cursor.getString(cursor.getColumnIndex("_id"));
        }
        return rowId;
    }

    public void inLogin() {
        this.abrirBanco();

        try {
            ContentValues cv = new ContentValues();
            String filtro = "_id = ? ";
            String[] argumentos = new String[]{this.getFirstID("table_login")};
            cv.put("log", "1");
            if (argumentos[0].equals("")) {
                this.db.insert("table_login", (String)null, cv);
            } else {
                this.db.update("table_login", cv, filtro, argumentos);
            }
        } catch (Exception var4) {
            ;
        }

        this.fecharBanco();
    }

    public void inLogin(String login) {
        Map<String, String> data = new ArrayMap();
        data.put("user", login);
        this.insertData(data);
    }

    public void inLogin(String login, String passWord) {
        Map<String, String> data = new ArrayMap();
        data.put("user", login);
        data.put("crypted", passWord);
        this.insertData(data);
    }

    public void inLogin(String userName, String login, String passWord) {
        Map<String, String> data = new ArrayMap();
        data.put("user_name", userName);
        data.put("user", login);
        data.put("crypted", passWord);
        this.insertData(data);
    }

    private void insertData(Map<String, String> data) {
        String userName = "";
        if (data.containsKey("user_name")) {
            userName = (String)data.get("user_name");
        }

        String login = "";
        if (data.containsKey("user")) {
            login = (String)data.get("user");
        }

        String passWord = "";
        if (data.containsKey("crypted")) {
            passWord = this.md5(userName, (String)data.get("crypted"));
        }

        this.abrirBanco();

        try {
            ContentValues cv = new ContentValues();
            ContentValues cvUser = new ContentValues();
            String filtro = "_id = ? ";
            String[] argumentos = new String[]{this.getFirstID("table_login")};
            cv.put("log", "1");
            cv.put("id_user", login);
            if (argumentos[0].equals("")) {
                this.db.insert("table_login", (String)null, cv);
            } else {
                this.db.update("table_login", cv, filtro, argumentos);
            }

            String query = "select * from table_user where user= ?";
            Cursor name = this.db.rawQuery(query, new String[]{login});
            if (name.getCount() == 0) {
                cvUser.put("user_name", userName);
                cvUser.put("user", login);
                cvUser.put("crypted", passWord);
                cvUser.put("date_login", getTime());
                cvUser.put("date_logout", "");
                cvUser.put("activated", "1");
                this.db.insert("table_user", (String)null, cvUser);
                Log.w("ac", "0");
            } else {
                String ac;
                for(ac = ""; name.moveToNext(); ac = name.getString(name.getColumnIndex("activated"))) {
                    ;
                }

                ContentValues cvU;
                if (ac.equals("0")) {
                    cvU = new ContentValues();
                    cvU.put("crypted", passWord);
                    cvU.put("activated", "1");
                    this.db.update("table_user", cvU, "user= ?", new String[]{login});
                } else {
                    cvU = new ContentValues();
                    cvU.put("crypted", passWord);
                    this.db.update("table_user", cvU, "user= ?", new String[]{login});
                    Log.w("ac", "1");
                }

                this.updateTime(1, login);
            }
        } catch (Exception var13) {
            ;
        }

        this.fecharBanco();
    }

    private void updateTime(int i, String login) {
        ContentValues cvUser = new ContentValues();
        String filtro2 = "user = ? ";
        if (i == 1) {
            cvUser.put("date_login", getTime());
        } else {
            cvUser.put("date_logout", getTime());
        }

        this.db.update("table_user", cvUser, filtro2, new String[]{login});
    }

    private static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void inLogout() {
        this.closeLogin();
    }

    public void inLogout(Class nextActivity) {
        this.closeLogin();
        Intent intent = new Intent(this.activity, nextActivity);
//        intent.putExtra("class", nextActivity);
        this.activity.startActivity(intent);
        this.activity.finish();
    }

    private void closeLogin() {
        this.abrirBanco();

        try {
            ContentValues cv = new ContentValues();
            String filtro = "_id = ? ";
            String[] argumentos = new String[]{this.getFirstID("table_login")};
            cv.put("log", "0");
            if (argumentos[0].equals("")) {
                this.db.insert("table_login", (String)null, cv);
            } else {
                this.db.update("table_login", cv, filtro, argumentos);
            }

            String comando = "select * from table_login where _id = ? ";
            Cursor logout = this.db.rawQuery(comando, argumentos);

            while(logout.moveToNext()) {
                String user = logout.getString(logout.getColumnIndex("id_user"));
                this.updateTime(2, user);
            }
        } catch (Exception var7) {
            ;
        }

        this.fecharBanco();
    }

    public void getLogin(Class mainActivity) {
        this.abrirBanco();
        Cursor cursor = null;
        String id = this.getFirstID("table_login");
        String dados = "";

        try {
            String comando = " select * from table_login where _id = ? ";
            String[] argumentos = new String[]{id};

            for(cursor = this.db.rawQuery(comando.toLowerCase(), argumentos); cursor.moveToNext(); dados = cursor.getString(cursor.getColumnIndex("log"))) {
                ;
            }

            cursor.close();
            cursor = null;
        } catch (Exception var7) {
            ;
        }

        this.fecharBanco();
        if (dados.equals("1")) {
//            Toast.makeText(this.activity, "Loged", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.activity, mainActivity);
            this.activity.startActivity(intent);
            this.activity.finish();
        }

    }

    public void getLogin(Class mainActivity, Class loginActivity) {
        int number = 0;
        this.abrirBanco();
        Cursor cursor = null;
        String id = this.getFirstID("table_login");
        String dados = "";

        try {
            String comando = " select * from table_login where _id = ? ";
            String[] argumentos = new String[]{id};

            for(cursor = this.db.rawQuery(comando.toLowerCase(), argumentos); cursor.moveToNext(); dados = cursor.getString(cursor.getColumnIndex("log"))) {

            }

            cursor.close();
            cursor = null;
            String comando2 = "select * from table_user where activated = 1";
            cursor = this.db.rawQuery(comando2, (String[])null);
            number = cursor.getCount();
            cursor.close();
            cursor = null;
        } catch (Exception var10) {
            ;
        }

        this.fecharBanco();
        Intent intent;
        if (dados.equals("1")) {
            intent = new Intent(this.activity, mainActivity);
            this.activity.startActivity(intent);
            this.activity.finish();
        } else {
            intent = new Intent(this.activity, loginActivity);
            this.activity.startActivity(intent);
            this.activity.finish();
        }

    }

    private String md5(String user, String pass) {
        String base64 = "";
        String md5 = pass + split + this.key + split + user;

        try {
            byte[] data1 = md5.getBytes("UTF-8");
            base64 = Base64.encodeToString(data1, 0);
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
        }

        return base64;
    }

    public static String md5Decode(String base64) {
        String rawDecoded = "";

        try {
            byte[] data2 = Base64.decode(base64, 0);
            rawDecoded = new String(data2, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        String[] array = rawDecoded.split(split);
        return array[0];
    }
}

