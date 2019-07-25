package com.example.luowenliang.idouban.moviedetail.utils;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.MovieDetailItem;
import com.hedgehog.ratingbar.RatingBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static java.util.Arrays.asList;

/**
 * 获取电影详情非RecyclerView的数据
 */
public class SetMovieDetailData {
    private static final String TAG = "取色";
    boolean isTelevision;
    private MovieDetailItem movieDetailItem;
    private TextView message,detailTitleText;
    private CardView stagePhotoCard;
    private String genres,genre1,genre2,oTitle,year,country,pubdate,duration,episodesCount;
    private  Palette.Swatch swatch,swatch2;
    private int backColor;
    private List<String>colorList=new ArrayList<>();
    private View view;
    private Toolbar detailToolbar;

    public SetMovieDetailData(MovieDetailItem movieDetailItem) {
        this.movieDetailItem = movieDetailItem;
    }

    public void setMovieMessage(TextView detailTitleText, ImageView image, TextView title, TextView originTitleYear, TextView message,
                                RatingBar ratingBar, TextView rating, TextView noneRating, ProgressBar star5, ProgressBar star4,
                                ProgressBar star3, ProgressBar star2, ProgressBar star1, TextView starCount, TextView summary,
                                View view, Toolbar detailToolbar, CardView stagePhotoCard) {
        this.view=view;
        this.detailToolbar=detailToolbar;
        this.stagePhotoCard=stagePhotoCard;
        this.message=message;
        this.detailTitleText=detailTitleText;
        Glide.with(MyApplication.getContext())
                .load(movieDetailItem.getImages().getLarge())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .apply(new RequestOptions().error(R.drawable.placeholder))
                .into(image);
        //获取glide加载的bitmap图片，设置背景色
        Glide.with(MyApplication.getContext())
                .asBitmap()
                .load(movieDetailItem.getImages().getLarge())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        setBackGroundColor(resource);
                    }
                });
        title.setText(movieDetailItem.getTitle());
        evadeMessageNull();
        message.setText(country+genres+pubdate+episodesCount+duration+">");
        originTitleYear.setText(oTitle+year);
        summary.setText(movieDetailItem.getSummary());
        //星级评分
        double fitRate = fitRating(movieDetailItem.getRating().getAverage());
        Log.d("分数", "合理的分数: " + fitRate);
        //暂无评价
        if (fitRate == 0f) {
            ratingBar.setVisibility(View.GONE);
            rating.setVisibility(View.GONE);
            noneRating.setVisibility(View.VISIBLE);

        } else {
            ratingBar.setVisibility(View.VISIBLE);
            rating.setText(String.valueOf(movieDetailItem.getRating().getAverage()));
            ratingBar.setStarCount(5);
            ratingBar.setStar((float) fitRate);
        }
            //横向评分条
             int c5 = (int) movieDetailItem.getRating().getDetails().get_$5();
             int c4 = (int) movieDetailItem.getRating().getDetails().get_$4();
             int c3 = (int) movieDetailItem.getRating().getDetails().get_$3();
             int c2 = (int) movieDetailItem.getRating().getDetails().get_$2();
             int c1 = (int) movieDetailItem.getRating().getDetails().get_$1();
             int realCount = c5 + c4 + c3 + c2 + c1;
             star5.setMax(realCount);
             star5.setProgress(c5);
             star4.setMax(realCount);
             star4.setProgress(c4);
             star3.setMax(realCount);
             star3.setProgress(c3);
             star2.setMax(realCount);
             star2.setProgress(c2);
             star1.setMax(realCount);
             star1.setProgress(c1);
        starCount.setText(movieDetailItem.getRatings_count() + "人评分");
    }

    /**
     * 此方法用于message数据判空规避
     */
    private void evadeMessageNull() {
        //原名、年份
        oTitle=movieDetailItem.getOriginal_title();
        year=movieDetailItem.getYear();
        if(oTitle==null){
            oTitle="";
        }
        if(year==null){
            year="";
        }else {
            year= "("+year+")";
        }
        //国家
        if(movieDetailItem.getCountries()==null){
            country="";
        }else {
            country=movieDetailItem.getCountries().get(0)+"/";
        }
        //影片类型
        getGenres();
        //上映时间
        if(movieDetailItem.getMainland_pubdate().equals("")){
            if(movieDetailItem.getPubdates().size()!=0){
                pubdate=movieDetailItem.getPubdates().get(0);
            }else {
                pubdate="";
            }
        }else {
            pubdate=movieDetailItem.getMainland_pubdate()+"(中国大陆)";
        }
        pubdate="上映时间："+pubdate+"/";
        //时长
        if(movieDetailItem.getDurations().size()==0){
            duration="";
        }else {
            duration= movieDetailItem.getDurations().get(0);
        }
        //集数
        episodesCount= (String) movieDetailItem.getEpisodes_count();
        if(episodesCount==null){
            isTelevision=false;
            episodesCount="";
            if(!duration.equals("")){
                duration="片长："+duration;
            }

        }else {
            isTelevision=true;
            episodesCount="集数："+episodesCount+"集/";
            if(!duration.equals("")){
                duration="单集片长："+duration;
            }
        }

        //影视详情标题
        if(isTelevision){
            detailTitleText.setText("电视");
        }else {
            detailTitleText.setText("电影");
        }
    }

    /**
     * 根据海报颜色动态设置详情界面背景色
     */
    public void setBackGroundColor(Bitmap poster) {
        if(movieDetailItem.getImages()!=null){
            Log.d(TAG, "poster:"+poster);
            Palette.from(poster).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(@NonNull Palette palette) {
                    swatch = palette.getMutedSwatch();
                    swatch2=palette.getDarkVibrantSwatch();
                    Log.d(TAG, "back:"+swatch);
                    if (swatch != null) {
                        backColor=swatch.getRgb();
                    }else {
                        if(swatch2!=null){
                            backColor = swatch2.getRgb();
                        }else {
                            Log.d(TAG, "没取到颜色");
                            String colorString="#4A708B";
                            backColor=Color.parseColor(colorString);
                        }
                    }
                    //储存背景颜色到sharepreference里
                    SharePreferencesUtil.putInt(MyApplication.getContext(),"background_color",backColor);
                    view.setBackgroundColor(backColor);
                    detailToolbar.setBackgroundColor(backColor);
                    stagePhotoCard.setCardBackgroundColor(backColor);
                }
            });
        }

    }


    /**
         * 获取前两个影片类型
         */
        private void getGenres () {
            if(movieDetailItem.getGenres().size()==0){
                genre1="";genre2="";
            }else{
                if (movieDetailItem.getGenres().size() >= 2) {
                    genre1 = movieDetailItem.getGenres().get(0);
                    genre2 = " " + movieDetailItem.getGenres().get(1)+"/";
                } else {
                    genre1 = movieDetailItem.getGenres().get(0);
                    genre2 = "/";
                }
            }

            genres = genre1 + genre2;
        }
        /**
         * 转换合理的评分以星级显示
         */
        public static double fitRating ( double rating) {
            double ratingIn5 = 0;
            if (rating >= 9.2f) {
                ratingIn5 = 5f;
            } else if (rating >= 2f) {
                BigDecimal tempRating = new BigDecimal(rating).setScale(0, BigDecimal.ROUND_HALF_UP);
                ratingIn5 = tempRating.doubleValue() / 2f;
            } else {
                if (rating == 0f) {
                    ratingIn5 = 0f;
                } else {
                    ratingIn5 = 1f;
                }

            }
            return ratingIn5;
        }
}
