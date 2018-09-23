package com.example.jarvis.top.WebService.Models.Login;

public class LoginModel {
    private int status;
    private String mensagem;
    private ResultLoginModel resultado;

    public LoginModel(int status, String mensagem, ResultLoginModel resultado) {
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

    public ResultLoginModel getResultado() {
        return resultado;
    }

    public void setResultado(ResultLoginModel resultado) {
            this.resultado = resultado;
        }

}
