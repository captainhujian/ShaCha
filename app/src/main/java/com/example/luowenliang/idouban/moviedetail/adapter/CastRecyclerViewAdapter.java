package com.example.luowenliang.idouban.moviedetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luowenliang.idouban.R;

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder>  {
    @NonNull
    @Override
    public CastRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_item,viewGroup,false);
       ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView castPicture;
        public TextView castName;
        public TextView roleName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            castPicture = itemView.findViewById(R.id.cast_picture);
            castName=itemView.findViewById(R.id.cast_name);
            roleName=itemView.findViewById(R.id.role_name);
        }
    }
}
