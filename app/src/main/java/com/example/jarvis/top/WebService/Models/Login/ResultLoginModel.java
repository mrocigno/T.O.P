package com.example.jarvis.top.WebService.Models.Login;

public class ResultLoginModel {
    private int ID;
    private String Nick;
    private String Senha;
    private String Email;
    private String Nome_Completo;
    private int Tipo_User;

    public ResultLoginModel(int ID, String nick, String senha, String email, String nome_Completo, int tipo_User) {
        this.ID = ID;
        Nick = nick;
        Senha = senha;
        Email = email;
        Nome_Completo = nome_Completo;
        Tipo_User = tipo_User;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNome_Completo() {
        return Nome_Completo;
    }

    public void setNome_Completo(String nome_Completo) {
        Nome_Completo = nome_Completo;
    }

    public int getTipo_User() {
        return Tipo_User;
    }

    public void setTipo_User(int tipo_User) {
        Tipo_User = tipo_User;
    }
}
