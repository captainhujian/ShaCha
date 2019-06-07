package com.example.luowenliang.idouban.castdetail.adapter;

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
import com.example.luowenliang.idouban.castdetail.entity.CastDetailFilmInfo;
import com.hedgehog.ratingbar.RatingBar;

import java.util.List;

public class CastFilmRecyclerViewAdapter extends RecyclerView.Adapter<CastFilmRecyclerViewAdapter.ViewHolder>{
    private List<CastDetailFilmInfo>castDetailFilmInfos;

    public CastFilmRecyclerViewAdapter(List<CastDetailFilmInfo> castDetailFilmInfos) {
        this.castDetailFilmInfos = castDetailFilmInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_detail_film_item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CastDetailFilmInfo castDetailFilmInfo=castDetailFilmInfos.get(i);
        Log.d("影人详情", "castDetailFilmInfo: "+castDetailFilmInfo);
        Glide.with(MyApplication.getContext()).load(castDetailFilmInfo.getFilmPicture()).into(viewHolder.filmPicture);
        viewHolder.filmTitle.setText(castDetailFilmInfo.getFilmTitle());
        if (castDetailFilmInfo.getFitFilmRate() == 0f) {
            viewHolder.filmRatingBar.setVisibility(View.GONE);
            viewHolder.filmRating.setText("暂无评分");
        } else {
            //必须要有这句代码，不然会出现滑动后非0分电影星级条消失的情况
            viewHolder.filmRatingBar.setVisibility(View.VISIBLE);
            viewHolder.filmRating.setText(String.valueOf(castDetailFilmInfo.getFilmRating()));
            viewHolder.filmRatingBar.setStarCount(5);
            viewHolder.filmRatingBar.setStar((float) castDetailFilmInfo.getFitFilmRate());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("最后的错误", "id： "+castDetailFilmInfo.getFilmId());
                listener.onClick(castDetailFilmInfo.getFilmId());
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("影人详情", "size="+castDetailFilmInfos.size());
        return castDetailFilmInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView filmPicture;
        public TextView filmTitle;
        public TextView filmRating;
        public RatingBar filmRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            filmPicture = itemView.findViewById(R.id.film_picture);
            filmTitle=itemView.findViewById(R.id.film_title);
            filmRating=itemView.findViewById(R.id.film_rating);
            filmRatingBar=itemView.findViewById(R.id.film_rating_bar);
            filmRatingBar.setmClickable(false);
        }
    }
    /**
     * 影视的点击事件
     */
    public interface OnFilmClickListener{
        void onClick(String filmId);
    }
    private OnFilmClickListener listener;
    public void setOnFilmClickListener(OnFilmClickListener listener){
        this.listener=listener;
    }
}
