package com.example.luowenliang.idouban.castdetail.entity;

public class CastDetailFilmInfo {
    String filmPicture;
    String filmTitle;
    double filmRating;
    String filmId;
    double fitFilmRate;

    public CastDetailFilmInfo(String filmPicture, String filmTitle, double filmRating, String filmId, double fitFilmRate) {
        this.filmPicture = filmPicture;
        this.filmTitle = filmTitle;
        this.filmRating = filmRating;
        this.filmId = filmId;
        this.fitFilmRate = fitFilmRate;
    }

    public String getFilmPicture() {
        return filmPicture;
    }

    public void setFilmPicture(String filmPicture) {
        this.filmPicture = filmPicture;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public double getFilmRating() {
        return filmRating;
    }

    public void setFilmRating(double filmRating) {
        this.filmRating = filmRating;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public double getFitFilmRate() {
        return fitFilmRate;
    }

    public void setFitFilmRate(double fitFilmRate) {
        this.fitFilmRate = fitFilmRate;
    }
}
