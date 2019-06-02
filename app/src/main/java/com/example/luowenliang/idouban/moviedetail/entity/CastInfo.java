package com.example.luowenliang.idouban.moviedetail.entity;

public class CastInfo {
    private String castId;
    private String castPicture;
    private String castName;
    private String enName;

    public CastInfo(String castPicture, String castName, String enName,String castId) {
        this.castId = castId;
        this.castPicture = castPicture;
        this.castName = castName;
        this.enName = enName;
    }

    public String getCastId() {
        return castId;
    }

    public void setCastId(String castId) {
        this.castId = castId;
    }

    public String getCastPicture() {
        return castPicture;
    }

    public void setCastPicture(String castPicture) {
        this.castPicture = castPicture;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}
