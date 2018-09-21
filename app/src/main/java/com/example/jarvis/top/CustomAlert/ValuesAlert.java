package com.example.jarvis.top.CustomAlert;

public class ValuesAlert {
    String msg;
    int img;
    int duration;

    public ValuesAlert(String msg, int img, int duration) {
        this.msg = msg;
        this.img = img;
        this.duration = duration;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
