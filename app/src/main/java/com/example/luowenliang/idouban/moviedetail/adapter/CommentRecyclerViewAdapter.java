package com.example.luowenliang.idouban.moviedetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luowenliang.idouban.R;
import com.hedgehog.ratingbar.RatingBar;
import com.shehuan.niv.NiceImageView;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item,viewGroup,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
     public class ViewHolder extends RecyclerView.ViewHolder{
        public NiceImageView conmentePicture;
        public TextView commenterName;
        public RatingBar commenterRatingBar;
        public TextView commentTime;
        public TextView comment;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
            conmentePicture= itemView.findViewById(R.id.commenter_picture);
            commenterName=itemView.findViewById(R.id.commenter_name);
            commenterRatingBar=itemView.findViewById(R.id.commenter_rating_bar);
            commentTime=itemView.findViewById(R.id.commenter_time);
            comment=itemView.findViewById(R.id.comment);
         }
     }
}
