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
        int ID;
        int ID_Chamado;
        String Comentario;

        public Resultado(int id, int id_chamado, String comentario) {
            this.ID = id;
            this.ID_Chamado = id_chamado;
            this.Comentario = comentario;
        }

        public int getId() {
            return ID;
        }

        public void setId(int id) {
            this.ID = id;
        }

        public int getId_chamado() {
            return ID_Chamado;
        }

        public void setId_chamado(int id_chamado) {
            this.ID_Chamado = id_chamado;
        }

        public String getComentario() {
            return Comentario;
        }

        public void setComentario(String comentario) {
            this.Comentario = comentario;
        }
    }
}
