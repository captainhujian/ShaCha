package com.example.luowenliang.idouban.moviedetail.service;

import com.example.luowenliang.idouban.moviedetail.entity.TotalStagePhotosItem;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TotalStagePhotosService {
    @GET("{id}/photos?start=0&count=100")
    rx.Observable<TotalStagePhotosItem> getTotalStagePhotos(@Path("id")String id);
}
