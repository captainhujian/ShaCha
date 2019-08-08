package com.example.luowenliang.idouban.moviedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.luowenliang.idouban.BaseActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.moviedetail.adapter.StageRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;
import com.example.luowenliang.idouban.moviedetail.service.TotalStagePhotosService;
import com.example.luowenliang.idouban.moviedetail.entity.TotalStagePhotosItem;
import com.example.luowenliang.idouban.moviedetail.utils.SmartRecyclerViewScrollLsnr;
import com.example.luowenliang.idouban.photoViewer.ViewPagerActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TotalStagePhotosActivity extends BaseActivity {
    private TextView totalPhotosTitle,back;
    private RecyclerView recyclerView;
    private String id,title,totalCount,url;
    private List<StagePhotoInfo>totalPhotoInfos=new ArrayList<>();
    private StageRecyclerViewAdapter adapter;
    private static int COUNT=100;
    private int start=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_stage_photos);
        back=findViewById(R.id.total_photos_back);
        totalPhotosTitle=findViewById(R.id.total_photos_title);
        recyclerView=findViewById(R.id.total_photos_recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false) {};
        recyclerView.setLayoutManager(gridLayoutManager);
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        id = intent.getStringExtra("id");
        title=intent.getStringExtra("title");
        Log.d("传值", "result: "+url+";"+id+";"+title);
        adapter=new StageRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        initTotalPhotos(0);
        quitActivity();
    }


    /**
     * 退出页面
     */
    private void quitActivity() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private rx.Observable<TotalStagePhotosItem> requestTotalPhotos(int start) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        TotalStagePhotosService totalStagePhotosService=retrofit.create(TotalStagePhotosService.class);
        Log.d("更多", "start: "+start);
        return totalStagePhotosService.getTotalStagePhotos(id,String.valueOf(start),String.valueOf(COUNT));
    }
    private void initTotalPhotos(int start){
        requestTotalPhotos(start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TotalStagePhotosItem>() {
                    @Override
                    public void onCompleted() {
                        adapter.setData(totalPhotoInfos);
                        adapter.notifyDataSetChanged();
                        totalCount="("+ adapter.getItemCount()+"张)";
                        totalPhotosTitle.setText(title+totalCount);
                        //图片的点击事件
                        setPhotoOnclickItem();
                        //加载更多
//                        loadMorePicture(recyclerView);剧照并不是使用分页请求
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("传值", "onError: "+e.toString());
                    }

                    @Override
                    public void onNext(TotalStagePhotosItem totalStagePhotosItem) {
                        Log.d("更多", "totalStagePhotosItem: "+totalStagePhotosItem.getCount());
                        String photoUrl;
                       for(int i=0;i<totalStagePhotosItem.getPhotos().size();i++){
                           TotalStagePhotosItem.PhotosBean photosBean=totalStagePhotosItem.getPhotos().get(i);
                           photoUrl=photosBean.getThumb();
                           StagePhotoInfo info=new StagePhotoInfo(photoUrl,null,null,0);
                           totalPhotoInfos.add(info);
                       }
                    }
                });
    }

    /**
     * 图片的点击事件(查看大图)
     */
    private void setPhotoOnclickItem() {
        adapter.setOnPhotoListener(new StageRecyclerViewAdapter.OnPhotoClickListener() {
            @Override
            public void onClick(List<StagePhotoInfo> stagePhotoInfos, int i) {
                Intent intent = new Intent(TotalStagePhotosActivity.this,ViewPagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("photo", (Serializable) stagePhotoInfos);
                intent.putExtras(bundle);
                intent.putExtra("currentPosition",i);
                startActivity(intent);
            }
        });
    }
    /**
     * 监听滑动，加载更多图片
     */
    private void loadMorePicture(RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(new SmartRecyclerViewScrollLsnr(this) {
            @Override
            public void onLoadMore() {
                start++;
                initTotalPhotos(COUNT*start);
            }
        });
    }

    @Override
    public boolean isActivitySlideBack() {
        return true;
    }
}
