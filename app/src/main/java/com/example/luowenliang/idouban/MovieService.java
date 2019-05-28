package com.example.luowenliang.idouban;
import retrofit2.http.GET;


public interface MovieService {
    @GET("in_theaters?start=0&count=15")//top250?start=0&count=249
    rx.Observable<MovieItem> getMovie();//@Query("apikey") String apikey
}
