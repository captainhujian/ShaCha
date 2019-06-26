package com.example.luowenliang.idouban.castdetail;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.castdetail.entity.WorksItem;
import com.example.luowenliang.idouban.castdetail.service.CastTotalWorksSercive;
import com.example.luowenliang.idouban.moviedetail.MovieDetailActivity;
import com.example.luowenliang.idouban.moviehot.HotMoviesFragment;
import com.example.luowenliang.idouban.moviehot.TotalMoviesActivity;
import com.example.luowenliang.idouban.moviehot.adapter.TotalMoviesRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviehot.entity.HotMovieInfo;
import com.example.luowenliang.idouban.moviehot.entity.HotMovieItem;
import com.example.luowenliang.idouban.moviehot.service.TotalMoviesService;
import com.example.luowenliang.idouban.moviehot.utils.ImageFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.example.luowenliang.idouban.moviedetail.MovieDetailActivity.PICTURE_PLACE_HOLDER;
import static com.example.luowenliang.idouban.moviehot.HotMoviesFragment.fitDateFormat;

public class TotalWorksActivity extends TotalMoviesActivity {
    private static final String TAG = "全部";
    private Toolbar totalMoviesTitle;
    private ImageView totalTitleBackground;
    private TextView totalExit;
    private CollapsingToolbarLayout collapsingLayout;
    private ProgressBar totalProgressBar;
    private RecyclerView totalMoviesRecyclerView;
    private TotalMoviesRecyclerViewAdapter adapter;
    private HotMoviesFragment fragment;
    private HotMovieInfo totalMovieInfo;
    private List<HotMovieInfo> totalMovieInfos = new ArrayList<>();
    private LocationManager locationManager;
    private LinearLayoutManager linearLayoutManager;
    private String id,totalTitle;
    private int lastVisibleItem,totalCount=0;
    private int start = 0;
    private static int count = 20;
    boolean isLoading = false;
    boolean isFirstImage=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(this).inflate(R.layout.activity_hotmovie_total, null, false));
        totalTitleBackground=findViewById(R.id.total_movie_toolbar_image);
        totalMoviesTitle = findViewById(R.id.total_movie_toolbar_title);
        totalExit = findViewById(R.id.total_exit);
        collapsingLayout=findViewById(R.id.movie_collapsing_toolbar);
        totalProgressBar = findViewById(R.id.total_movie_progress_bar);
        totalMoviesRecyclerView = findViewById(R.id.total_movie_recycle_view);
        totalMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        linearLayoutManager=new LinearLayoutManager(TotalWorksActivity.this, LinearLayoutManager.VERTICAL, false);
        totalMoviesRecyclerView.setLayoutManager(linearLayoutManager);
        fragment = new HotMoviesFragment();
        //adapter实例化完成，等待数据加入，不能重复new和setAdapter，否则每次都会回到第一个item
        adapter = new TotalMoviesRecyclerViewAdapter(this);
        totalMoviesRecyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        id = intent.getStringExtra("cast_id");
        totalTitle = intent.getStringExtra("cast_total_title");
        //请求作品总数
        initTotalWorkCount(id);
        exitActivity();

    }

    /**
     * 退出界面
     */
    private void exitActivity() {
        totalExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        //给recyclerView添加滑动监听
        totalMoviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
        /*
        到达底部了,如果不加!isLoading的话到达底部如果还一滑动的话就会一直进入这个方法
        就一直去做请求网络的操作,这样的用户体验肯定不好.添加一个判断,每次滑倒底只进行一次网络请求去请求数据
        当请求完成后,在把isLoading赋值为false,下次滑倒底又能进入这个方法了
         */
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !isLoading) {
                    //到达底部之后如果footView的状态不是正在加载的状态,就将 他切换成正在加载的状态
                    if (start < ((totalCount/count))) {
                        Log.e(TAG, "onScrollStateChanged: " + "进来了");
                        isLoading = true;
                        adapter.changeState(1);
                        start++;
                        Log.d(TAG, "第"+(start+1)+"次请求开始");
                        initTotalWorkData(id, totalTitle,String.valueOf(start*count));
                        Log.d(TAG, "第"+(start+1)+"次请求成功");
                    } else {
                        adapter.changeState(2);
                        Log.d(TAG, "");

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //拿到最后一个出现的item的位置
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
    /**
     * 请求电影总数
     */
    private rx.Observable<WorksItem> requestTotalWorksCount(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://douban.uieee.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        CastTotalWorksSercive castTotalWorksSercive = retrofit.create(CastTotalWorksSercive.class);
        return castTotalWorksSercive.getCastWorksCount(id);
    }
    public void initTotalWorkCount(final String castId) {
        requestTotalWorksCount()
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<WorksItem>() {
                    @Override
                    public void onCompleted() {
                        //第一次totalmovie请求
                        Log.d(TAG, "第1次请求开始");
                        if(totalCount>=20){
                            count=20;
                        }else if(totalCount>=10){
                            count=totalCount;
                        }
                        Log.d(TAG, "totalCount:"+totalCount+" start:"+count);
                        initTotalWorkData(castId, totalTitle, String.valueOf(start));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WorksItem worksItem) {
                       totalCount= worksItem.getTotal();
                    }
                });

    }


    /**
     * 请求所有的电影数据
     */
    public rx.Observable<WorksItem> requestTotalWorksData(String id,String start) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://douban.uieee.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        CastTotalWorksSercive castTotalWorksSercive = retrofit.create(CastTotalWorksSercive.class);
        return castTotalWorksSercive.getCastTotalWorks(id,start,String.valueOf(count));
    }

    public void initTotalWorkData(String id, final String title,String start) {
        final List<HotMovieInfo>updateTotalMovieList=new ArrayList<>();
        requestTotalWorksData(id,start)
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<WorksItem>() {
                    @Override
                    public void onCompleted() {
                        totalProgressBar.setVisibility(View.GONE);
                        //设置标题栏背景图(只取第一次请求的第一张图片)
                        if(isFirstImage){
                            setTotalTitleBackGround(updateTotalMovieList);
                        }
                        //showTitle();
                        initData();
                        totalMoviesTitle.setTitle((CharSequence)title);
                        adapter.setData(updateTotalMovieList);
                        adapter.notifyDataSetChanged();
                        TotalMovieRecyclerViewOnClickItem();
                        //上拉加载标志位更新
                        isLoading=false;
//                        GPSUtils.getInstance(TotalMoviesActivity.this).getLngAndLat(new GPSUtils.OnLocationResultListener() {
//                            @Override
//                            public void onLocationResult(Location location) {
//                                getCityName(location);
//                            }
//
//                            @Override
//                            public void OnLocationChange(Location location) {
//                                getCityName(location);
//                            }
//                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());

                    }

                    @Override
                    public void onNext(WorksItem worksItem) {
                        totalProgressBar.setVisibility(View.VISIBLE);
                        String totalMoviePicture;
                        double totalMovieRating;
                        for (int i = 0; i < worksItem.getWorks().size(); i++) {
                            Log.d(TAG, "item: " + worksItem);
                            if (worksItem.getWorks().get(i).getSubject().getImages().getLarge() == null) {
                                totalMoviePicture = PICTURE_PLACE_HOLDER;
                            } else {
                                totalMoviePicture = worksItem.getWorks().get(i).getSubject().getImages().getLarge();
                                Log.d(TAG, "directorpicture: " + totalMoviePicture);
                            }
                            String publicPraiseTitle = worksItem.getWorks().get(i).getSubject().getTitle();
                            if (worksItem.getWorks().get(i).getSubject().getRating() != null) {
                                totalMovieRating = worksItem.getWorks().get(i).getSubject().getRating().getAverage();
                            } else {
                                totalMovieRating = 0f;
                            }
                            String publicPraiseId = worksItem.getWorks().get(i).getSubject().getId();
                            //充当角色
                            String role,role1=null,role2=null,role3=null;
                            if (worksItem.getWorks().get(i).getRoles().size()==0) {
                                Log.d(TAG, "xxxxxxxxxxxxxx4");
                                role1 = "无";
                                role2 = "";
                                role3 = "";
                            }
                            if(worksItem.getWorks().get(i).getRoles().size() ==1){
                                role1 = worksItem.getWorks().get(i).getRoles().get(0);
                                role2 = "";
                                role3 ="";
                            }
                            if (worksItem.getWorks().get(i).getRoles().size() ==2) {
                                    Log.d(TAG, "yyyyyyyyyyyyyyyy");
                                    role1 = worksItem.getWorks().get(i).getRoles().get(0);
                                    role2 = "、" + worksItem.getWorks().get(i).getRoles().get(1);
                                    role3 ="";
                                }
                                if (worksItem.getWorks().get(i).getRoles().size() == 3) {
                                    Log.d(TAG, "zzzzzzzzzzzzzz");
                                    role1 = worksItem.getWorks().get(i).getRoles().get(0);
                                    role2 = "、"+worksItem.getWorks().get(i).getRoles().get(1);
                                    role3="、"+worksItem.getWorks().get(i).getRoles().get(2);
                                }
                                role=role1+role2+role3;
                            //星级评分
                            double fitFilmRate = fragment.fitRating(totalMovieRating);
                            Log.d("分数", "合理的分数: " + fitFilmRate);
                            String totalMoviesMessage = setTotalMoviesMesssage(worksItem, i);
                            totalMovieInfo = new HotMovieInfo(totalMoviePicture, publicPraiseTitle, totalMovieRating, publicPraiseId, fitFilmRate, totalMoviesMessage,"",role);
                            updateTotalMovieList.add(totalMovieInfo);
                        }
                    }

                });
    }


    private String setTotalMoviesMesssage(WorksItem worksItem, int i) {
        String year, genre1=null, genre2=null, director, cast1 = null, cast2 = null;
        //年份
        if (worksItem.getWorks().get(i).getSubject().getYear() != null) {
            year = worksItem.getWorks().get(i).getSubject().getYear() + "/";
            Log.d(TAG, "-4 ");
        } else {
            Log.d(TAG, "-3");
            year = "";
        }
        //类型
        if (worksItem.getWorks().get(i).getSubject().getGenres() == null) {
            Log.d(TAG, "-2");
            genre1 = "";
            genre2 = "";
        } else {
            if (worksItem.getWorks().get(i).getSubject().getGenres().size() > 1) {
                genre1 = worksItem.getWorks().get(i).getSubject().getGenres().get(0);
                genre2 = " " + worksItem.getWorks().get(i).getSubject().getGenres().get(1) + "/";
                Log.d(TAG, "-1");
            } else if(worksItem.getWorks().get(i).getSubject().getGenres().size()==1){
                genre1 = worksItem.getWorks().get(i).getSubject().getGenres().get(0);
                genre2 = "/";
                Log.d(TAG, "00000000000000");
            }
        }
        //导演
        Log.d(TAG, "");
        if (worksItem.getWorks().get(i).getSubject().getDirectors().size()== 0) {
            director = "";
            Log.d(TAG, "1111111111111111");
        } else {
            Log.d(TAG, "1.5");
            if(worksItem.getWorks().get(i).getSubject().getDirectors().get(0).equals("")){
                Log.d(TAG, "2222222222222");
                director="";
            }else {
                Log.d(TAG, "333333333333333");
                director = worksItem.getWorks().get(i).getSubject().getDirectors().get(0).getName() + "/";
            }
        }
        //卡司
        if (worksItem.getWorks().get(i).getSubject().getCasts().size()==0) {
            Log.d(TAG, "444444444444444444");
            cast1 = "";
            cast2 = "";
        } else {
            if (worksItem.getWorks().get(i).getSubject().getCasts().size() > 1) {
                Log.d(TAG, "555555555555555555555");
                cast1 = worksItem.getWorks().get(i).getSubject().getCasts().get(0).getName();
                cast2 = " " + worksItem.getWorks().get(i).getSubject().getCasts().get(1).getName();
            } else if (worksItem.getWorks().get(i).getSubject().getCasts().size() == 1) {
                Log.d(TAG, "66666666666666666666");
                cast1 = worksItem.getWorks().get(i).getSubject().getCasts().get(0).getName();
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
                if (movieId != null) {
                    Intent intent = new Intent(TotalWorksActivity.this, MovieDetailActivity.class);
                    intent.putExtra("id", movieId);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 设置全部电影标题栏背景图
     */
    private void setTotalTitleBackGround(List<HotMovieInfo>updateTotalMovieList) {
        //获取第一个电影海报作为标题封面
        //取出bitmap图片资源
        Glide.with(TotalWorksActivity.this)
                .asBitmap()
                .load(updateTotalMovieList.get(0).getHotMoviePicture())
                .into(new SimpleTarget<Bitmap>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //图片高斯模糊
                        Bitmap gaussBitmap= ImageFilter.blufBitmap(TotalWorksActivity.this,resource,5f);
                        //imageview变暗
                        totalTitleBackground.setColorFilter(Color.parseColor("#cc141414"));
                        totalTitleBackground.setImageBitmap(resource);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                int color;
                                Palette.Swatch swatch = palette.getDarkMutedSwatch();
                                if (swatch != null) {
                                    color=swatch.getRgb();
                                }else {
                                    color=Color.parseColor("#CD853F");
                                }
                                collapsingLayout.setContentScrimColor(color);
                            }
                        });
                    }
                });
        isFirstImage=false;
    }

    @Override
    public boolean isActivitySlideBack() {
        return true;
    }
}
