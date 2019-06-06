package com.example.luowenliang.idouban.moviehot.service;

import com.example.luowenliang.idouban.moviehot.SearchItem;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {
    @GET("search")
    rx.Observable<SearchItem> getSearchResult(@Query("q")String query);//
}
