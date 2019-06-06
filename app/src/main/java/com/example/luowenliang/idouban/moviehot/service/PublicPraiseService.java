package com.example.luowenliang.idouban.moviehot.service;

import com.example.luowenliang.idouban.moviehot.entity.HotMovieItem;

import retrofit2.http.GET;

public interface PublicPraiseService {
    @GET("weekly")
    rx.Observable<HotMovieItem> getPublicPraiseResult();//
}
