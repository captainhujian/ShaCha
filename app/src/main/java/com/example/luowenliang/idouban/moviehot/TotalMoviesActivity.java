package com.example.luowenliang.idouban.moviehot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.luowenliang.idouban.BaseActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.moviedetail.MovieDetailActivity;
import com.example.luowenliang.idouban.moviehot.adapter.HotMovieRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviehot.entity.HotMovieInfo;
import com.example.luowenliang.idouban.moviehot.entity.HotMovieItem;
import com.example.luowenliang.idouban.moviehot.service.HotMovieService;
import com.example.luowenliang.idouban.moviehot.service.TotalMoviesService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.luowenliang.idouban.moviedetail.MovieDetailActivity.PICTURE_PLACE_HOLDER;

public class TotalMoviesActivity extends BaseActivity {
    private static final String TAG = "全部";
    private TextView totalMoviesTitle;
    private ProgressBar totalProgressBar;
    private RecyclerView totalMoviesRecyclerView;
    private TotalMoviesRecyclerViewAdapter adapter;
    private HotMoviesFragment fragment;
    private HotMovieInfo totalMovieInfo;
    private List<HotMovieInfo>totalMovieInfos=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(this).inflate(R.layout.activity_hotmovie_total,null,false));
        totalMoviesTitle=findViewById(R.id.total_movie_title);
        totalProgressBar=findViewById(R.id.total_movie_progress_bar);
        totalMoviesRecyclerView=findViewById(R.id.total_movie_recycle_view);
        totalMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        totalMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(TotalMoviesActivity.this, LinearLayoutManager.VERTICAL, false));
        fragment=new HotMoviesFragment();
        Intent intent =getIntent();
        String totalUrl = intent.getStringExtra("total_url");
        String totalTitle=intent.getStringExtra("total_title");
        initTotalMovieData(totalUrl,totalTitle);

    }

    /**
     * 请求所有的电影数据
     */
    private rx.Observable<HotMovieItem> requestTotalMoviesData(String url) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://douban.uieee.com/v2/movie/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            TotalMoviesService totalMoviesService = retrofit.create(TotalMoviesService.class);
            return totalMoviesService.getMovieTotal(url);
        }

        public void initTotalMovieData(String url, final String title) {
            requestTotalMoviesData(url)
                    .subscribeOn(Schedulers.io())//io加载数据
                    .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                    .subscribe(new Subscriber<HotMovieItem>() {
                        @Override
                        public void onCompleted() {
                           totalProgressBar.setVisibility(View.GONE);
                            //showTitle();
                            adapter=new TotalMoviesRecyclerViewAdapter(totalMovieInfos);
                            totalMoviesRecyclerView.setAdapter(adapter);
                            totalMoviesTitle.setText(title);
                            TotalMovieRecyclerViewOnClickItem();

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: "+e.toString());

                        }

                        @Override
                        public void onNext(HotMovieItem hotMovieItem) {
                           totalProgressBar.setVisibility(View.VISIBLE);
                            String totalMoviePicture;
                            double totalMovieRating;
                            for (int i = 0; i < hotMovieItem.getSubjects().size(); i++) {
                                Log.d(TAG, "item: " + hotMovieItem);
                                if (hotMovieItem.getSubjects().get(i).getImages() == null) {
                                    totalMoviePicture = PICTURE_PLACE_HOLDER;
                                } else {
                                    totalMoviePicture = hotMovieItem.getSubjects().get(i).getImages().getLarge();
                                    Log.d(TAG, "directorpicture: " + totalMoviePicture);
                                }
                                String publicPraiseTitle = hotMovieItem.getSubjects().get(i).getTitle();
                                if (hotMovieItem.getSubjects().get(i).getRating() != null) {
                                    totalMovieRating = hotMovieItem.getSubjects().get(i).getRating().getAverage();
                                } else {
                                    totalMovieRating = 0f;
                                }
                                String publicPraiseId = hotMovieItem.getSubjects().get(i).getId();
                                //星级评分
                                double fitFilmRate = fragment.fitRating(totalMovieRating);
                                Log.d("分数", "合理的分数: " + fitFilmRate);
                                String totalMoviesMessage = setTotalMoviesMesssage(hotMovieItem, i);
                                totalMovieInfo = new HotMovieInfo(totalMoviePicture, publicPraiseTitle, totalMovieRating, publicPraiseId, fitFilmRate, totalMoviesMessage);
                                totalMovieInfos.add(totalMovieInfo);
                            }
                    }

                    });
        }

    private String setTotalMoviesMesssage(HotMovieItem hotMovieItem, int i) {
        String year, genre1, genre2, director,cast1 = null, cast2 = null;
        //年份
        if(hotMovieItem.getSubjects().get(i).getYear()!=null){
            year=hotMovieItem.getSubjects().get(i).getYear()+"/";
        }else {
            year="";
        }
        //类型
        if (hotMovieItem.getSubjects().get(i).getGenres() == null) {
            genre1 = "";
            genre2 = "";
        } else {
            if (hotMovieItem.getSubjects().get(i).getGenres().size() > 1) {
                genre1 = hotMovieItem.getSubjects().get(i).getGenres().get(0);
                genre2 = " " + hotMovieItem.getSubjects().get(i).getGenres().get(1) + "/";
            } else {
                genre1 = hotMovieItem.getSubjects().get(i).getGenres().get(0);
                genre2 = "/";
            }
        }
        //导演
        if(hotMovieItem.getSubjects().get(i).getDirectors()!=null){
            director=hotMovieItem.getSubjects().get(i).getDirectors().get(0).getName()+"/";
        }else {
            director="";
        }
        //卡司
        if (hotMovieItem.getSubjects().get(i).getCasts() == null) {
            cast1 = "";
            cast2 = "";
        } else {
            if (hotMovieItem.getSubjects().get(i).getCasts().size() > 1) {
                cast1 = hotMovieItem.getSubjects().get(i).getCasts().get(0).getName();
                cast2 = " " + hotMovieItem.getSubjects().get(i).getCasts().get(1).getName();
            } else if (hotMovieItem.getSubjects().get(i).getCasts().size() == 1) {
                cast1 =  hotMovieItem.getSubjects().get(i).getCasts().get(0).getName();
                cast2 = "";
            }
        }
        return year + genre1 + genre2 + director + cast1 + cast2;
    }

    /**
     * 全部电影点击事件
     */
    private void TotalMovieRecyclerViewOnClickItem() {
        adapter.setOnTotalMovieClickListener(new TotalMoviesRecyclerViewAdapter.OnTotalMovieClickListener() {
            @Override
            public void onClick(String movieId) {
                if(movieId!=null){
                    Intent intent = new Intent(TotalMoviesActivity.this,MovieDetailActivity.class);
                    intent.putExtra("id", movieId);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public boolean isActivitySlideBack() {
        return true;
    }
}
