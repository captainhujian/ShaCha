package com.example.luowenliang.idouban.moviedetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.MovieResourceInfo;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;
import java.util.List;

public class ResourceIconRecyclerViewAdapter extends RecyclerView.Adapter<ResourceIconRecyclerViewAdapter.ViewHolder> {
    private List<MovieResourceInfo>movieResourceInfos= new ArrayList<>();

    public ResourceIconRecyclerViewAdapter(List<MovieResourceInfo> movieResourceInfos) {
        this.movieResourceInfos = movieResourceInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resource_icon_item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String resourceIcon=movieResourceInfos.get(i).getResourceIcon();
        Glide.with(MyApplication.getContext())
                .load(resourceIcon)
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .apply(new RequestOptions().error(R.drawable.placeholder))
                .into(viewHolder.movieResourceIcon);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieResourceInfos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public NiceImageView movieResourceIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieResourceIcon = itemView.findViewById(R.id.resource_icon);
        }
    }

    /**
     * item点击事件
     */
    public interface OnResourceIconClickListener {
        void onClick();
    }

    private OnResourceIconClickListener listener;

    public void setOnResourceIconClickListener(OnResourceIconClickListener listener) {
        this.listener = listener;
    }
}
