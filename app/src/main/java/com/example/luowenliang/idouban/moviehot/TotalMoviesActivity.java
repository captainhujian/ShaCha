package com.example.luowenliang.idouban.moviehot;

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
import com.example.luowenliang.idouban.BaseActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.moviedetail.MovieDetailActivity;
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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.luowenliang.idouban.moviedetail.MovieDetailActivity.PICTURE_PLACE_HOLDER;
import static com.example.luowenliang.idouban.moviehot.HotMoviesFragment.fitDateFormat;

public class TotalMoviesActivity extends BaseActivity {
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
    private String totalUrl,totalTitle;
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
        linearLayoutManager=new LinearLayoutManager(TotalMoviesActivity.this, LinearLayoutManager.VERTICAL, false);
        totalMoviesRecyclerView.setLayoutManager(linearLayoutManager);
        fragment = new HotMoviesFragment();
        //adapter实例化完成，等待数据加入，不能重复new和setAdapter，否则每次都会回到第一个item
        adapter = new TotalMoviesRecyclerViewAdapter(this);
        totalMoviesRecyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        totalUrl = intent.getStringExtra("total_url");
        totalTitle = intent.getStringExtra("total_title");
        totalCount=intent.getIntExtra("total_count",0);
        //第一次totalmovie请求
        Log.d(TAG, "第1次请求开始");
        if(totalCount>=20){
            count=20;
        }else if(totalCount>=10){
            count=10;
        }else {
            count=5;
        }
        Log.d(TAG, "totalCount:"+totalCount+" start:"+count);
        initTotalMovieData(totalUrl, totalTitle, String.valueOf(start));
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
                    if (start < ((adapter.getItemCount()/count))) {
                        Log.e(TAG, "onScrollStateChanged: " + "进来了");
                        isLoading = true;
                        adapter.changeState(1);
                        start++;
                        Log.d(TAG, "第"+(start+1)+"次请求开始");
                        initTotalMovieData(totalUrl, totalTitle,String.valueOf(start*count));
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
     * 请求所有的电影数据
     */
    private rx.Observable<HotMovieItem> requestTotalMoviesData(String url,String start) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://douban.uieee.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        TotalMoviesService totalMoviesService = retrofit.create(TotalMoviesService.class);
        return totalMoviesService.getMovieTotal(url,start,String.valueOf(count));
    }

    public void initTotalMovieData(String url, final String title,String start) {
        final List<HotMovieInfo>updateTotalMovieList=new ArrayList<>();
        requestTotalMoviesData(url,start)
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<HotMovieItem>() {
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
                            //大陆上映日期
                            String totalPubDate;
                            Log.d(TAG, "上映时间："+hotMovieItem.getSubjects().get(i).getMainland_pubdate());
                            if((hotMovieItem.getSubjects().get(i).getMainland_pubdate().equals(""))||(hotMovieItem.getSubjects().get(i).getMainland_pubdate()==null)){
                                totalPubDate="时间待定";
                            }else {
                                totalPubDate = fitDateFormat(hotMovieItem.getSubjects().get(i).getMainland_pubdate());
                            }
                            String totalMoviesMessage = setTotalMoviesMesssage(hotMovieItem, i);
                            totalMovieInfo = new HotMovieInfo(totalMoviePicture, publicPraiseTitle, totalMovieRating, publicPraiseId, fitFilmRate, totalMoviesMessage,"",totalPubDate);
                            updateTotalMovieList.add(totalMovieInfo);
                        }
                    }

                });
    }


    private String setTotalMoviesMesssage(HotMovieItem hotMovieItem, int i) {
        String year, genre1, genre2, director, cast1 = null, cast2 = null;
        //年份
        if (hotMovieItem.getSubjects().get(i).getYear() != null) {
            year = hotMovieItem.getSubjects().get(i).getYear() + "/";
        } else {
            year = "";
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
        Log.d(TAG, "");
        if (hotMovieItem.getSubjects().get(i).getDirectors().size()== 0) {
            director = "";
            Log.d(TAG, "1111111111111111");
        } else {
            if(hotMovieItem.getSubjects().get(i).getDirectors().get(0).equals("")){
                Log.d(TAG, "2222222222222");
                director="";
            }else {
                Log.d(TAG, "333333333333333");
                director = hotMovieItem.getSubjects().get(i).getDirectors().get(0).getName() + "/";
            }
        }
        //卡司
        if (hotMovieItem.getSubjects().get(i).getCasts().size()==0) {
            Log.d(TAG, "444444444444444444");
            cast1 = "";
            cast2 = "";
        } else {
            if (hotMovieItem.getSubjects().get(i).getCasts().size() > 1) {
                Log.d(TAG, "555555555555555555555");
                cast1 = hotMovieItem.getSubjects().get(i).getCasts().get(0).getName();
                cast2 = " " + hotMovieItem.getSubjects().get(i).getCasts().get(1).getName();
            } else if (hotMovieItem.getSubjects().get(i).getCasts().size() == 1) {
                Log.d(TAG, "66666666666666666666");
                cast1 = hotMovieItem.getSubjects().get(i).getCasts().get(0).getName();
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
                    Intent intent = new Intent(TotalMoviesActivity.this, MovieDetailActivity.class);
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
            Glide.with(TotalMoviesActivity.this)
                    .asBitmap()
                    .load(updateTotalMovieList.get(0).getHotMoviePicture())
                    .into(new SimpleTarget<Bitmap>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            //图片高斯模糊
                            Bitmap gaussBitmap=ImageFilter.blufBitmap(TotalMoviesActivity.this,resource,5f);
                            //imageview变暗
                            totalTitleBackground.setColorFilter(Color.parseColor("#bb141414"));
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

    /**
     * 根据经纬度获得城市名
     */
    private void getCityName(Location location){
        String latLongString;
        double lat=0;
        double lng=0;
        TextView myLocationText;
        //myLocationText = findViewById(R.id.location_name);(使用时需打开)
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            latLongString = "纬度:" + lat + "\n经度:" + lng;
        } else {
            latLongString = "无法获取地理信息";
        }
        //android原生api有些设备会出现不支持的情况，所以使用网络请求地址
        List<Address> addList = null;
        Geocoder ge = new Geocoder(this);
        try {
            addList = ge.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, "error:"+e.toString());
            e.printStackTrace();
        }
        if(addList!=null && addList.size()>0){
            for(int i=0; i<addList.size(); i++){
                Address ad = addList.get(i);
                latLongString = ad.getLocality();
                Log.d(TAG, "开始");
                Log.d(TAG, "getCityName: "+ad.getCountryName() + " " + ad.getLocality());
                Log.d(TAG, "结束");
            }
        }
       // myLocationText.setText(latLongString);（使用时需打开）
    }

    @Override
    public boolean isActivitySlideBack() {
        return true;
    }
}
