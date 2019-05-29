package com.example.luowenliang.idouban.movietop250;

import com.example.luowenliang.idouban.movietop250.entity.MovieItem;

import retrofit2.http.GET;


public interface Top250MovieService {
    @GET("top250?start=0&count=249")//in_theaters?start=0&count=15
    rx.Observable<MovieItem> getMovie();//@Query("apikey") String apikey
}
