package com.example.luowenliang.idouban.movietop250;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.movietop250.entity.Top250Movie;

import java.math.BigDecimal;
import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    private List<Top250Movie> top250MovieList;

    public MovieRecyclerViewAdapter(List<Top250Movie> top250MovieList) {
        this.top250MovieList = top250MovieList;
    }

    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView original_title;
        TextView rating;
        TextView genres;
        TextView durations;
        TextView year;
        TextView number;
        TextView director_cast;
        com.hedgehog.ratingbar.RatingBar ratingBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            original_title = itemView.findViewById(R.id.origin_title);
            rating = itemView.findViewById(R.id.rating);
            genres = itemView.findViewById(R.id.genres);
            durations = itemView.findViewById(R.id.durations);
            year = itemView.findViewById(R.id.year);
            director_cast = itemView.findViewById(R.id.director_cast);
            number = itemView.findViewById(R.id.number);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Top250Movie top250Movie = top250MovieList.get(i);
        Glide.with(MyApplication.getContext()).load(top250Movie.getImage()).into(viewHolder.image);
        viewHolder.durations.setText(top250Movie.getDurations());
        viewHolder.year.setText(top250Movie.getYear());
        viewHolder.genres.setText(top250Movie.getGenres());
        viewHolder.original_title.setText(top250Movie.getOriginal_title());
        viewHolder.title.setText(top250Movie.getTitle());
        viewHolder.director_cast.setText(top250Movie.getDirector() + "/" + top250Movie.getCast());
        viewHolder.number.setText("No." + (i + 1));
        Log.d("分数", "名字: "+top250Movie.getTitle());
        //星级评分
        double fitRate = fitRating(top250Movie.getRating());
        Log.d("分数", "合理的分数: "+fitRate);
        if(fitRate==0f){
            viewHolder.ratingBar.setVisibility(View.GONE);
            viewHolder.rating.setText("暂无评分");
        }else {
            //必须要有这句代码，不然会出现滑动后非0分电影星级条消失的情况
            viewHolder.ratingBar.setVisibility(View.VISIBLE);
            viewHolder.rating.setText(String.valueOf(top250Movie.getRating()));
            viewHolder.ratingBar.setStarCount(5);
            viewHolder.ratingBar.setStar((float) fitRate);
        }
        //点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(top250Movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return top250MovieList.size();
    }

    /**
     * item点击事件
     */
    public interface OnItemClickListener {
        void onClick(Top250Movie top250Movie);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 转换合理的评分以星级显示
     */
    public double fitRating(double rating) {
        double ratingIn5 = 0;
        if(rating>=9.2f){
                ratingIn5 = 5f;
        }else if(rating>=2f){
            BigDecimal tempRating = new BigDecimal(rating).setScale(0, BigDecimal.ROUND_HALF_UP);
            ratingIn5=tempRating.doubleValue()/2f;
        }else {
            if(rating==0f){
                ratingIn5=0f;
            }else {
                ratingIn5=1f;
            }

        }

    return ratingIn5;
    }

}
