package com.example.luowenliang.idouban.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luowenliang.idouban.BaseActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.VedioViewer.VideoViewerActivity;
import com.example.luowenliang.idouban.castdetail.CastDetailActivity;
import com.example.luowenliang.idouban.moviedetail.adapter.CastRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.CommentRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.MovieResourceRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.ResourceIconRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.adapter.StageRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.entity.CastInfo;
import com.example.luowenliang.idouban.moviedetail.entity.CommentInfo;
import com.example.luowenliang.idouban.moviedetail.entity.MovieDetailItem;
import com.example.luowenliang.idouban.moviedetail.entity.MovieResourceInfo;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;
import com.example.luowenliang.idouban.moviedetail.service.MovieDetailService;
import com.example.luowenliang.idouban.moviedetail.utils.SetMovieDetailData;
import com.example.luowenliang.idouban.photoViewer.ViewPagerActivity;
import com.flipboard.bottomsheet.BottomSheetLayout;
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

import static java.util.Arrays.asList;

public class MovieDetailActivity extends BaseActivity {
    private static final String TAG = "详情";
    public static final String PICTURE_PLACE_HOLDER="http://6120491.s21i.faiusr.com/2/ABUIABACGAAg0725rAUoiLv9qAQwrAI4rAI.jpg";
    private List<String>colorList=new ArrayList<>();
    private String id;
    private MovieDetailItem localMovieDetailItem;
    private CastInfo castInfo;
    private CommentInfo commentInfo;
    private MovieResourceInfo movieResourceInfo;
    private Toolbar detailToolbar;
    private ImageView image;
    private TextView movieDetailExit,detailTitleText,title,originTitleYear,mesage,rating,noneRating,starCount,summary,stagePhotoTitle;
    private CardView stagePhotoCardView;
    private RatingBar ratingBar;
    private ProgressBar star5,star4,star3,star2,star1;
    private RelativeLayout watchMovie;
    private List<MovieResourceInfo>movieResourceInfos=new ArrayList<>();
    private List<CastInfo> castInfos=new ArrayList<>();
    private List<StagePhotoInfo>stagePhotoInfos=new ArrayList<>();
    private List<CommentInfo>commentInfos=new ArrayList<>();
    private SetMovieDetailData setDetailData;
    private RecyclerView resourceIconRecyclerView,castRecyclerView,stageRecyclerView,commentRecyclerView;
    private ResourceIconRecyclerViewAdapter resourceIconRecyclerViewAdapter;
    private CastRecyclerViewAdapter castRecyclerViewAdapter;
    private StageRecyclerViewAdapter stageRecyclerViewAdapter;
    private CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;//弹出框的子布局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSlideable(isActivitySlideBack());
        View view=LayoutInflater.from(this).inflate(R.layout.activity_movie_detail,null,false);
        setContentView(view);
        //界面和控件初始化
        initView();
        //动态设置背景色
        colorList = asList("#42426F","#5C4033","#4A766E","#42426F","#4A708B","#993333","#8B4789","#473C8B","#8B7D7B","#426F42","#CD919E",
                "#8B7355","#668B8B","#CD853F","#2F2F4F","#4A766E","#104E8B","#27408B","#996699","#8B8386","#339966");
        String colorString=colorList.get((int)(0+Math.random()*(colorList.size()-0)));
        view.setBackgroundColor(Color.parseColor(colorString));
        detailToolbar.setBackgroundColor(Color.parseColor(colorString));
        //接收影片id，接着进行影片详情信息的网络请求
        Intent intent =getIntent();
        id = intent.getStringExtra("id");
        Log.d(TAG, "接收id："+id);
        Log.d("搜索", "接收id: "+id);
        initMovieDetailData();
        showHideFullSummary();
        //按键退出
        exitMovieDetailActivity();

    }

    /**
     * 按键退出
     */
    private void exitMovieDetailActivity() {
        movieDetailExit.setText("×");
        movieDetailExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        movieDetailExit=findViewById(R.id.movie_detail_exit);
        detailToolbar=findViewById(R.id.detail_title);
        detailTitleText=findViewById(R.id.detail_title_text);
        image=findViewById(R.id.movie_image);
        title=findViewById(R.id.movie_title);
        originTitleYear=findViewById(R.id.movie_origin_title_year);
        mesage=findViewById(R.id.detail_message);
        rating=findViewById(R.id.rating_number);
        noneRating=findViewById(R.id.none_rating);
        stagePhotoTitle=findViewById(R.id.stage_photo);
        stagePhotoCardView=findViewById(R.id.stage_photo_card_view);
        ratingBar=findViewById(R.id.movie_detail_rating_bar);
        star5=findViewById(R.id.progress_bar_h5);
        star4=findViewById(R.id.progress_bar_h4);
        star3=findViewById(R.id.progress_bar_h3);
        star2=findViewById(R.id.progress_bar_h2);
        star1=findViewById(R.id.progress_bar_h1);
        starCount=findViewById(R.id.rating_count);
        watchMovie=findViewById(R.id.watch_movie);
        summary=findViewById(R.id.summary);
        resourceIconRecyclerView=findViewById(R.id.resource_icon_recycler_view);
        castRecyclerView=findViewById(R.id.cast_recycler_view);
        stageRecyclerView=findViewById(R.id.stage_photo_recycler_view);
        commentRecyclerView=findViewById(R.id.comment_recycler_view);
        bottomSheetLayout=findViewById(R.id.bottomSheetLayout);
        ratingBar.setmClickable(false);
        //summary.setMovementMethod(ScrollingMovementMethod.getInstance());
        resourceIconRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
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

    }

    /**
     * 唤醒底部抽屉
     */
    private void wakeBottomSheet() {
        resourceIconRecyclerViewAdapter.setOnResourceIconClickListener(new ResourceIconRecyclerViewAdapter.OnResourceIconClickListener() {
            @Override
            public void onClick() {
                bottomSheet= showBottomSheetView();
                //弹出布局
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        });
    }

    /**
     * 从底部弹出的子布局,设置点击事件
     * @return
     */
    private View showBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet, (ViewGroup) getWindow().getDecorView(), false);
        //在子布局中绑定控件一定要在子布局的view中进行，不然会默认主布局报空指针
        TextView title =view.findViewById(R.id.resource_title);
        RecyclerView movieResourceRecyclerView=view.findViewById(R.id.movie_resource_recycler_view);
        movieResourceRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        MovieResourceRecyclerViewAdapter adapter=new MovieResourceRecyclerViewAdapter(movieResourceInfos);
        movieResourceRecyclerView.setAdapter(adapter);
        //布局item点击事件,跳转网页观看全片
        adapter.setOnMovieResourceClickListener
                (new MovieResourceRecyclerViewAdapter.OnMovieResourceClickListener() {
                    @Override
                    public void onClick(String resourceUrl) {
                        if(resourceUrl!=null){
                            openUrlWithBrowser(MovieDetailActivity.this,resourceUrl);
                        }
                    }
                });
        return view;
    }
    /**
     * 浏览器打开url
     *
     * @param context
     * @param url
     * @return
     */
    public static void openUrlWithBrowser(Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络请求详情界面数据
     */
    private void initMovieDetailData() {
        //rxjava线程切换
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
                        //唤醒电影资源抽屉
                        wakeBottomSheet();
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
                        setMovieResource(movieDetailItem);
                        setCastData(movieDetailItem);
                        setStagePhoto(movieDetailItem);
                        setCommentData(movieDetailItem);
                        Log.d(TAG, "onNext: "+image.toString()+title.toString());
                        }

                });
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

    /**
     * 获取电影播放源图标
     * @param movieDetailItem
     */
    private void setMovieResource(MovieDetailItem movieDetailItem) {
        String resourcePicture,resourceName,resourceUrl;
        boolean needPay;
       if( movieDetailItem.getVideos().size()!=0){
           Log.d("播放", "video:"+movieDetailItem.getVideos().size());
           watchMovie.setVisibility(View.VISIBLE);
           Log.d("播放", "显示");
           for(int i=0;i<movieDetailItem.getVideos().size();i++){
               resourcePicture= movieDetailItem.getVideos().get(i).getSource().getPic();
               resourceName=movieDetailItem.getVideos().get(i).getSource().getName();
               resourceUrl=movieDetailItem.getVideos().get(i).getSample_link();
               needPay=movieDetailItem.getVideos().get(i).isNeed_pay();


               movieResourceInfo=new MovieResourceInfo(resourcePicture,resourceName,needPay,resourceUrl);
               movieResourceInfos.add(movieResourceInfo);
           }
       }else {
           watchMovie.setVisibility(View.GONE);
           Log.d("播放", "隐藏");
       }
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
            directorPicture="";
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
                castPicture="";
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
     * 获取预告片、主创特辑、片段、剧照
     */
    private void setStagePhoto(MovieDetailItem movieDetailItem) {
        //获取预告片
        if (movieDetailItem.getTrailers().size() != 0) {
            String videoPicture = movieDetailItem.getTrailers().get(0).getMedium();
            String videoUrl = movieDetailItem.getTrailers().get(0).getResource_url();
            String videoTitle = movieDetailItem.getTrailers().get(0).getTitle();
            StagePhotoInfo previewInfo = new StagePhotoInfo(videoPicture, videoUrl, videoTitle,1);
            stagePhotoInfos.add(previewInfo);
        }
        //获取主创特辑
        if (movieDetailItem.getBloopers().size()!=0) {
                String videoPicture = movieDetailItem.getBloopers().get(0).getMedium();
                String videoUrl = movieDetailItem.getBloopers().get(0).getResource_url();
                String videoTitle = movieDetailItem.getBloopers().get(0).getTitle();
                StagePhotoInfo blooerInfo = new StagePhotoInfo(videoPicture, videoUrl, videoTitle,2);
                stagePhotoInfos.add(blooerInfo);

        }
        //获取影片片段
        if (movieDetailItem.getClips().size()!=0) {
                String videoPicture = movieDetailItem.getClips().get(0).getMedium();
                String videoUrl = movieDetailItem.getClips().get(0).getResource_url();
                String videoTitle = movieDetailItem.getClips().get(0).getTitle();
                StagePhotoInfo clipInfo = new StagePhotoInfo(videoPicture, videoUrl, videoTitle,3);
                stagePhotoInfos.add(clipInfo);
        }
        //获取剧照
        for (int j = 0; j < movieDetailItem.getPhotos().size(); j++) {
            Log.d(TAG, "Photo: " + movieDetailItem.getPhotos().get(j).getImage());
            //防止有的图片为空导致recyclerView不显示，这里设置占位图
            String stagePhoto = null;
            if (movieDetailItem.getPhotos().get(j).getImage() == null) {
                stagePhoto = PICTURE_PLACE_HOLDER;
            } else {
                stagePhoto = movieDetailItem.getPhotos().get(j).getImage();
            }
            StagePhotoInfo stagePhotoInfo = new StagePhotoInfo(stagePhoto, null, null,0);
            stagePhotoInfos.add(stagePhotoInfo);
        }
        //当剧照等为空时，隐藏标题和recyclerView
        if(stagePhotoInfos.size()==0){
            stagePhotoTitle.setVisibility(View.GONE);
            stagePhotoCardView.setVisibility(View.GONE);
        }else {
            stagePhotoTitle.setVisibility(View.VISIBLE);
            stagePhotoCardView.setVisibility(View.VISIBLE);
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

    /**
     * 数据接入RecyclerView
     */
    private void showRecyclerViewData() {
        resourceIconRecyclerViewAdapter=new ResourceIconRecyclerViewAdapter(movieResourceInfos);
        resourceIconRecyclerView.setAdapter(resourceIconRecyclerViewAdapter);
        castRecyclerViewAdapter=new CastRecyclerViewAdapter(castInfos,this);
        castRecyclerView.setAdapter(castRecyclerViewAdapter);
        stageRecyclerViewAdapter=new StageRecyclerViewAdapter(stagePhotoInfos);
        stageRecyclerView.setAdapter(stageRecyclerViewAdapter);
        commentRecyclerViewAdapter=new CommentRecyclerViewAdapter(commentInfos);
        commentRecyclerView.setAdapter(commentRecyclerViewAdapter);
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
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
    /**预告片、剧照的点击事件*/
    private void setStageRecyclerViewOnclickItem(){
        stageRecyclerViewAdapter.setOnPhotoListener(new StageRecyclerViewAdapter.OnPhotoClickListener() {
            @Override
            public void onClick(List<StagePhotoInfo> stagePhotoInfos,int i) {
                //判断是视频还是图片
                if(stagePhotoInfos.get(i).getVideoUrl()==null){
                    Intent intent = new Intent(MovieDetailActivity.this,ViewPagerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("photo", (Serializable) stagePhotoInfos);
                    intent.putExtras(bundle);
                    intent.putExtra("currentPosition",i);
                    startActivity(intent);
                }else{
                    //预告片
                    String videoUrl=stagePhotoInfos.get(i).getVideoUrl();
                    String videoTitle=stagePhotoInfos.get(i).getVideoTitle();
                    Intent intent = new Intent(MovieDetailActivity.this,VideoViewerActivity.class);
                    intent.putExtra("videoUrl",videoUrl);
                    intent.putExtra("videoTitle",videoTitle);
                    Log.d("预告", "发送url："+videoUrl);
                    startActivity(intent);
                }

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
                StagePhotoInfo photo=new StagePhotoInfo(localMovieDetailItem.getImages().getLarge(),null,null,0);
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
                String castDetailPhoto=castInfo.getCastPicture();
                String castDetailFilmPicture = localMovieDetailItem.getImages().getLarge();
                String castDetailFilmTitle=localMovieDetailItem.getTitle();
                double castDetailFilmRating=localMovieDetailItem.getRating().getAverage();
                String castDetailFilmId=localMovieDetailItem.getId();
                Intent intent =new Intent(MovieDetailActivity.this,CastDetailActivity.class);
                intent.putExtra("castId",castId);
                //传递备用数据
                intent.putExtra("castName",castName);
                intent.putExtra("castEnName",castName);
                intent.putExtra("castDetailPhoto",castDetailPhoto);
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
