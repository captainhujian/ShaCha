package com.example.luowenliang.idouban.moviedetail.entity;

import java.io.Serializable;

public class StagePhotoInfo implements Serializable {
    private String stagePhoto;

    public StagePhotoInfo(String stagePhoto) {
        this.stagePhoto = stagePhoto;
    }

    public String getStagePhoto() {
        return stagePhoto;
    }

    public void setStagePhoto(String stagePhoto) {
        this.stagePhoto = stagePhoto;
    }
}
