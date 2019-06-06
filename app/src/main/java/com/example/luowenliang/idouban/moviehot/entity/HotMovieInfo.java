package com.example.luowenliang.idouban.moviehot.entity;

public class HotMovieInfo {
    String hotMoviePicture;
    String hotMovieTitle;
    double hotMovieRating;
    String hotMovieId;
    double fitMovieRate;
    String hotMovieMessage;

    public HotMovieInfo(String hotMoviePicture, String hotMovieTitle, double hotMovieRating, String hotMovieId, double fitMovieRate, String hotMovieMessage) {
        this.hotMoviePicture = hotMoviePicture;
        this.hotMovieTitle = hotMovieTitle;
        this.hotMovieRating = hotMovieRating;
        this.hotMovieId = hotMovieId;
        this.fitMovieRate = fitMovieRate;
        this.hotMovieMessage = hotMovieMessage;
    }

    public String getHotMoviePicture() {
        return hotMoviePicture;
    }

    public void setHotMoviePicture(String hotMoviePicture) {
        this.hotMoviePicture = hotMoviePicture;
    }

    public String getHotMovieTitle() {
        return hotMovieTitle;
    }

    public void setHotMovieTitle(String hotMovieTitle) {
        this.hotMovieTitle = hotMovieTitle;
    }

    public double getHotMovieRating() {
        return hotMovieRating;
    }

    public void setHotMovieRating(double hotMovieRating) {
        this.hotMovieRating = hotMovieRating;
    }

    public String getHotMovieId() {
        return hotMovieId;
    }

    public void setHotMovieId(String hotMovieId) {
        this.hotMovieId = hotMovieId;
    }

    public double getFitMovieRate() {
        return fitMovieRate;
    }

    public void setFitMovieRate(double fitMovieRate) {
        this.fitMovieRate = fitMovieRate;
    }

    public String getHotMovieMessage() {
        return hotMovieMessage;
    }

    public void setHotMovieMessage(String hotMovieMessage) {
        this.hotMovieMessage = hotMovieMessage;
    }
}
