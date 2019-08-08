package com.example.luowenliang.idouban.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luowenliang.idouban.BaseActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.vedioViewer.VideoViewerActivity;
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
import com.example.luowenliang.idouban.moviedetail.utils.GetResourcePackageName;
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


public class MovieDetailActivity extends BaseActivity {
    private static final String TAG = "详情";
    public static final String PICTURE_PLACE_HOLDER="http://6120491.s21i.faiusr.com/2/ABUIABACGAAg0725rAUoiLv9qAQwrAI4rAI.jpg";
    private String id,movieTitle;
    private double ratingNumber;
    private MovieDetailItem localMovieDetailItem;
    private CastInfo castInfo;
    private CommentInfo commentInfo;
    private MovieResourceInfo movieResourceInfo;
    private Toolbar detailToolbar;
    private RelativeLayout toolbar1;
    private ConstraintLayout toolbar2;
    private ImageView detailToolbarPic;
    private RatingBar detailToolbarRatingBar;
    private TextView movieDetailExit,detailTitleText,movieDetailExit2,detailToolbarMovieTitle,detailToolbarRating,detailToolBarNoRating;
    private NestedScrollView detailNestedScrollView;
    private ImageView image;
    private TextView title,originTitleYear,message,rating,noneRating,starCount,summary,stagePhotoTitle,totalComments,totalStagePhotos;
    private CardView stagePhotoCardView,ratingCardView;
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
    private View bottomSheet,view;
    private GetResourcePackageName getResourcePackageName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSlideable(isActivitySlideBack());
        view=LayoutInflater.from(this).inflate(R.layout.activity_movie_detail,null,false);
        setContentView(view);
        //界面和控件初始化
        initView();
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
     * 上滑更改toolbar样式
     */
    private void changeToolbarStyle() {
        //获取toolbar2样式数据
        Glide.with(MovieDetailActivity.this)
                .load(localMovieDetailItem.getImages().getLarge())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .apply(new RequestOptions().error(R.drawable.placeholder))
                .into(detailToolbarPic);
        detailToolbarMovieTitle.setText(localMovieDetailItem.getTitle());
        //星级评分
        double fitRate =setDetailData.fitRating(localMovieDetailItem.getRating().getAverage());
        Log.d("分数", "合理的分数: " + fitRate);
        //暂无评价
        if (fitRate == 0f) {
            detailToolbarRatingBar.setVisibility(View.GONE);
            detailToolbarRating.setVisibility(View.GONE);
            detailToolBarNoRating.setVisibility(View.VISIBLE);

        } else {
            detailToolbarRatingBar.setVisibility(View.VISIBLE);
            detailToolbarRating.setText(String.valueOf(localMovieDetailItem.getRating().getAverage()));
            detailToolbarRatingBar.setStarCount(5);
            detailToolbarRatingBar.setStar((float) fitRate);
        }
        //滑动监听
        detailNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY>=150){
                    toolbar1.setVisibility(View.GONE);
                    toolbar2.setVisibility(View.VISIBLE);
                }else {
                    toolbar1.setVisibility(View.VISIBLE);
                    toolbar2.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 按键退出
     */
    private void exitMovieDetailActivity() {
        movieDetailExit.setText("×");
        movieDetailExit2.setText("×");
        movieDetailExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        movieDetailExit2.setOnClickListener(new View.OnClickListener() {
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
     * 界面初始化绑定控件
     */
    private void initView() {
        toolbar1=findViewById(R.id.toobar1);
        toolbar2=findViewById(R.id.toobar2);
        movieDetailExit=findViewById(R.id.movie_detail_exit);
        movieDetailExit2=findViewById(R.id.movie_detail_exit2);
        detailToolbarMovieTitle=findViewById(R.id.detail_toolbar_movie_title);
        detailToolbarPic=findViewById(R.id.detail_toolbar_pic);
        detailToolbarRatingBar=findViewById(R.id.detail_toolbar_rating_bar);
        detailToolbarRating=findViewById(R.id.detail_toolbar_rating);
        detailToolBarNoRating=findViewById(R.id.detail_toolbar_none_rating);
        detailToolbar=findViewById(R.id.detail_title);
        detailTitleText=findViewById(R.id.detail_title_text);
        detailNestedScrollView=findViewById(R.id.detail_nested_scroll_view);
        image=findViewById(R.id.movie_image);
        title=findViewById(R.id.movie_title);
        originTitleYear=findViewById(R.id.movie_origin_title_year);
        message=findViewById(R.id.detail_message);
        rating=findViewById(R.id.rating_number);
        noneRating=findViewById(R.id.none_rating);
        stagePhotoTitle=findViewById(R.id.stage_photo);
        stagePhotoCardView=findViewById(R.id.stage_photo_card_view);
        ratingCardView=findViewById(R.id.rating_card);
        ratingBar=findViewById(R.id.movie_detail_rating_bar);
        star5=findViewById(R.id.progress_bar_h5);
        star4=findViewById(R.id.progress_bar_h4);
        star3=findViewById(R.id.progress_bar_h3);
        star2=findViewById(R.id.progress_bar_h2);
        star1=findViewById(R.id.progress_bar_h1);
        starCount=findViewById(R.id.rating_count);
        watchMovie=findViewById(R.id.watch_movie);
        summary=findViewById(R.id.summary);
        totalComments=findViewById(R.id.total_comments);
        totalStagePhotos=findViewById(R.id.total_stage_photos);
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
        //适配android 9.0的行间距
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O_MR1) {
//            message.setLineSpacing(1.1f,1f);
//            summary.setLineSpacing(1.1f,1f);
//        }
    }

    /**
     * 唤醒底部抽屉
     */
    private void wakeBottomSheet() {
        resourceIconRecyclerViewAdapter.setOnResourceIconClickListener(new ResourceIconRecyclerViewAdapter.OnResourceIconClickListener() {
            @Override
            public void onClick() {
                //弹出框的子布局
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
                    public void onClick(String resourceUrl,String resourceName) {
                        if(resourceUrl!=null){
                            //getResourcePackageName=new GetResourcePackageName(resourceName,MovieDetailActivity.this);
//                            String resourcePackageName=getResourcePackageName.getAppPackageName();
//                            if(resourcePackageName!=null){
//                                openUrlWithPackageName(MovieDetailActivity.this,resourceUrl,resourcePackageName);
//                            }else{
                                openUrlWithBrowser(MovieDetailActivity.this,resourceUrl);
//                            }
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
     * 根据应用包名指定app打开Url
     *  该方法暂时不用
     * @param context
     * @param url
     */
    public static void openUrlWithPackageName(Context context, String url, String PackageName) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            intent.setPackage(PackageName);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, "跳转错误："+e.toString());
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
                        //上滑更改toolbar样式
                        changeToolbarStyle();
                        //获取全部影评
                        showtotalComents();
                        //获取全部剧照
                        showTotalStagePhoto();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(MovieDetailItem movieDetailItem) {
                        //传递给全部评论页面作为标题数据
                        movieTitle=movieDetailItem.getTitle();
                        ratingNumber=movieDetailItem.getRating().getAverage();
                        //为获取海报图片对movieDetailItem取实例
                        localMovieDetailItem=new MovieDetailItem();
                        localMovieDetailItem=movieDetailItem;
                        setDetailData=new SetMovieDetailData(movieDetailItem);
                        setDetailData.setMovieMessage(detailTitleText,image,title,originTitleYear,message,ratingBar,rating,
                                noneRating,star5,star4,star3,star2,star1,starCount,summary,view,detailToolbar,stagePhotoCardView);
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
        //判断是否需要查看全部短评（是否有大于4个短评）
        if (movieDetailItem.getPopular_comments().size()<4){
            totalComments.setVisibility(View.GONE);
        }else {
            totalComments.setVisibility(View.VISIBLE);
        }
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
        stageRecyclerViewAdapter=new StageRecyclerViewAdapter();
        stageRecyclerView.setAdapter(stageRecyclerViewAdapter);
        stageRecyclerViewAdapter.setData(stagePhotoInfos);
        stageRecyclerViewAdapter.notifyDataSetChanged();
        commentRecyclerViewAdapter=new CommentRecyclerViewAdapter(Color.WHITE);
        commentRecyclerView.setAdapter(commentRecyclerViewAdapter);
        commentRecyclerViewAdapter.setData(commentInfos);
        castRecyclerViewAdapter.notifyDataSetChanged();
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
                if(castInfo.getCastId()!=null) {
                    String castId = castInfo.getCastId();
                    //当id为空,传空值给卡司详情无法做网络请求时的备用数据
                    String castName = castInfo.getCastName();
                    String castDetailPhoto = castInfo.getCastPicture();
                    String castDetailFilmPicture = localMovieDetailItem.getImages().getLarge();
                    String castDetailFilmTitle = localMovieDetailItem.getTitle();
                    double castDetailFilmRating = localMovieDetailItem.getRating().getAverage();
                    String castDetailFilmId = localMovieDetailItem.getId();
                    Intent intent = new Intent(MovieDetailActivity.this, CastDetailActivity.class);
                    intent.putExtra("castId", castId);
                    //传递备用数据（暂不用）
                    intent.putExtra("castName", castName);
                    intent.putExtra("castEnName", castName);
                    intent.putExtra("castDetailPhoto", castDetailPhoto);
                    intent.putExtra("castDetailFilmPicture", castDetailFilmPicture);
                    intent.putExtra("castDetailFilmTitle", castDetailFilmTitle);
                    intent.putExtra("castDetailFilmRating", castDetailFilmRating);
                    intent.putExtra("castDetailFilmId", castDetailFilmId);
                    startActivity(intent);
                }else {
                    Toast.makeText(MovieDetailActivity.this,"暂无该影人信息",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 获取全部电影剧照
     */
    private void showTotalStagePhoto() {
        totalStagePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MovieDetailActivity.this,TotalStagePhotosActivity.class);
                intent.putExtra("url","https://douban.uieee.com/v2/movie/subject/");
                intent.putExtra("id",id);
                intent.putExtra("title",movieTitle+"的剧照");
                startActivity(intent);
            }
        });

    }

    /**
     * 获取全部短评
     */
    private void showtotalComents() {
        totalComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MovieDetailActivity.this,TotalCommentsActivity.class);
                intent.putExtra("movieId",id);
                intent.putExtra("movieTitle",movieTitle);
                intent.putExtra("rating",ratingNumber);
                startActivity(intent);
            }
        });
        ratingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MovieDetailActivity.this,TotalCommentsActivity.class);
                intent.putExtra("movieId",id);
                intent.putExtra("movieTitle",movieTitle);
                intent.putExtra("rating",ratingNumber);
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
