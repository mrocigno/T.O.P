package com.example.jarvis.top.Login.Sessao;

import android.app.Activity;
import android.content.Intent;

import com.example.jarvis.top.Login.Sessao.LoginBuilder.LoginBuilder;
import com.example.jarvis.top.Utils.Utils;

public class Sessao {
    private static String nome_completo;
    private static String nick;
    private static int    id;

    public static String getNome_completo() {
        return nome_completo;
    }

    public static void setNome_completo(String nome_completo) {
        Sessao.nome_completo = nome_completo;
    }

    public static String getNick() {
        return nick;
    }

    public static void setNick(String nick) {
        Sessao.nick = nick;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Sessao.id = id;
    }

    public static void deslogar(final Activity activity, final Class nextActivity){
        new LoginBuilder(activity).destroy(new LoginBuilder.DestroyLoginCallback() {
            @Override
            public void CallBack() {
                Utils.initActivity(activity, new Intent(activity, nextActivity), true);
            }
        });
    }
}
