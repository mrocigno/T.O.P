package com.example.jarvis.top.WebService.Models.Chamados;

public class ResultChamadosModel {
    private int ID;
    private String Status;
    private String Titulo;
    private String Conteudo;
    private String De;
    private String Para;
    private String PostadoEm;
    private String Latitude;
    private String Longitude;
    private int tem_comentario;

    public ResultChamadosModel(int ID, String status, String titulo, String conteudo, String de, String para, String postadoEm, String latitude, String longitude, int tem_comentario) {
        this.ID = ID;
        Status = status;
        Titulo = titulo;
        Conteudo = conteudo;
        De = de;
        Para = para;
        PostadoEm = postadoEm;
        Latitude = latitude;
        Longitude = longitude;
        this.tem_comentario = tem_comentario;
    }

    public String getDe() {
        return De;
    }

    public void setDe(String de) {
        De = de;
    }

    public String getPara() {
        return Para;
    }

    public void setPara(String para) {
        Para = para;
    }

    public String getPostadoEm() {
        return PostadoEm;
    }

    public void setPostadoEm(String postadoEm) {
        PostadoEm = postadoEm;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
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
