package com.example.instagramclone.Models;

import java.util.ArrayList;

public class storyModal{
    String storyby;
    long storyat;
    ArrayList<userstories>userstories;

    public storyModal(String storyby, long storyat, ArrayList<com.example.instagramclone.Models.userstories> userstories) {
        this.storyby = storyby;
        this.storyat = storyat;
        this.userstories = userstories;
    }

    public storyModal() {
    }

    public String getStoryby() {
        return storyby;
    }

    public void setStoryby(String storyby) {
        this.storyby = storyby;
    }

    public long getStoryat() {
        return storyat;
    }

    public void setStoryat(long storyat) {
        this.storyat = storyat;
    }

    public ArrayList<com.example.instagramclone.Models.userstories> getUserstories() {
        return userstories;
    }

    public void setUserstories(ArrayList<com.example.instagramclone.Models.userstories> userstories) {
        this.userstories = userstories;
    }
}


