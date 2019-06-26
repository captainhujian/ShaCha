package com.example.luowenliang.idouban.moviehot.adapter;

import android.graphics.Color;
import android.os.Build;
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

import retrofit2.http.PUT;

public class PublicPraiseRecyclerViewAdapter extends RecyclerView.Adapter<PublicPraiseRecyclerViewAdapter.ViewHolder> {
    private List<HotMovieInfo> publicPraiseInfos;

    public PublicPraiseRecyclerViewAdapter(List<HotMovieInfo> publicPraiseInfos) {
        this.publicPraiseInfos = publicPraiseInfos;
        Log.d("热门", "口碑榜adapter里的hotmovielist: "+this.publicPraiseInfos.size());
    }


    @NonNull
    @Override
    public PublicPraiseRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_praise_item,viewGroup,false);
        PublicPraiseRecyclerViewAdapter.ViewHolder viewHolder=new PublicPraiseRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PublicPraiseRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final HotMovieInfo hotMovieInfo=publicPraiseInfos.get(i);
        viewHolder.publicPraiseRank.setText("No."+(i+1));
        //前三名的rank颜色不同
        switch (i){
            case 0:
                viewHolder.publicPraiseRank.setBackgroundColor(Color.parseColor("#EE6363"));
                break;
            case 1:
                viewHolder.publicPraiseRank.setBackgroundColor(Color.parseColor("#FF7F00"));
                break;
            case 2:
                viewHolder.publicPraiseRank.setBackgroundColor(Color.parseColor("#FFC125"));
                break;
                default:
                    break;

        }
        Glide.with(MyApplication.getContext()).load(hotMovieInfo.getHotMoviePicture()).into(viewHolder.publicPraisePicture);
        viewHolder.publicPraiseTitle.setText(hotMovieInfo.getHotMovieTitle());
        viewHolder.publicPraiseMessage.setText(hotMovieInfo.getHotMovieMessage());
        if (hotMovieInfo.getFitMovieRate() == 0f) {
            viewHolder.publicPraiseRatingBar.setVisibility(View.GONE);
            viewHolder.publicPraiseRating.setText("暂无评分");
        } else {
            //必须要有这句代码，不然会出现滑动后非0分电影星级条消失的情况
            viewHolder.publicPraiseRatingBar.setVisibility(View.VISIBLE);
            viewHolder.publicPraiseRating.setText(String.valueOf(hotMovieInfo.getHotMovieRating()));
            viewHolder.publicPraiseRatingBar.setStarCount(5);
            viewHolder.publicPraiseRatingBar.setStar((float) hotMovieInfo.getFitMovieRate());
        }
        viewHolder.publicPraisePubDateCard.setVisibility(View.GONE);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(hotMovieInfo.getHotMovieId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return publicPraiseInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView publicPraiseRank;
        public ImageView publicPraisePicture;
        public TextView publicPraiseTitle;
        public TextView publicPraiseRating;
        public TextView publicPraiseMessage;
        public RatingBar publicPraiseRatingBar;
        public CardView publicPraisePubDateCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            publicPraiseRank=itemView.findViewById(R.id.praise_rank);
            publicPraisePicture = itemView.findViewById(R.id.praise_image);
            publicPraiseTitle=itemView.findViewById(R.id.praise_title);
            publicPraiseRating=itemView.findViewById(R.id.praise_rating);
            publicPraiseMessage=itemView.findViewById(R.id.praise_message);
            publicPraiseRatingBar=itemView.findViewById(R.id.praise_rating_bar);
            publicPraiseRatingBar.setmClickable(false);
            publicPraisePubDateCard=itemView.findViewById(R.id.praise_pubdates_card);
            //适配android 9.0的行间距
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O_MR1) {
                publicPraiseMessage.setLineSpacing(1.1f,1f);
            }
        }
    }
    /**
     * 影视的点击事件
     */
    public interface OnpublicPraiseClickListener{
        void onClick(String hotMovieId);
    }
    public PublicPraiseRecyclerViewAdapter.OnpublicPraiseClickListener listener;
    public void setOnPublicPraiseClickListener(PublicPraiseRecyclerViewAdapter.OnpublicPraiseClickListener listener){
        this.listener=listener;
    }
}
