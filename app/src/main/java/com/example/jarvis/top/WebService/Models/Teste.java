package com.example.jarvis.top.WebService.Models;

import java.util.ArrayList;

public class Teste {
    private int status;
    private String mensagem;
    private LoginModel resultado;

    public Teste(int status, String mensagem, LoginModel resultado) {
        this.status = status;
        this.mensagem = mensagem;
        this.resultado = resultado;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LoginModel getResultado() {
        return resultado;
    }

    public void setResultado(LoginModel resultado) {
        this.resultado = resultado;
    }
}
