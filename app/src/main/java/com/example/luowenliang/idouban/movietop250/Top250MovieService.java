package com.example.luowenliang.idouban.movietop250;

import com.example.luowenliang.idouban.movietop250.entity.MovieItem;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Top250MovieService {
    @GET("top250")
    rx.Observable<MovieItem> getMovie(@Query("start")String start, @Query("count")String count);//
}
