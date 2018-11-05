package com.example.jarvis.top.Login.Sessao.LoginBuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseActions extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "loginbuilder.db";
    private static final String TABLE = "table_login";
    private static final String ID = "ID";
    private static final String ID_WEB = "ID_WEB";
    private static final String USER = "USER";
    private static final String USER_NAME = "USER_NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String TOKEN = "TOKEN";
    private static final int VERSAO = 3;

    public DataBaseActions(Context context){
        super(context, NOME_BANCO,null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLE+" ("
                + ID + " integer primary key autoincrement,"
                + ID_WEB + " integer,"
                + USER + " text,"
                + USER_NAME + " text,"
                + PASSWORD + " text,"
                + TOKEN + " text"
                +")";
        db.beginTransaction();
        try {
            ExecutarComandosSQL(db, sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("TESTEEE", e.getMessage());
        }finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    private void ExecutarComandosSQL(SQLiteDatabase db, String sql){

        if (sql.trim().length()>0)
            db.execSQL(sql);
    }

    public long setDefault(){
        SQLiteDatabase db = getReadableDatabase();
        try{
            ContentValues initialValues = new ContentValues();
            initialValues.put(ID_WEB, 0);
            initialValues.put(USER, "");
            initialValues.put(USER_NAME, "");
            initialValues.put(PASSWORD, "");
            initialValues.put(TOKEN, "");
            return db.insert(TABLE, null, initialValues);
        }finally{
            db.close();
        }
    }

    public void setUpdate(int id, String user, String user_name, String password, String token){
        SQLiteDatabase db = getReadableDatabase();
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(ID_WEB, id);
            initialValues.put(USER, user);
            initialValues.put(USER_NAME, user_name);
            initialValues.put(PASSWORD, password);
            initialValues.put(TOKEN, token);
            db.update(TABLE, initialValues, "ID=1", null);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }
    }

    public UserModel getData(){
        String selectQuery = "SELECT * FROM "+ TABLE +" WHERE ID = 1";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        UserModel result = null;
        if(cursor.moveToFirst()) {
            result = new UserModel(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        }
        cursor.close();
        db.close();
        return result;
    }


}
