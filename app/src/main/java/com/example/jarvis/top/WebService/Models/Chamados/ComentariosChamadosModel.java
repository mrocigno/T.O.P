package com.example.jarvis.top.WebService.Models.Chamados;

import java.util.ArrayList;

public class ComentariosChamadosModel {
    private int status;
    private String mensagem;
    private ArrayList<Resultado> resultado;

    public ComentariosChamadosModel(int status, String mensagem, ArrayList<Resultado> resultado) {
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

    public ArrayList<Resultado> getResultado() {
        return resultado;
    }

    public void setResultado(ArrayList<Resultado> resultado) {
        this.resultado = resultado;
    }

    public class Resultado{
        int id;
        int id_chamado;
        String comentario;

        public Resultado(int id, int id_chamado, String comentario) {
            this.id = id;
            this.id_chamado = id_chamado;
            this.comentario = comentario;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId_chamado() {
            return id_chamado;
        }

        public void setId_chamado(int id_chamado) {
            this.id_chamado = id_chamado;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }
    }
}
