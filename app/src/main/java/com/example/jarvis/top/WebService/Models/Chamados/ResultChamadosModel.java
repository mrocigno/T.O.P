package com.example.jarvis.top.WebService.Models.Chamados;

public class ResultChamadosModel {
    private int ID;
    private String Status;
    private String Titulo;
    private String Conteudo;
    private int tem_comentario;

    public ResultChamadosModel(int ID, String status, String titulo, String conteudo, int tem_comentario) {
        this.ID = ID;
        Status = status;
        Titulo = titulo;
        Conteudo = conteudo;
        this.tem_comentario = tem_comentario;
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

    public int getTem_comentario() {
        return tem_comentario;
    }

    public void setTem_comentario(int tem_comentario) {
        this.tem_comentario = tem_comentario;
    }
}
