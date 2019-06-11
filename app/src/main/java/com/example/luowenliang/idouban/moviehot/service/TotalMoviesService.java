package com.example.luowenliang.idouban.moviehot.service;


import com.example.luowenliang.idouban.moviehot.entity.HotMovieItem;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TotalMoviesService {
    @GET("{url}")
    rx.Observable<HotMovieItem> getMovieTotal(@Path("url")String url);
}
