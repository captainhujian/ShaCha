package com.example.luowenliang.idouban.moviedetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.moviedetail.adapter.CastRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.CommentRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.StageRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.entity.MovieDetailItem;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = "详情";
    private String id;
    private TextView introduction;
    private RecyclerView castRecyclerView;
    private CastRecyclerViewAdapter castRecyclerViewAdapter;
    private RecyclerView stageRecyclerView;
    private StageRecyclerViewAdapter stageRecyclerViewAdapter;
    private RecyclerView commentRecyclerView;
    private CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_movie_detail);
        introduction=findViewById(R.id.introduction);
        castRecyclerView=findViewById(R.id.cast_recycler_view);
        stageRecyclerView=findViewById(R.id.stage_photo_recycler_view);
        commentRecyclerView=findViewById(R.id.comment_recycler_view);
        introduction.setMovementMethod(ScrollingMovementMethod.getInstance());
        castRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        stageRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //评论不可滑动recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        commentRecyclerView.setLayoutManager(layoutManager);

        castRecyclerViewAdapter=new CastRecyclerViewAdapter();
        castRecyclerView.setAdapter(castRecyclerViewAdapter);
        stageRecyclerViewAdapter=new StageRecyclerViewAdapter();
        stageRecyclerView.setAdapter(stageRecyclerViewAdapter);
        commentRecyclerViewAdapter=new CommentRecyclerViewAdapter();
        commentRecyclerView.setAdapter(commentRecyclerViewAdapter);
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        Intent intent =getIntent();
        id = intent.getStringExtra("id");
        Log.d(TAG, "接收id："+id);
        initMovieDetailData();
    }

    private rx.Observable<MovieDetailItem> requsetMovieDetailData() {
        Log.d(TAG, "requsetDetailData: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.douban.uieee.com/v2/movie/subject/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        MovieDetailService movieDetailService = retrofit.create(MovieDetailService.class);
//        rx.Observable<MovieItem> top250Movie = movieService.getMovie();//"0b2bdeda43b5688921839c8ecb20399b"
        return movieDetailService.getMovieDetail(id);
    }
    private void initMovieDetailData() {
        Log.d(TAG, "initMovieTop250Data: ");
        requsetMovieDetailData()
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<MovieDetailItem>() {
                    @Override
                    public void onCompleted() {
//                        progressBar.setVisibility(View.GONE);
//                        initData();
//                        MovieRecyclerViewOnClickItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(MovieDetailItem movieDetailItem) {
                        Log.d(TAG, "onNext: ");
                       // progressBar.setVisibility(View.VISIBLE);
                            String title = movieDetailItem.getTitle();
                            Log.d(TAG, " 影片名：" + title);
//                            setMovieData(image, title, original_title, cast, rating, genres,
//                                    durations, year, director,id);
                        }
                       // Log.d(TAG, "movieList: " + movieList.size());

                });
    }
}
