package com.example.luowenliang.idouban.moviedetail.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class MyImageAdapter extends PagerAdapter {

    private List<StagePhotoInfo>stagePhotoInfos;
    private AppCompatActivity activity;

    public MyImageAdapter(List<StagePhotoInfo> stagePhotoInfos, AppCompatActivity activity) {
        this.stagePhotoInfos = stagePhotoInfos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       String stagephoto =stagePhotoInfos.get(position).getStagePhoto();
        PhotoView photoView = new PhotoView(activity);
        Glide.with(MyApplication.getContext()).load(stagephoto).into(photoView);
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("剧照", "onClick: ");
                activity.finish();
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        return stagePhotoInfos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
