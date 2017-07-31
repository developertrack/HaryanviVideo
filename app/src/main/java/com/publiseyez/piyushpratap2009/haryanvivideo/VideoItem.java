package com.publiseyez.piyushpratap2009.haryanvivideo;

public class VideoItem {
    private String title;
    private String description;
    private String thumbnailURL;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnail) {
        this.thumbnailURL = thumbnail;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String val = "" + getId() + "---" + getTitle() + "---" + getDescription() + "-----" + getThumbnailURL();
        return val;
    }


}