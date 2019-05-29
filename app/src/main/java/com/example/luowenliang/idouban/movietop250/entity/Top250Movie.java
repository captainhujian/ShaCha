package com.example.luowenliang.idouban.movietop250.entity;

public class Top250Movie {
    String image;
    String title;
    String original_title;
    String cast ;
    double rating;
    String genres;
    String durations;
    String year;
    String director;
    String id;

    public Top250Movie(String image, String title, String original_title, String cast, double rating, String genres, String durations, String year, String director, String id) {
        this.image = image;
        this.title = title;
        this.original_title = original_title;
        this.cast = cast;
        this.rating = rating;
        this.genres = genres;
        this.durations = durations;
        this.year = year;
        this.director = director;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
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
}
