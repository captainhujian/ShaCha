package com.example.luowenliang.idouban.castdetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.castdetail.entity.CastDetailAlbumInfo;

import java.util.List;

public class CastAlbumRecyclerViewAdapter extends RecyclerView.Adapter<CastAlbumRecyclerViewAdapter.ViewHolder> {
    private List<CastDetailAlbumInfo>castDetailAlbumInfos;

    public CastAlbumRecyclerViewAdapter(List<CastDetailAlbumInfo> castDetailAlbumInfos) {
        this.castDetailAlbumInfos = castDetailAlbumInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_detail_album,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final CastDetailAlbumInfo castDetailAlbumInfo=castDetailAlbumInfos.get(i);
        Glide.with(MyApplication.getContext()).load(castDetailAlbumInfo.getAlbum()).into(viewHolder.album);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(castDetailAlbumInfos,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return castDetailAlbumInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView album;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            album = itemView.findViewById(R.id.album_view);
        }
    }
    /**
     * 剧照图片点击监听
     */
    public interface OnAlbumClickListener{
        void onClick(List<CastDetailAlbumInfo> castDetailAlbumInfos,int i);
    }
    private OnAlbumClickListener listener;

    public void setOnAlbumListener(OnAlbumClickListener listener) {
        this.listener = listener;
    }
}

