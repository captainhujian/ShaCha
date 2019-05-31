package com.example.luowenliang.idouban.moviedetail;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.MovieDetailItem;
import com.hedgehog.ratingbar.RatingBar;

import java.math.BigDecimal;

public class SetDetailData {

    private MovieDetailItem movieDetailItem;
    private String genres,genre1,genre2;

    public SetDetailData(MovieDetailItem movieDetailItem) {
        this.movieDetailItem = movieDetailItem;
    }

    public void setMovieMessage(ImageView image, TextView title, TextView originTitleYear, TextView message, RatingBar ratingBar, TextView rating
    , ProgressBar star5,ProgressBar star4,ProgressBar star3,ProgressBar star2,ProgressBar star1,TextView starCount,TextView summary) {
        Glide.with(MyApplication.getContext()).load(movieDetailItem.getImages().getLarge()).into(image);
        title.setText(movieDetailItem.getTitle());
        originTitleYear.setText(movieDetailItem.getOriginal_title() + " (" + movieDetailItem.getYear() + ")");
        getGenres();
        message.setText(movieDetailItem.getCountries().get(0) + "/" + genres + "/上映时间：" + movieDetailItem.getPubdates().get(0)
                + "/片长：" + movieDetailItem.getDurations().get(0) + ">");
        summary.setText(movieDetailItem.getSummary());
        //星级评分
        double fitRate = fitRating(movieDetailItem.getRating().getAverage());
        Log.d("分数", "合理的分数: " + fitRate);
        if (fitRate == 0f) {
            ratingBar.setVisibility(View.GONE);
            rating.setText("暂无评分");
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
        int realCount = c5+c4+c3+c2+c1;
        star5.setMax(realCount);star5.setProgress(c5);
        star4.setMax(realCount);star4.setProgress(c4);
        star3.setMax(realCount);star3.setProgress(c3);
        star2.setMax(realCount);star2.setProgress(c2);
        star1.setMax(realCount);star1.setProgress(c1);
        starCount.setText(movieDetailItem.getRatings_count()+"人评分");





    }

        /**
         * 获取前两个影片类型
         */
        private void getGenres () {
            if (movieDetailItem.getGenres().size() >= 2) {
                genre1 = movieDetailItem.getGenres().get(0);
                genre2 = " " + movieDetailItem.getGenres().get(1);
            } else {
                genre1 = movieDetailItem.getGenres().get(0);
                genre2 = "";
            }
            genres = genre1 + genre2;
        }
        /**
         * 转换合理的评分以星级显示
         */
        public double fitRating ( double rating) {
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
