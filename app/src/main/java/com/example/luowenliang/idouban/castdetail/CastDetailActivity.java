package com.example.luowenliang.idouban.castdetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luowenliang.idouban.BaseActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.castdetail.adapter.CastAlbumRecyclerViewAdapter;
import com.example.luowenliang.idouban.castdetail.adapter.CastFilmRecyclerViewAdapter;
import com.example.luowenliang.idouban.castdetail.entity.CastDetailAlbumInfo;
import com.example.luowenliang.idouban.castdetail.entity.CastDetailFilmInfo;
import com.example.luowenliang.idouban.castdetail.entity.CastDetailItem;
import com.example.luowenliang.idouban.castdetail.service.CastDetailService;
import com.example.luowenliang.idouban.moviedetail.MovieDetailActivity;
import com.example.luowenliang.idouban.moviedetail.TotalStagePhotosActivity;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;
import com.example.luowenliang.idouban.photoViewer.ViewPagerActivity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.luowenliang.idouban.moviedetail.MovieDetailActivity.PICTURE_PLACE_HOLDER;

public class CastDetailActivity extends BaseActivity {
    private static final String TAG = "影人详情";
    private CastDetailItem localCastDetailItem;
    private String castId,castName,castPhoto,castSpareName,castSpareEnName,castSpareDetailFilmPicture,castSpareDetailFilmTitle,castSpareDetailFilmId,castSpareDetailPhoto;
    private double castSpareDetailFilmRating;
    private CastDetailFilmInfo castDetailFilmInfo;
    private List<CastDetailFilmInfo>castDetailFilmInfos=new ArrayList<>();
    private CastDetailAlbumInfo castDetailAlbumInfo;
    private List<CastDetailAlbumInfo>castDetailAlbumInfos=new ArrayList<>();
    private ImageView castPhotoView;
    private TextView castDetailExit,castDetailNameView,castDetailOriginNameView,birthdayView,bornPlaceView,professionsView,castSummaryView,castTotalWorks,castTotalPhotos;
    private RelativeLayout castFilmTitle,castAlbumTitle;
    private RecyclerView castFilmRecyclerView;
    private RecyclerView castAlbumRecyclerView;
    private CastFilmRecyclerViewAdapter castFilmRecyclerViewAdapter;
    private CastAlbumRecyclerViewAdapter castAlbumRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSlideable(isActivitySlideBack());
        setContentView(LayoutInflater.from(this).inflate(R.layout.activity_cast_detail,null,false));
        //界面和控件初始化
        initView();
        showHideFullSummary();
        castFilmRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        castAlbumRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //接收影人id，如果id不为空，接着进行影人详情信息的网络请求，如果为空使用intent传来的备用值进行赋值
        Intent intent =getIntent();
        castId = intent.getStringExtra("castId");
        Log.d(TAG, "接收id："+castId);
        if(castId!=null){
            initCastDetailData();
        }else{
            //initSpareCastDetailData(intent);(使用备用数据会出现影视跳转崩溃，先屏蔽)
        }

        exitCastDetailActivity();
    }

    /**
     * 按键退出
     */
    private void exitCastDetailActivity() {
        castDetailExit.setOnClickListener(new View.OnClickListener() {
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
     * 绑定控件
     */
    private void initView() {
        castDetailExit=findViewById(R.id.cast_detail_exit);
        castPhotoView=findViewById(R.id.cast_photo);
        castDetailNameView=findViewById(R.id.cast_detail_name);
        castDetailOriginNameView=findViewById(R.id.cast_detail_origin_name);
        birthdayView=findViewById(R.id.birthday);
        bornPlaceView=findViewById(R.id.born_place);
        professionsView=findViewById(R.id.professions);
        castSummaryView=findViewById(R.id.cast_summary);
        castFilmTitle=findViewById(R.id.cast_film_title);
        castTotalWorks=findViewById(R.id.total_works);
        castTotalPhotos=findViewById(R.id.total_cast_photos);
        castAlbumTitle=findViewById(R.id.album);
        castFilmRecyclerView=findViewById(R.id.film_recycler_view);
        castAlbumRecyclerView=findViewById(R.id.album_recycler_view);
        //适配android 9.0的行间距
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O_MR1) {
//            castSummaryView.setLineSpacing(1.1f,1f);
//        }
    }

    /**
     * 影人信息的网络请求
     * @return
     */
    private rx.Observable<CastDetailItem> requsetCastDetailData() {
        String castDetailName=null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://douban.uieee.com/v2/movie/celebrity/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        CastDetailService castDetailService = retrofit.create(CastDetailService.class);
//        rx.Observable<MovieItem> top250Movie = movieService.getMovie();//"0b2bdeda43b5688921839c8ecb20399b"
        return castDetailService.getCastDetail(castId);
    }
    //RxJava数据线程切换
    private void initCastDetailData() {
        requsetCastDetailData()
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<CastDetailItem>() {
                    @Override
                    public void onCompleted() {
                        showRecyclerViewData();
                        setPosterOnclickItem();
                        setAlbumRecyclerViewOnclickItem();
                        setFilmRecyclerViewOnclickItem();
                        getTotalWorks(castName,castPhoto);
                        getTotalCastPotos();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(CastDetailItem castDetailItem) {
                        //为获取海报图片对movieDetailItem取实例
                        localCastDetailItem=castDetailItem;
                        //防止有的图片为空导致页面不显示数据，这里设置占位图
                        String castDetailPhoto = null;
                            if(castDetailItem.getAvatars().getLarge()==null){
                                castDetailPhoto=PICTURE_PLACE_HOLDER;
                            }
                            else{
                                castDetailPhoto =castDetailItem.getAvatars().getLarge();
                            }
                       String castDetailName=castDetailItem.getName();
                       String castDetailOriginName=castDetailItem.getName_en();
                       String birthday=castDetailItem.getBirthday();
                       String castSummary=castDetailItem.getSummary();
                       String bornPlace=castDetailItem.getBorn_place();
                       List<String>professions=castDetailItem.getProfessions();
                        setCastDetailMessage(castDetailPhoto,castDetailName,castDetailOriginName,birthday,bornPlace,professions,castSummary);
                        setFilmData(castDetailItem);
                        setAlbum(castDetailItem);
                        //为了传值给全部作品界面和全部图片显示标题toolbar
                        castName=castDetailName;
                        castPhoto=castDetailPhoto;
                    }

                });
    }


    /**
     * 如果castId为空,无法做网络请求，使用intent传来的备用值
     * 接收备用数据,并接入布局
     */
    private void initSpareCastDetailData(Intent intent) {
        castSpareDetailPhoto=intent.getStringExtra("castDetailPhoto");
        castSpareName= intent.getStringExtra("castName");
        castSpareEnName=intent.getStringExtra("castEnName");
        castSpareDetailFilmPicture = intent.getStringExtra("castDetailFilmPicture");
        castSpareDetailFilmTitle=intent.getStringExtra("castDetailFilmTitle");
        castSpareDetailFilmRating=intent.getDoubleExtra("castDetailFilmRating",0f);
        castSpareDetailFilmId=intent.getStringExtra("castDetailFilmId");
        //星级评分
        double fitSpareFilmRate = fitRating(castSpareDetailFilmRating);
        List<String>noneProfessions=null;
        setCastDetailMessage(castSpareDetailPhoto,castSpareName,castSpareEnName,"","",noneProfessions,"");
        CastDetailFilmInfo spareCastDetailFilmInfo=new CastDetailFilmInfo(castSpareDetailFilmPicture,castSpareDetailFilmTitle,
                castSpareDetailFilmRating,castSpareDetailFilmId,fitSpareFilmRate);
        List<CastDetailFilmInfo>castSpareDetailFilmInfos=new ArrayList<>();
        castSpareDetailFilmInfos.add(spareCastDetailFilmInfo);
        castFilmRecyclerViewAdapter=new CastFilmRecyclerViewAdapter(castSpareDetailFilmInfos);
        castFilmRecyclerView.setAdapter(castFilmRecyclerViewAdapter);
    }

    /**
     * 接入非recyclerView的网络请求数据
     */
    private void setCastDetailMessage(String castPhoto, String castDetailName,String castDetailOriginName,String birthay,
                                      String bornPlace, List<String>professions, String castSummary) {
        //防止空指针
        if(castSummary.equals("")){ castSummary="暂无简介"; }
        if(birthay.equals("")){birthay="未知";}
        if(bornPlace.equals("")){bornPlace="未知";}
        professionsView.setText("职业：");
        if(!(professions==null)){
            for(int i=0;i<professions.size();i++){
                String tempProfession=professions.get(i)+" ";
                professionsView.append(tempProfession);
            }
        }else{
            professionsView.append("未知");
        }

        Glide.with(MyApplication.getContext()).load(castPhoto)
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .apply(new RequestOptions().error(R.drawable.placeholder))
                .into(castPhotoView);
        castDetailNameView.setText(castDetailName);
        castDetailOriginNameView.setText(castDetailOriginName);
        birthdayView.setText("出生日期："+birthay);
        bornPlaceView.setText("出生地："+bornPlace);
        castSummaryView.setText(castSummary);
    }

    /**
     * 获取影视信息
     * @param castDetailItem
     */
    private void setFilmData(CastDetailItem castDetailItem) {
        castFilmTitle.setVisibility(View.VISIBLE);
        //若作品少于5个则关闭显示全部作品功能
        if(castDetailItem.getWorks().size()<5){
            castTotalWorks.setVisibility(View.GONE);
        }else {
            castTotalWorks.setVisibility(View.VISIBLE);
        }
        //防止有的图片为空导致recyclerView不显示，这里设置占位图
        String filmPicture = null;
        for(int i=0;i<castDetailItem.getWorks().size();i++){
            if(castDetailItem.getWorks().get(i).getSubject().getImages()==null){
                filmPicture=PICTURE_PLACE_HOLDER;
            }
            else{
                filmPicture=castDetailItem.getWorks().get(i).getSubject().getImages().getLarge();
                Log.d(TAG, "directorpicture: "+filmPicture);
            }
            String filmTitle=castDetailItem.getWorks().get(i).getSubject().getTitle();
            double filmRating = castDetailItem.getWorks().get(i).getSubject().getRating().getAverage();
            String filmId=castDetailItem.getWorks().get(i).getSubject().getId();
            //星级评分
            double fitFilmRate = fitRating(filmRating);
            Log.d("分数", "合理的分数: " + fitFilmRate);
            castDetailFilmInfo=new CastDetailFilmInfo(filmPicture,filmTitle,filmRating,filmId,fitFilmRate);
            castDetailFilmInfos.add(castDetailFilmInfo);
        }
    }

    /**
     * 获取影人全部作品
     */
    private void getTotalWorks(final String castName, final String castPhoto) {
        castTotalWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CastDetailActivity.this,TotalWorksActivity.class);
                intent.putExtra("cast_id",castId);
                intent.putExtra("cast_total_title",castName);
                intent.putExtra("cast_photo",castPhoto);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取相册数据
     */
    private void setAlbum(CastDetailItem castDetailItem) {
        castAlbumTitle.setVisibility(View.VISIBLE);
        for (int j =0;j<castDetailItem.getPhotos().size();j++){
            String album= castDetailItem.getPhotos().get(j).getImage();
            castDetailAlbumInfo=new CastDetailAlbumInfo(album);
            castDetailAlbumInfos.add(castDetailAlbumInfo);
        }
        if (castDetailAlbumInfos.size()==0){
            castAlbumTitle.setVisibility(View.GONE);
        }

    }

    /**
     * 获取影人全部照片
     */
    private void getTotalCastPotos() {
        castTotalPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CastDetailActivity.this,TotalStagePhotosActivity.class);
                intent.putExtra("url","https://douban.uieee.com/v2/movie/celebrity/");
                intent.putExtra("id",castId);
                intent.putExtra("title",castName+"的照片");
                startActivity(intent);
            }
        });
    }

    /**
     * 网络请求数据接入RecyclerView
     */
    private void showRecyclerViewData() {
        Log.d(TAG, "showRecyclerViewData: ");
        Log.d(TAG, "castDetailFilmInfos: "+castDetailFilmInfos);
        castFilmRecyclerViewAdapter=new CastFilmRecyclerViewAdapter(castDetailFilmInfos);
        castFilmRecyclerView.setAdapter(castFilmRecyclerViewAdapter);
        castAlbumRecyclerViewAdapter =new CastAlbumRecyclerViewAdapter(castDetailAlbumInfos);
        castAlbumRecyclerView.setAdapter(castAlbumRecyclerViewAdapter);
    }


    /**简介点击展开收缩全文*/
    private void showHideFullSummary(){
        castSummaryView.setOnClickListener(new View.OnClickListener() {
            boolean flag=true;
            @Override
            public void onClick(View view) {
                if(flag){
                    flag = false;
                    castSummaryView.setEllipsize(null); // 展开
                    castSummaryView.setSingleLine(flag);
                    Log.d(TAG, "展开啦！");
                }else{
                    flag = true;
                    castSummaryView.setLines(4);
                    castSummaryView.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    Log.d(TAG, "收缩啦！");
                }
            }
        });
    }
    /**
     * 主题海报的点击事件
     */
    private void setPosterOnclickItem(){
        castPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CastDetailActivity.this,ViewPagerActivity.class);
                Bundle bundle = new Bundle();
                List<StagePhotoInfo> photos=new ArrayList<>();
                StagePhotoInfo photo=new StagePhotoInfo(localCastDetailItem.getAvatars().getLarge(),null,null,0);
                photos.add(photo);
                bundle.putSerializable("photo",(Serializable)photos);
                intent.putExtras(bundle);
                intent.putExtra("currentPosition",0);
                startActivity(intent);
            }
        });
    }

    /**
     * 影视的点击事件
     */
    private void setFilmRecyclerViewOnclickItem(){
        castFilmRecyclerViewAdapter.setOnFilmClickListener(new CastFilmRecyclerViewAdapter.OnFilmClickListener() {
            @Override
            public void onClick(String filmId) {
                Intent intent = new Intent(CastDetailActivity.this,MovieDetailActivity.class);
                intent.putExtra("id", filmId);
                Log.d(TAG, "id: "+filmId);
                startActivity(intent);
            }
        });
    }


    /**
     * 相册的点击事件
     */
    private void setAlbumRecyclerViewOnclickItem(){
        castAlbumRecyclerViewAdapter.setOnAlbumListener(new CastAlbumRecyclerViewAdapter.OnAlbumClickListener(){
            @Override
            public void onClick(List<CastDetailAlbumInfo> castDetailAlbumInfos,int i) {
                Intent intent = new Intent(CastDetailActivity.this,ViewPagerActivity.class);
                Bundle bundle = new Bundle();
                List<StagePhotoInfo> photos=new ArrayList<>();
                for(int k=0;k<castDetailAlbumInfos.size();k++){
                    StagePhotoInfo photo=new StagePhotoInfo(castDetailAlbumInfos.get(k).getAlbum(),null,null,0);
                    photos.add(photo);
                }
                bundle.putSerializable("photo", (Serializable) photos);
                intent.putExtras(bundle);
                intent.putExtra("currentPosition",i);
                startActivity(intent);
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
         finish();
    }
}
