package com.example.jarvis.top.WebService.Models.Chamados;

public class ResultChamadosModel {
    private int ID;
    private String Status;
    private String Titulo;
    private String Conteudo;

    public ResultChamadosModel(int ID, String status, String titulo, String conteudo) {
        this.ID = ID;
        Status = status;
        Titulo = titulo;
        Conteudo = conteudo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getConteudo() {
        return Conteudo;
    }

    public void setConteudo(String conteudo) {
        Conteudo = conteudo;
    }
}
