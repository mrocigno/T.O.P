package com.example.jarvis.top.WebService.Models.Chamados;

import java.util.ArrayList;

public class ChamadosModel {
    int status;
    String mensagem;
    ArrayList<ResultChamadosModel> resultado;

    public ChamadosModel(int status, String mensagem, ArrayList<ResultChamadosModel> resultado) {
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

    public ArrayList<ResultChamadosModel> getResultado() {
        return resultado;
    }

    public void setResultado(ArrayList<ResultChamadosModel> resultado) {
        this.resultado = resultado;
    }
}
