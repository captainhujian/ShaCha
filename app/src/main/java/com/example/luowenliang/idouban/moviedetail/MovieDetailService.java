package com.example.luowenliang.idouban.moviedetail;

import com.example.luowenliang.idouban.moviedetail.entity.MovieDetailItem;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieDetailService {
    @GET("{id}")
    rx.Observable<MovieDetailItem> getMovieDetail(@Path("id")String id);
}
