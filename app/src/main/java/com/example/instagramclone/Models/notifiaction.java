package com.example.instagramclone.Models;

public class notifiaction {
    int profile;
    String post,time;

    public notifiaction(int profile, String post, String time) {
        this.profile = profile;
        this.post = post;
        this.time = time;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
