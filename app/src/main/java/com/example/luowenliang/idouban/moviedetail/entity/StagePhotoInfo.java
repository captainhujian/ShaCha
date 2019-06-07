package com.example.luowenliang.idouban.moviedetail.entity;

import java.io.Serializable;

public class StagePhotoInfo implements Serializable {
    private String stagePhoto;
    private String videoUrl;
    private String videoTitle;

    public StagePhotoInfo(String stagePhoto, String videoUrl, String videoTitle) {
        this.stagePhoto = stagePhoto;
        this.videoUrl = videoUrl;
        this.videoTitle = videoTitle;
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
}
