package com.example.luowenliang.idouban.moviedetail.service;

import com.example.luowenliang.idouban.moviedetail.entity.CommentsItem;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TotalCommentsService {
    @GET("{id}/comments")
    rx.Observable<CommentsItem> getTotalComments(@Path("id")String id, @Query("start")String start, @Query("count")String count);
}
