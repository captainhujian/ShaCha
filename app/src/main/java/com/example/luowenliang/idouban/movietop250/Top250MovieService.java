package com.example.luowenliang.idouban.movietop250;

import com.example.luowenliang.idouban.movietop250.entity.MovieItem;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Top250MovieService {
    @GET("top250?start=10&count=20")//in_theaters?start=0&count=15
    rx.Observable<MovieItem> getMovie();//@Query("start")int start, @Query("count")int count
}
