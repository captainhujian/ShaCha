package com.example.luowenliang.idouban.moviedetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luowenliang.idouban.ParallaxSwipeBackActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.adapter.CastRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.CommentRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.StageRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.entity.CastInfo;
import com.example.luowenliang.idouban.moviedetail.entity.CommentInfo;
import com.example.luowenliang.idouban.moviedetail.entity.MovieDetailItem;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;
import com.hedgehog.ratingbar.RatingBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailActivity extends ParallaxSwipeBackActivity {
    private static final String TAG = "详情";
    public static final String PICTURE_PLACE_HOLDER="http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87%E5%8D%A0%E4%BD%8D%E5%9B%BE&step_word=&hs=2&pn=3&spn=0&di=660&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=319223871%2C3949806150&os=4290579727%2C470734890&simid=0%2C0&adpicid=0&lpn=0&ln=769&fr=&fmq=1559270247510_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2F6120491.s21i.faiusr.com%2F2%2FABUIABACGAAg0725rAUoiLv9qAQwrAI4rAI.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B15g2uwg2x7_z%26e3Bv54AzdH3Fr51_z%26e3B3fr%3Ft1%3D0b&gsm=0&rpstart=0&rpnum=0&islist=&querylist=&force=undefined";
    private String id;
    private CastInfo castInfo;
    private StagePhotoInfo stagePhotoInfo;
    private CommentInfo commentInfo;
    private ImageView image;
    private TextView title,originTitleYear,mesage,rating,starCount,summary;
    private RatingBar ratingBar;
    private ProgressBar star5,star4,star3,star2,star1;
    private List<CastInfo> castInfos=new ArrayList<>();
    private List<StagePhotoInfo>stagePhotoInfos=new ArrayList<>();
    private List<CommentInfo>commentInfos=new ArrayList<>();
    private SetDetailData setDetailData;
    private RecyclerView castRecyclerView,stageRecyclerView,commentRecyclerView;
    private CastRecyclerViewAdapter castRecyclerViewAdapter;
    private StageRecyclerViewAdapter stageRecyclerViewAdapter;
    private CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        super.onCreate(savedInstanceState);
        /**
         * 状态栏透明
         */
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_movie_detail);


        //界面和控件初始化
        initView();
        showHideFullSummary();
        ratingBar.setmClickable(false);
        //summary.setMovementMethod(ScrollingMovementMethod.getInstance());
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

        //接收网络请求数据
        Intent intent =getIntent();
        id = intent.getStringExtra("id");
        Log.d(TAG, "接收id："+id);
        initMovieDetailData();
    }

    /**
     * 绑定控件
     */
    private void initView() {
        image=findViewById(R.id.movie_image);
        title=findViewById(R.id.movie_title);
        originTitleYear=findViewById(R.id.movie_origin_title_year);
        mesage=findViewById(R.id.detail_message);
        rating=findViewById(R.id.rating_number);
        ratingBar=findViewById(R.id.movie_detail_rating_bar);
        star5=findViewById(R.id.progress_bar_h5);
        star4=findViewById(R.id.progress_bar_h4);
        star3=findViewById(R.id.progress_bar_h3);
        star2=findViewById(R.id.progress_bar_h2);
        star1=findViewById(R.id.progress_bar_h1);
        starCount=findViewById(R.id.rating_count);
        summary=findViewById(R.id.summary);
        castRecyclerView=findViewById(R.id.cast_recycler_view);
        stageRecyclerView=findViewById(R.id.stage_photo_recycler_view);
        commentRecyclerView=findViewById(R.id.comment_recycler_view);
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
                        showRecyclerViewData();
                        setStageRecyclerViewOnclickItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(MovieDetailItem movieDetailItem) {
                        Log.d("星星", "starCount: "+movieDetailItem.getRatings_count());
                        Log.d(TAG, "onNext: ");
                        setDetailData=new SetDetailData(movieDetailItem);
                        setDetailData.setMovieMessage(image,title,originTitleYear,mesage,ratingBar,rating,
                                star5,star4,star3,star2,star1,starCount,summary);
                        setCastData(movieDetailItem);
                        setStagePhoto(movieDetailItem);
                        setCommentData(movieDetailItem);
                        }

                });
    }

    /**
     * 数据接入RecyclerView
     */
    private void showRecyclerViewData() {
        castRecyclerViewAdapter=new CastRecyclerViewAdapter(castInfos);
        castRecyclerView.setAdapter(castRecyclerViewAdapter);
        stageRecyclerViewAdapter=new StageRecyclerViewAdapter(stagePhotoInfos);
        stageRecyclerView.setAdapter(stageRecyclerViewAdapter);
        commentRecyclerViewAdapter=new CommentRecyclerViewAdapter(commentInfos);
        commentRecyclerView.setAdapter(commentRecyclerViewAdapter);
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    /**
     *获取卡司信息
     * @param movieDetailItem
     */
    private void setCastData(MovieDetailItem movieDetailItem) {
        /**获取导演信息*/
        //防止有的图片为空导致recyclerView不显示，这里设置占位图
        String directorPicture = null;
        if(movieDetailItem.getDirectors().get(0).getAvatars()==null){
            directorPicture=PICTURE_PLACE_HOLDER;
        }
        else{
            directorPicture=movieDetailItem.getDirectors().get(0).getAvatars().getLarge();
        }
        Log.d(TAG, "directorpicture: "+directorPicture);
        String directorName=movieDetailItem.getDirectors().get(0).getName();
        String directorenName=movieDetailItem.getDirectors().get(0).getName_en();
        castInfo=new CastInfo(directorPicture,directorName,directorenName);
        castInfos.add(castInfo);

        /**获取演员信息*/
        for(int i=0;i<movieDetailItem.getCasts().size();i++){
            //防止有的图片为空导致recyclerView不显示，这里设置占位图
            String castPicture =null ;
            if(movieDetailItem.getCasts().get(i).getAvatars()==null){
                castPicture=PICTURE_PLACE_HOLDER;
            }
            else{
                castPicture=movieDetailItem.getCasts().get(i).getAvatars().getLarge();
            }
            Log.d(TAG, "castpicture: "+castPicture);
           String castName = movieDetailItem.getCasts().get(i).getName();
           String enName = movieDetailItem.getCasts().get(i).getName_en();
            castInfo=new CastInfo(castPicture,castName,enName);
            castInfos.add(castInfo);
        }
    }
    /**
     * 获取剧照
     */
    private void setStagePhoto(MovieDetailItem movieDetailItem) {
        for (int j= 0;j<movieDetailItem.getPhotos().size();j++){
            Log.d(TAG, "Photo: "+movieDetailItem.getPhotos().get(j).getImage());
            //防止有的图片为空导致recyclerView不显示，这里设置占位图
            String stagePhoto =null ;
            if(movieDetailItem.getPhotos().get(j).getImage()==null){
                stagePhoto=PICTURE_PLACE_HOLDER;
            }
            else{
                stagePhoto=movieDetailItem.getPhotos().get(j).getImage();
            }
            stagePhotoInfo=new StagePhotoInfo(stagePhoto);
            stagePhotoInfos.add(stagePhotoInfo);
        }

    }

    /**
     * 获取短评
     */
    private void setCommentData(MovieDetailItem movieDetailItem) {
        for(int k=0 ;k<movieDetailItem.getPopular_comments().size();k++){
            //防止有的图片为空导致recyclerView不显示，这里设置占位图
            String commenterPisture =null ;
            if(movieDetailItem.getPopular_comments().get(k).getAuthor().getAvatar()==null){
                commenterPisture=PICTURE_PLACE_HOLDER;
            }
            else{
                commenterPisture=movieDetailItem.getPopular_comments().get(k).getAuthor().getAvatar();
            }
            String commenterName=movieDetailItem.getPopular_comments().get(k).getAuthor().getName();
            double commenterRating= movieDetailItem.getPopular_comments().get(k).getRating().getValue();
            String commentTime=movieDetailItem.getPopular_comments().get(k).getCreated_at();
            String comment=movieDetailItem.getPopular_comments().get(k).getContent();
            int usefulCount=movieDetailItem.getPopular_comments().get(k).getUseful_count();
            commentInfo=new CommentInfo(commenterPisture,commenterName,commenterRating,commentTime,comment,usefulCount);
            commentInfos.add(commentInfo);
        }
    }
    /**简介点击展开收缩全文*/
    private void showHideFullSummary(){
        summary.setOnClickListener(new View.OnClickListener() {
            boolean flag=true;
            @Override
            public void onClick(View view) {
                if(flag){
                    flag = false;
                    summary.setEllipsize(null); // 展开
                    summary.setSingleLine(flag);
                    Log.d(TAG, "展开啦！");
                }else{
                    flag = true;
                    summary.setLines(4);
                    summary.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    Log.d(TAG, "收缩啦！");
                }
            }
        });
    }
    /**剧照的点击事件*/
    private void setStageRecyclerViewOnclickItem(){
        stageRecyclerViewAdapter.setOnPhotoListener(new StageRecyclerViewAdapter.OnPhotoClickListener() {
            @Override
            public void onClick(List<StagePhotoInfo> stagePhotoInfos,int i) {
                //Toast.makeText(MyApplication.getContext(),stagePhotoInfo.getStagePhoto(),Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(MovieDetailActivity.this,ViewPagerActivity.class);
              Bundle bundle = new Bundle();
              bundle.putSerializable("stagePhotoInfos", (Serializable) stagePhotoInfos);
                intent.putExtras(bundle);
                intent.putExtra("currentPosition",i);
              startActivity(intent);
            }
        });
    }

}
