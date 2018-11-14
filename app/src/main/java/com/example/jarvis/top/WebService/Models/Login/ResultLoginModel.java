package com.example.jarvis.top.WebService.Models.Login;

public class ResultLoginModel {
    private int id_usuario;
    private String email;
    private String nome_completo;
    private String token;
    private String senha;
    private int id_tipo_usuario;

    public ResultLoginModel(int id_usuario, String email, String nome_completo, String token, String senha, int id_tipo_usuario) {
        this.id_usuario = id_usuario;
        this.email = email;
        this.nome_completo = nome_completo;
        this.token = token;
        this.id_tipo_usuario = id_tipo_usuario;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome_completo() {
        return nome_completo;
    }

    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(int id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }
}
