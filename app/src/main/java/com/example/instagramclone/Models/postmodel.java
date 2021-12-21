package com.example.instagramclone.Models;

public class postmodel {

    int story,profileimage,like,save,share,chat,threedots;
    String name,about,liketext,sharetext,chattext;

    public postmodel(int story, int profileimage, int like, int save, int share, int chat, int threedots, String name, String about, String liketext, String sharetext, String chattext) {
        this.story = story;
        this.profileimage = profileimage;
        this.like = like;
        this.save = save;
        this.share = share;
        this.chat = chat;
        this.threedots = threedots;
        this.name = name;
        this.about = about;
        this.liketext = liketext;
        this.sharetext = sharetext;
        this.chattext = chattext;
    }

    public int getStory() {
        return story;
    }

    public void setStory(int story) {
        this.story = story;
    }

    public int getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(int profileimage) {
        this.profileimage = profileimage;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getChat() {
        return chat;
    }

    public void setChat(int chat) {
        this.chat = chat;
    }

    public int getThreedots() {
        return threedots;
    }

    public void setThreedots(int threedots) {
        this.threedots = threedots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLiketext() {
        return liketext;
    }

    public void setLiketext(String liketext) {
        this.liketext = liketext;
    }

    public String getSharetext() {
        return sharetext;
    }

    public void setSharetext(String sharetext) {
        this.sharetext = sharetext;
    }

    public String getChattext() {
        return chattext;
    }

    public void setChattext(String chattext) {
        this.chattext = chattext;
    }
}
