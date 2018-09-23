package com.example.jarvis.top.Login.Sessao;

public class Sessao {
    private static String login;
    private static String senha;

    public static String getSenha() {
        return senha;
    }

    public static void setSenha(String senha) {
        Sessao.senha = senha;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String nome) {
        Sessao.login = nome;
    }
}
