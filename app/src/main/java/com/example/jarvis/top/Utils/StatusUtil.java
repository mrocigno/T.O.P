package com.example.jarvis.top.Utils;


import com.example.jarvis.top.R;

public class StatusUtil {

    int resourceImage;
    int resourceColor;
    String mensagem;

    public StatusUtil(int codeStatus) {
        switch (codeStatus){
            case 1:{
                resourceImage = R.drawable.ic_access_time_black_24dp;
                resourceColor = R.color.colorClockYellow;
                mensagem = "Em andamento";
                break;
            }
            case 2:{
                resourceImage = R.drawable.ic_done_black_24dp;
                resourceColor = R.color.colorCheckGreen;
                mensagem = "Concluido";
                break;
            }
            case 3:{
                resourceImage = R.drawable.ic_close_black_24dp;
                resourceColor = R.color.colorRemoveRed;
                mensagem = "Cancelado";
                break;
            }
            case 4:{
                resourceImage = R.drawable.ic_access_time_black_24dp;
                resourceColor = R.color.colorClockYellow;
                mensagem = "Pendente";
                break;
            }
        }
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }

    public int getResourceColor() {
        return resourceColor;
    }

    public void setResourceColor(int resourceColor) {
        this.resourceColor = resourceColor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
