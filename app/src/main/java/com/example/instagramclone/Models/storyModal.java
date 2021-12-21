package com.example.instagramclone.Models;

public class storyModal{

    int story,status,profile;
    String name;

    public storyModal(int story, int status, int profile, String name) {
        this.story = story;
        this.status = status;
        this.profile = profile;
        this.name = name;
    }

    public int getStory() {
        return story;
    }

    public void setStory(int story) {
        this.story = story;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int storyype) {
        this.status = storyype;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}


