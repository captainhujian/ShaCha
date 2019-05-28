package com.example.luowenliang.idouban;

import android.content.res.Configuration;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hedgehog.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MoviesFragment extends Fragment {
    private static final String TAG = "豆瓣";
    private RecyclerView movieRecyclerView;
    private Top250Movie top250Movie;
    private List<Top250Movie> movieList = new ArrayList<>();
    private MovieRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    public static Drawable emptyStar;
    public static Drawable halfStar;
    public static Drawable fullStar;
    private float rating;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_movies, null);
        progressBar = view.findViewById(R.id.progress_bar);
        movieRecyclerView = view.findViewById(R.id.movies_list);
        emptyStar=getResources().getDrawable(R.mipmap.star_empty);
        halfStar=getResources().getDrawable(R.mipmap.star_half);
        fullStar=getResources().getDrawable(R.mipmap.star_full);
        requsetMovieData();
        initMovieTop250Data();
        return view;
    }


    private void initData() {
        Log.d(TAG, "initData: ");
        Configuration configuration = this.getResources().getConfiguration();

        /**横竖屏布局区分*/
        int ori = configuration.orientation;
        if (ori == configuration.ORIENTATION_LANDSCAPE) {
            movieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else if (ori == configuration.ORIENTATION_PORTRAIT) {
            movieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }

        movieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MovieRecyclerViewAdapter(movieList);
        movieRecyclerView.setAdapter(adapter);
    }

    private rx.Observable<MovieItem> requsetMovieData() {
        Log.d(TAG, "requsetMovieData: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.douban.uieee.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
//        rx.Observable<MovieItem> top250Movie = movieService.getMovie();//"0b2bdeda43b5688921839c8ecb20399b"
        return movieService.getMovie();

    }

    private void initMovieTop250Data() {
        Log.d(TAG, "initMovieTop250Data: ");
        requsetMovieData()
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<MovieItem>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        initData();
                        MovieRecyclerViewOnClickItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(MovieItem movieItem) {
                        Log.d(TAG, "onNext: ");
                        progressBar.setVisibility(View.VISIBLE);
                        for (int i = 0; i < movieItem.getSubjects().size(); i++) {
                            String image = movieItem.getSubjects().get(i).getImages().getSmall();
                            String title = movieItem.getSubjects().get(i).getTitle();
                            String original_title = movieItem.getSubjects().get(i).getOriginal_title();
                            String cast = movieItem.getSubjects().get(i).getCasts().get(0).getName();
                            double rating = movieItem.getSubjects().get(i).getRating().getAverage();
                            String genres = movieItem.getSubjects().get(i).getGenres().get(0);
                            String durations = movieItem.getSubjects().get(i).getDurations().get(0);
                            String year = "(" + movieItem.getSubjects().get(i).getYear() + ")";
                            String director = movieItem.getSubjects().get(i).getDirectors().get(0).getName();
                            Log.d(TAG, "海报：" + image + " 影片名：" + title + " 原名：" + original_title + " 主演：" + cast + " 导演：" +director+ " 评分：" + rating
                                    + " 类型：" + genres + " 时长：" + durations + " 年份：" + year + "\n");
                            setMovieData(image, title, original_title, cast, rating, genres,
                                    durations, year, director);
                        }
                        Log.d(TAG, "movieList: " + movieList.size());

                    }

                });
    }

    private void setMovieData(String image, String title, String original_title, String cast,
                              double rating, String genres, String durations, String year, String director) {
        top250Movie = new Top250Movie(image, title, original_title, cast, rating, genres,
                durations, year, director);
        movieList.add(top250Movie);
    }

    private void MovieRecyclerViewOnClickItem() {
        adapter.setOnItemClickListener(new MovieRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(Top250Movie top250Movie) {
                Toast.makeText(getContext(), top250Movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


