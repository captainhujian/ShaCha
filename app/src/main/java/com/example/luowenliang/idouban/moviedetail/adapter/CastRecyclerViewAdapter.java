package com.example.luowenliang.idouban.moviedetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.CastInfo;

import java.util.List;

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder>  {
    private List<CastInfo>castInfos;

    public CastRecyclerViewAdapter(List<CastInfo> castInfos) {
        this.castInfos = castInfos;
    }

    @NonNull
    @Override
    public CastRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_item,viewGroup,false);
       ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CastInfo castInfo=castInfos.get(i);
        Glide.with(MyApplication.getContext()).load(castInfo.getCastPicture()).into(viewHolder.castPicture);
        viewHolder.castName.setText(castInfo.getCastName());
        viewHolder.enName.setText(castInfo.getEnName());
    }

    @Override
    public int getItemCount() {
        return castInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView castPicture;
        public TextView castName;
        public TextView enName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            castPicture = itemView.findViewById(R.id.cast_picture);
            castName=itemView.findViewById(R.id.cast_name);
            enName=itemView.findViewById(R.id.en_name);
        }
    }
}
