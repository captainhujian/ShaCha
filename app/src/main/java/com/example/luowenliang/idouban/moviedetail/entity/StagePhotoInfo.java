package com.example.luowenliang.idouban.moviedetail.entity;

import java.io.Serializable;

public class StagePhotoInfo implements Serializable {
    private String stagePhoto;
    private String videoUrl;
    private String videoTitle;
    private int videoStyle;

    public StagePhotoInfo(String stagePhoto, String videoUrl, String videoTitle, int videoStyle) {
        this.stagePhoto = stagePhoto;
        this.videoUrl = videoUrl;
        this.videoTitle = videoTitle;
        this.videoStyle = videoStyle;
    }

    public String getStagePhoto() {
        return stagePhoto;
    }

    public void setStagePhoto(String stagePhoto) {
        this.stagePhoto = stagePhoto;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public int getVideoStyle() {
        return videoStyle;
    }

    public void setVideoStyle(int videoStyle) {
        this.videoStyle = videoStyle;
    }
}
