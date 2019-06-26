package com.example.luowenliang.idouban.photoViewer;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class MyImageAdapter extends PagerAdapter  {

    private List<StagePhotoInfo>stagePhotoInfos;
    private AppCompatActivity activity;

    public MyImageAdapter(List<StagePhotoInfo> stagePhotoInfos, AppCompatActivity activity) {
        this.stagePhotoInfos = stagePhotoInfos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
       final String stagephoto =stagePhotoInfos.get(position).getStagePhoto();
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
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(listener!=null){
                    listener.onLongClick(position,stagephoto);

                }

                return false;
            }
        });
        return photoView;
    }

    /**
     *监听图片长按事件
     * @return
     */
    public interface OnImageLongClickListener {
        void onLongClick(int position,String stagephoto);
    }

    private OnImageLongClickListener listener;

    public void setOnImageLongClickListener(OnImageLongClickListener listener) {
        this.listener = listener;
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
