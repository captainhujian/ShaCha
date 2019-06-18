package com.example.luowenliang.idouban.moviehot.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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

/**
 * 即将上映adapter复用HotMovieItem的布局
 */
public class ComingSoonRecyclerViewAdapter extends RecyclerView.Adapter<ComingSoonRecyclerViewAdapter.ViewHolder>{
    private List<HotMovieInfo> comingSoonInfos;

    public ComingSoonRecyclerViewAdapter(List<HotMovieInfo> comingSoonInfos) {
        this.comingSoonInfos = comingSoonInfos;
        Log.d("热门", "即将上映adapter里的hotmovielist: "+this.comingSoonInfos.size());
    }
    @NonNull
    @Override
    public ComingSoonRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hot_movie_item,viewGroup,false);
        ComingSoonRecyclerViewAdapter.ViewHolder viewHolder=new ComingSoonRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComingSoonRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final HotMovieInfo hotMovieInfo=comingSoonInfos.get(i);
        Glide.with(MyApplication.getContext()).load(hotMovieInfo.getHotMoviePicture()).into(viewHolder.hotMoviePicture);
        viewHolder.hotMovieTitle.setText(hotMovieInfo.getHotMovieTitle());
        viewHolder.hotMovieRating.setVisibility(View.GONE);
        viewHolder.hotMovieRatingBar.setVisibility(View.GONE);
        viewHolder.hotMovieCollect.setVisibility(View.VISIBLE);
        viewHolder.hotMovieUpDateCard.setVisibility(View.VISIBLE);
        viewHolder.hotMovieCollect.setText(hotMovieInfo.getHotMovieCollect());
        viewHolder.hotMovieUpDate.setText(hotMovieInfo.getHotMovieUpDate());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(hotMovieInfo.getHotMovieId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return comingSoonInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView hotMoviePicture;
        public TextView hotMovieTitle;
        public TextView hotMovieRating;
        public RatingBar hotMovieRatingBar;
        public TextView hotMovieCollect;
        public CardView hotMovieUpDateCard;
        public TextView hotMovieUpDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotMoviePicture = itemView.findViewById(R.id.hot_movie_picture);
            hotMovieTitle=itemView.findViewById(R.id.hot_movie_title);
            hotMovieRating=itemView.findViewById(R.id.hot_movie_rating);
            hotMovieRatingBar=itemView.findViewById(R.id.hot_movie_rating_bar);
            hotMovieRatingBar.setmClickable(false);
            hotMovieCollect=itemView.findViewById(R.id.hot_movie_collect);
            hotMovieUpDateCard=itemView.findViewById(R.id.hot_movie_pubdates_card);
            hotMovieUpDate=itemView.findViewById(R.id.hot_movie_pubdates);
        }
    }
    /**
     * 影视的点击事件
     */
    public interface OnComingSoonClickListener{
        void onClick(String hotMovieId);
    }
    public ComingSoonRecyclerViewAdapter.OnComingSoonClickListener listener;
    public void setOnComingSoonClickListener(ComingSoonRecyclerViewAdapter.OnComingSoonClickListener listener){
        this.listener=listener;
    }
}
