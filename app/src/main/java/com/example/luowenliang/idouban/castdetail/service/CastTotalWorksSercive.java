package com.example.luowenliang.idouban.castdetail.service;

import com.example.luowenliang.idouban.castdetail.entity.WorksItem;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CastTotalWorksSercive {
    @GET("celebrity/{id}/works")
    rx.Observable<WorksItem> getCastTotalWorks(@Path("id")String id, @Query("start")String start, @Query("count")String count);
    @GET("celebrity/{id}/works")
    rx.Observable<WorksItem> getCastWorksCount(@Path("id")String id);
}
