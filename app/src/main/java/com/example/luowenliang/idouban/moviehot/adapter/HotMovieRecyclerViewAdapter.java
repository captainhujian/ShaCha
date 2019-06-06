package com.example.luowenliang.idouban.moviehot.adapter;

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

public class HotMovieRecyclerViewAdapter extends RecyclerView.Adapter<HotMovieRecyclerViewAdapter.ViewHolder> {
    private List<HotMovieInfo> hotMovieInfos;

    public HotMovieRecyclerViewAdapter(List<HotMovieInfo> hotMovieInfos) {
        this.hotMovieInfos = hotMovieInfos;
        Log.d("热门", "热门adapter里的hotmovielist: "+this.hotMovieInfos.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hot_movie_item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final HotMovieInfo hotMovieInfo=hotMovieInfos.get(i);
        Glide.with(MyApplication.getContext()).load(hotMovieInfo.getHotMoviePicture()).into(viewHolder.hotMoviePicture);
        viewHolder.hotMovieTitle.setText(hotMovieInfo.getHotMovieTitle());
        if (hotMovieInfo.getFitMovieRate() == 0f) {
            viewHolder.hotMovieRatingBar.setVisibility(View.GONE);
            viewHolder.hotMovieRating.setText("暂无评分");
        } else {
            //必须要有这句代码，不然会出现滑动后非0分电影星级条消失的情况
            viewHolder.hotMovieRatingBar.setVisibility(View.VISIBLE);
            viewHolder.hotMovieRating.setText(String.valueOf(hotMovieInfo.getHotMovieRating()));
            viewHolder.hotMovieRatingBar.setStarCount(5);
            viewHolder.hotMovieRatingBar.setStar((float) hotMovieInfo.getFitMovieRate());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(hotMovieInfo.getHotMovieId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return hotMovieInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView hotMoviePicture;
        public TextView hotMovieTitle;
        public TextView hotMovieRating;
        public RatingBar hotMovieRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotMoviePicture = itemView.findViewById(R.id.hot_movie_picture);
            hotMovieTitle=itemView.findViewById(R.id.hot_movie_title);
            hotMovieRating=itemView.findViewById(R.id.hot_movie_rating);
            hotMovieRatingBar=itemView.findViewById(R.id.hot_movie_rating_bar);
            hotMovieRatingBar.setmClickable(false);
        }
    }
    /**
     * 影视的点击事件
     */
    public interface OnHotMovieClickListener{
        void onClick(String hotMovieId);
    }
    public OnHotMovieClickListener listener;
    public void setOnHotMovieClickListener(OnHotMovieClickListener listener){
        this.listener=listener;
    }
}
