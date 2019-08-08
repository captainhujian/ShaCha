package com.example.luowenliang.idouban.moviedetail.service;

import com.example.luowenliang.idouban.moviedetail.entity.TotalStagePhotosItem;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TotalStagePhotosService {
    @GET("{id}/photos")
    rx.Observable<TotalStagePhotosItem> getTotalStagePhotos(@Path("id")String id,@Query("start")String start, @Query("count")String count);
}
