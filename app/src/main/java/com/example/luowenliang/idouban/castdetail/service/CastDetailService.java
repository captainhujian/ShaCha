package com.example.luowenliang.idouban.castdetail.service;

import com.example.luowenliang.idouban.castdetail.entity.CastDetailItem;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CastDetailService {
    @GET("{id}")
    rx.Observable<CastDetailItem> getCastDetail(@Path("id")String id);
}
