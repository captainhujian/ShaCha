package com.example.luowenliang.idouban.movietop250.entity;

public class Top250Movie {
    String image;
    String title;
    String original_title;
    String cast1 ;
    String cast2;
    double rating;
    String genres1;
    String genres2;
    String durations;
    String year;
    String director;
    String id;

    public Top250Movie(String image, String title, String original_title, String cast1, String cast2, double rating, String genres1, String genres2, String durations, String year, String director, String id) {
        this.image = image;
        this.title = title;
        this.original_title = original_title;
        this.cast1 = cast1;
        this.cast2 = cast2;
        this.rating = rating;
        this.genres1 = genres1;
        this.genres2 = genres2;
        this.durations = durations;
        this.year = year;
        this.director = director;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getCast1() {
        return cast1;
    }

    public void setCast1(String cast1) {
        this.cast1 = cast1;
    }

    public String getCast2() {
        return cast2;
    }

    public void setCast2(String cast2) {
        this.cast2 = cast2;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGenres1() {
        return genres1;
    }

    public void setGenres1(String genres1) {
        this.genres1 = genres1;
    }

    public String getGenres2() {
        return genres2;
    }

    public void setGenres2(String genres2) {
        this.genres2 = genres2;
    }

    public String getDurations() {
        return durations;
    }

    public void setDurations(String durations) {
        this.durations = durations;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
