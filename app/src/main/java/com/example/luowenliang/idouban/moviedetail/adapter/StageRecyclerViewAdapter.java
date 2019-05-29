package com.example.luowenliang.idouban.moviedetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.luowenliang.idouban.R;

public class StageRecyclerViewAdapter extends RecyclerView.Adapter<StageRecyclerViewAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stage_photo_item,viewGroup,false);
       ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StageRecyclerViewAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
    public ImageView stagePhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stagePhoto=itemView.findViewById(R.id.stage_photo);
        }
    }
}
