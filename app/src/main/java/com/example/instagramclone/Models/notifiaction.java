package com.example.instagramclone.Models;

public class notifiaction {
     String notifiactionby,type,postid,postedby,checkopen,notifiactionid;
     long notificationat;

    public notifiaction(String notifiactionby, String type, String postid, String postedby, String checkopen, long notificationat) {
        this.notifiactionby = notifiactionby;
        this.type = type;
        this.postid = postid;
        this.postedby = postedby;
        this.checkopen = checkopen;
        this.notificationat = notificationat;
    }

    public String getNotifiactionid() {
        return notifiactionid;
    }

    public void setNotifiactionid(String notifiactionid) {
        this.notifiactionid = notifiactionid;
    }

    public notifiaction() {
    }

    public String getNotifiactionby() {
        return notifiactionby;
    }

    public void setNotifiactionby(String notifiactionby) {
        this.notifiactionby = notifiactionby;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getCheckopen() {
        return checkopen;
    }

    public void setCheckopen(String checkopen) {
        this.checkopen = checkopen;
    }

    public long getNotificationat() {
        return notificationat;
    }

    public void setNotificationat(long notificationat) {
        this.notificationat = notificationat;
    }
}
