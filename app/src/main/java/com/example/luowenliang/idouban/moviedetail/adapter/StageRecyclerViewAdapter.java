package com.example.luowenliang.idouban.moviedetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;

import java.util.ArrayList;
import java.util.List;

public class StageRecyclerViewAdapter extends RecyclerView.Adapter<StageRecyclerViewAdapter.ViewHolder> {
    private List<StagePhotoInfo>stagePhotoInfos = new ArrayList<>();

    public StageRecyclerViewAdapter(List<StagePhotoInfo> stagePhotoInfos) {
        this.stagePhotoInfos = stagePhotoInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stage_photo_item,viewGroup,false);
       ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StageRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        final StagePhotoInfo stagePhotoInfo=stagePhotoInfos.get(i);
        //若为预告片显示视频可播放标识
        if(stagePhotoInfo.getVideoUrl()!=null){
            viewHolder.previewTitle.setVisibility(View.VISIBLE);
            viewHolder.previewStart.setVisibility(View.VISIBLE);
        }else{
            viewHolder.previewTitle.setVisibility(View.GONE);
            viewHolder.previewStart.setVisibility(View.GONE);
        }
        Glide.with(MyApplication.getContext()).load(stagePhotoInfo.getStagePhoto()).into(viewHolder.stagePhoto);
        /**点击，photoView查看详细图片或点击观看预告片*/
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listener.onClick(stagePhotoInfos,i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stagePhotoInfos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
    public ImageView stagePhoto;
    public CardView previewTitle;
    public TextView previewStart;

         ViewHolder( View itemView) {
            super(itemView);
            stagePhoto=itemView.findViewById(R.id.stage_image_view);
            previewTitle=itemView.findViewById(R.id.preview_title);
            previewStart=itemView.findViewById(R.id.preview_start);
        }
    }

    /**
     * 剧照图片点击监听
     */
    public interface OnPhotoClickListener{
        void onClick(List<StagePhotoInfo> stagePhotoInfos,int i);
    }
    private OnPhotoClickListener listener;

    public void setOnPhotoListener(OnPhotoClickListener listener) {
        this.listener = listener;
    }
}
