package com.example.instagramclone.Models;

public class postmodel {

    String Postid,postedby,postedimage,postdescription;
    long postedat;

    public postmodel() {

    }

    public postmodel(String postid, String postedby, String postedimage, String postdescription, long postedat) {
        Postid = postid;
        this.postedby = postedby;
        this.postedimage = postedimage;
        this.postdescription = postdescription;
        this.postedat = postedat;
    }

    public String getPostid() {
        return Postid;
    }

    public void setPostid(String postid) {
        Postid = postid;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getPostedimage() {
        return postedimage;
    }

    public void setPostedimage(String postedimage) {
        this.postedimage = postedimage;
    }

    public String getPostdescription() {
        return postdescription;
    }

    public void setPostdescription(String postdescription) {
        this.postdescription = postdescription;
    }

    public long getPostedat() {
        return postedat;
    }

    public void setPostedat(long postedat) {
        this.postedat = postedat;
    }
}