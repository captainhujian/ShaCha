package com.example.luowenliang.idouban.castdetail.entity;

import java.io.Serializable;

public class CastDetailAlbumInfo implements Serializable {
    private String album;

    public CastDetailAlbumInfo(String album) {
        this.album = album;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
