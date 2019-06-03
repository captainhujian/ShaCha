package com.example.luowenliang.idouban.moviedetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.luowenliang.idouban.BaseActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.castdetail.CastDetailActivity;
import com.example.luowenliang.idouban.moviedetail.adapter.CastRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.CommentRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.StageRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.entity.CastInfo;
import com.example.luowenliang.idouban.moviedetail.entity.CommentInfo;
import com.example.luowenliang.idouban.moviedetail.entity.MovieDetailItem;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;
import com.example.luowenliang.idouban.moviedetail.service.MovieDetailService;
import com.example.luowenliang.idouban.moviedetail.utils.SetMovieDetailData;
import com.example.luowenliang.idouban.photoViewer.ViewPagerActivity;
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

public class MovieDetailActivity extends BaseActivity {
    private static final String TAG = "详情";
    public static final String PICTURE_PLACE_HOLDER="http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87%E5%8D%A0%E4%BD%8D%E5%9B%BE&step_word=&hs=2&pn=3&spn=0&di=660&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=319223871%2C3949806150&os=4290579727%2C470734890&simid=0%2C0&adpicid=0&lpn=0&ln=769&fr=&fmq=1559270247510_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2F6120491.s21i.faiusr.com%2F2%2FABUIABACGAAg0725rAUoiLv9qAQwrAI4rAI.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B15g2uwg2x7_z%26e3Bv54AzdH3Fr51_z%26e3B3fr%3Ft1%3D0b&gsm=0&rpstart=0&rpnum=0&islist=&querylist=&force=undefined";
    private String id;
    private MovieDetailItem localMovieDetailItem;
    private CastInfo castInfo;
    private StagePhotoInfo stagePhotoInfo;
    private CommentInfo commentInfo;
    private ImageView image;
    private TextView detailTitleText,title,originTitleYear,mesage,rating,noneRating,starCount,summary;
    private RatingBar ratingBar;
    private ProgressBar star5,star4,star3,star2,star1;
    private List<CastInfo> castInfos=new ArrayList<>();
    private List<StagePhotoInfo>stagePhotoInfos=new ArrayList<>();
    private List<CommentInfo>commentInfos=new ArrayList<>();
    private SetMovieDetailData setDetailData;
    private RecyclerView castRecyclerView,stageRecyclerView,commentRecyclerView;
    private CastRecyclerViewAdapter castRecyclerViewAdapter;
    private StageRecyclerViewAdapter stageRecyclerViewAdapter;
    private CommentRecyclerViewAdapter commentRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        setSlideable(isActivitySlideBack());
        setContentView(LayoutInflater.from(this).inflate(R.layout.activity_movie_detail,null,false));

        //界面和控件初始化
        initView();

        //接收影片id，接着进行影片详情信息的网络请求
        Intent intent =getIntent();
        id = intent.getStringExtra("id");
        Log.d(TAG, "接收id："+id);
        initMovieDetailData();
    }

    /**
     * 是否滑动退出
     * @return
     */
    @Override
    public boolean isActivitySlideBack() {
        return true;
    }

    /**
     * 界面初始化
     */
    private void initView() {
        detailTitleText=findViewById(R.id.detail_title_text);
        image=findViewById(R.id.movie_image);
        title=findViewById(R.id.movie_title);
        originTitleYear=findViewById(R.id.movie_origin_title_year);
        mesage=findViewById(R.id.detail_message);
        rating=findViewById(R.id.rating_number);
        noneRating=findViewById(R.id.none_rating);
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
        showHideFullSummary();
    }

    //网络请求
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
    //RxJava数据线程切换
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
                        setPosterOnclickItem();
                        setCastOnclickItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(MovieDetailItem movieDetailItem) {
                        //为获取海报图片对movieDetailItem取实例
                        localMovieDetailItem=new MovieDetailItem();
                        localMovieDetailItem=movieDetailItem;
                        setDetailData=new SetMovieDetailData(movieDetailItem);
                        setDetailData.setMovieMessage(detailTitleText,image,title,originTitleYear,mesage,ratingBar,rating,
                                noneRating,star5,star4,star3,star2,star1,starCount,summary);
                        setCastData(movieDetailItem);
                        setStagePhoto(movieDetailItem);
                        setCommentData(movieDetailItem);
                        Log.d(TAG, "onNext: "+image.toString()+title.toString());
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
        for(int i=0;i<movieDetailItem.getDirectors().size();i++){
        if(movieDetailItem.getDirectors().get(i).getAvatars()==null){
            directorPicture=PICTURE_PLACE_HOLDER;
        }
        else{
                directorPicture=movieDetailItem.getDirectors().get(i).getAvatars().getLarge();
                Log.d(TAG, "directorpicture: "+directorPicture);
            }
            String directorName=movieDetailItem.getDirectors().get(i).getName();
            String directorenName="导演";
            String dircetorId=movieDetailItem.getDirectors().get(i).getId();
            castInfo=new CastInfo(directorPicture,directorName,directorenName,dircetorId);
            castInfos.add(castInfo);
        }

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
           String castId=movieDetailItem.getCasts().get(i).getId();
            castInfo=new CastInfo(castPicture,castName,enName,castId);
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
              bundle.putSerializable("photo", (Serializable) stagePhotoInfos);
                intent.putExtras(bundle);
                intent.putExtra("currentPosition",i);
              startActivity(intent);
            }
        });
    }
    /**
     * 主题海报的点击事件
     */
    private void setPosterOnclickItem(){
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this,ViewPagerActivity.class);
                Bundle bundle = new Bundle();
                List<StagePhotoInfo>photos=new ArrayList<>();
                StagePhotoInfo photo=new StagePhotoInfo(localMovieDetailItem.getImages().getLarge());
                photos.add(photo);
                bundle.putSerializable("photo",(Serializable)photos);
                intent.putExtras(bundle);
                intent.putExtra("currentPosition",0);
                startActivity(intent);
            }
        });
    }
    /**
     * 卡司的点击事件
     */
    private void setCastOnclickItem(){
        castRecyclerViewAdapter.setOnItemClickListener(new CastRecyclerViewAdapter.OnCastItemClickListener() {
            @Override
            public void onClick(CastInfo castInfo) {
                String castId=castInfo.getCastId();
                //当id为空,传空值给卡司详情无法做网络请求时的备用数据
                String castName=castInfo.getCastName();
                String castEnName=castInfo.getEnName();
                String castDetailFilmPicture = localMovieDetailItem.getImages().getLarge();
                String castDetailFilmTitle=localMovieDetailItem.getTitle();
                double castDetailFilmRating=localMovieDetailItem.getRating().getAverage();
                String castDetailFilmId=localMovieDetailItem.getId();
                Intent intent =new Intent(MovieDetailActivity.this,CastDetailActivity.class);
                intent.putExtra("castId",castId);
                //传递备用数据
                intent.putExtra("castName",castName);
                intent.putExtra("castEnName",castName);
                intent.putExtra("castDetailFilmPicture",castDetailFilmPicture);
                intent.putExtra("castDetailFilmTitle",castDetailFilmTitle);
                intent.putExtra("castDetailFilmRating",castDetailFilmRating);
                intent.putExtra("castDetailFilmId",castDetailFilmId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
