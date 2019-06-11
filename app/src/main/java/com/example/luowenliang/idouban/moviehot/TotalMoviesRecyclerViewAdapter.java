package com.example.luowenliang.idouban.moviehot;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviehot.entity.HotMovieInfo;
import com.hedgehog.ratingbar.RatingBar;

import java.util.List;

public class TotalMoviesRecyclerViewAdapter extends RecyclerView.Adapter<TotalMoviesRecyclerViewAdapter.ViewHolder>{
    private List<HotMovieInfo> totalMovieInfos;

    public TotalMoviesRecyclerViewAdapter(List<HotMovieInfo> totalmovieInfos) {
        this.totalMovieInfos = totalmovieInfos;
        Log.d("热门", "adapter里的totalmovielist: "+this.totalMovieInfos.size());
    }


    @NonNull
    @Override
    /**
     * 复用口碑榜的item
     */
    public TotalMoviesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_praise_item,viewGroup,false);
        TotalMoviesRecyclerViewAdapter.ViewHolder viewHolder=new TotalMoviesRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TotalMoviesRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final HotMovieInfo totalMovieInfo=totalMovieInfos.get(i);
        viewHolder.totalMovieRank.setText("No."+(i+1));
        Glide.with(MyApplication.getContext()).load(totalMovieInfo.getHotMoviePicture()).into(viewHolder.totalMoviePicture);
        viewHolder.totalMovieTitle.setText(totalMovieInfo.getHotMovieTitle());
        viewHolder.totalMovieMessage.setText(totalMovieInfo.getHotMovieMessage());
        if (totalMovieInfo.getFitMovieRate() == 0f) {
            viewHolder.totalMovieRatingBar.setVisibility(View.GONE);
            viewHolder.totalMovieRating.setText("暂无评分");
        } else {
            //必须要有这句代码，不然会出现滑动后非0分电影星级条消失的情况
            viewHolder.totalMovieRatingBar.setVisibility(View.VISIBLE);
            viewHolder.totalMovieRating.setText(String.valueOf(totalMovieInfo.getHotMovieRating()));
            viewHolder.totalMovieRatingBar.setStarCount(5);
            viewHolder.totalMovieRatingBar.setStar((float) totalMovieInfo.getFitMovieRate());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(totalMovieInfo.getHotMovieId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return totalMovieInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView totalMovieRank;
        public ImageView totalMoviePicture;
        public TextView totalMovieTitle;
        public TextView totalMovieRating;
        public TextView totalMovieMessage;
        public RatingBar totalMovieRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            totalMovieRank=itemView.findViewById(R.id.praise_rank);
            totalMoviePicture = itemView.findViewById(R.id.praise_image);
            totalMovieTitle=itemView.findViewById(R.id.praise_title);
            totalMovieRating=itemView.findViewById(R.id.praise_rating);
            totalMovieMessage=itemView.findViewById(R.id.praise_message);
            totalMovieRatingBar=itemView.findViewById(R.id.praise_rating_bar);
            totalMovieRatingBar.setmClickable(false);
        }
    }
    /**
     * 影视的点击事件
     */
    public interface OnTotalMovieClickListener{
        void onClick(String movieId);
    }
    public TotalMoviesRecyclerViewAdapter.OnTotalMovieClickListener listener;
    public void setOnTotalMovieClickListener(TotalMoviesRecyclerViewAdapter.OnTotalMovieClickListener listener){
        this.listener=listener;
    }
}
