package com.example.jarvis.top.Login.Sessao;

import android.app.Activity;
import android.content.Intent;

import com.example.jarvis.top.CustomAlert.AlertTop;
import com.example.jarvis.top.Login.Sessao.LoginBuilder.LoginBuilder;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.Utils;

public class Sessao {
    private static String nome_completo;
    private static String nick;
    private static int id;
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Sessao.token = token;
    }

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

    public static void deslogar(final Activity activity, final Class nextActivity) {
        AlertTop.CustomYesNoTopAlert(activity, "Atenção", "Você tem certeza em sair da conta?", R.drawable.ic_warning_theme_24dp, new AlertTop.YesNoCallBack() {
            @Override
            public void onClickYes() {
                new LoginBuilder(activity).destroy(new LoginBuilder.DestroyLoginCallback() {
                    @Override
                    public void CallBack() {
                        Utils.initActivity(activity, new Intent(activity, nextActivity), true);
                    }
                });
            }

            @Override
            public void onClickNo() {
                //No action
            }
        });
    }
}
