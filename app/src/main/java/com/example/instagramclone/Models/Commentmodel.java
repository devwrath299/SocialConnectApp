package com.example.instagramclone.Models;

public class Commentmodel {

    String msg,by;
    Long time;

    public Commentmodel(String msg, String by, Long time) {
        this.msg = msg;
        this.by = by;
        this.time = time;
    }

    public Commentmodel() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
