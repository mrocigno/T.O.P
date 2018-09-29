package com.example.jarvis.top.Login.Sessao.LoginBuilder;

public class UserModel {
    private int id;
    private String user;
    private String user_name;
    private String password;
    private String token;

    public UserModel(int id, String user, String user_name, String password, String token) {
        this.id = id;
        this.user = user;
        this.user_name = user_name;
        this.password = password;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
