package com.example.jarvis.top.Main.Menu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jarvis.top.Login.Sessao.LoginBuilder.UserModel;

public class DataBaseConfig extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "config.db";
    private static final String TABLE = "configuracoes";
    private static final String ID = "ID";
    private static final String FREQUENCIA = "FREQUENCIA";
    private static final String MODO_EXIBICAO = "MODO_EXIBICAO";
//    private static final String USER_NAME = "USER_NAME";
//    private static final String PASSWORD = "PASSWORD";
    private static final int VERSAO = 1;

    public DataBaseConfig(Context context){
        super(context, NOME_BANCO,null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLE+" ("
                + ID + " integer primary key autoincrement,"
                + FREQUENCIA + " integer,"
                + MODO_EXIBICAO + " text"
//                + USER_NAME + " text,"
//                + PASSWORD + " text"
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
            initialValues.put(FREQUENCIA, 5);
            initialValues.put(MODO_EXIBICAO, "LISTA");
//            initialValues.put(USER_NAME, "");
//            initialValues.put(PASSWORD, "");
            return db.insert(TABLE, null, initialValues);
        }finally{
            db.close();
        }
    }

    public void setUpdate(int frequencia, String modo_exibicao){
        SQLiteDatabase db = getReadableDatabase();
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(FREQUENCIA, frequencia);
            initialValues.put(MODO_EXIBICAO, modo_exibicao);
//            initialValues.put(USER_NAME, user_name);
//            initialValues.put(PASSWORD, password);
            db.update(TABLE, initialValues, "ID=1", null);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }
    }

    public void setUpdate(int frequencia){
        SQLiteDatabase db = getReadableDatabase();
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(FREQUENCIA, frequencia);
           // initialValues.put(MODO_EXIBICAO, modo_exibicao);
//            initialValues.put(USER_NAME, user_name);
//            initialValues.put(PASSWORD, password);
            db.update(TABLE, initialValues, "ID=1", null);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }
    }

    public void setUpdate(String modo_exibicao){
        SQLiteDatabase db = getReadableDatabase();
        try {
            ContentValues initialValues = new ContentValues();
            //initialValues.put(FREQUENCIA, frequencia);
            initialValues.put(MODO_EXIBICAO, modo_exibicao);
//            initialValues.put(USER_NAME, user_name);
//            initialValues.put(PASSWORD, password);
            db.update(TABLE, initialValues, "ID=1", null);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }
    }


    public String[] getData(){
        String selectQuery = "SELECT * FROM "+ TABLE +" WHERE ID = 1";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//        UserModel result = null;
        String[] str = null;
        if(cursor.moveToFirst()) {
            str = new String[]{
                    String.valueOf(cursor.getInt(1)),
                    cursor.getString(2)
            };
//            result = new UserModel(, , cursor.getString(3), cursor.getString(4));
        }
        cursor.close();
        db.close();
        return str;
    }


}
