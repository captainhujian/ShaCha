package com.example.luowenliang.idouban.moviedetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luowenliang.idouban.BaseActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.adapter.CommentRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviedetail.entity.CommentInfo;
import com.example.luowenliang.idouban.moviedetail.entity.CommentsItem;
import com.example.luowenliang.idouban.moviedetail.service.TotalCommentsService;
import com.example.luowenliang.idouban.moviedetail.utils.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TotalCommentsActivity extends BaseActivity {
    private String id,title;
    private int backGroundColor;
    private TextView titleView;
    private Toolbar commentsToolbar;
    private RecyclerView totalCommentsRecyclerView;
    private CommentRecyclerViewAdapter adapter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view =LayoutInflater.from(this).inflate(R.layout.activity_total_comments, null, false);
        setContentView(view);
        titleView=findViewById(R.id.comments_title);
        commentsToolbar=findViewById(R.id.comments_toolbar);
        totalCommentsRecyclerView=findViewById(R.id.total_comments_recyclerView);
        totalCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        totalCommentsRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //获取背景颜色并使用
        backGroundColor= SharePreferencesUtil.getInt(MyApplication.getContext(),"background_color",0);
        commentsToolbar.setBackgroundColor(backGroundColor);
        Intent intent=getIntent();
        id = intent.getStringExtra("movieId");
        title=intent.getStringExtra("movieTitle");
        initTotalComments();
    }

    /**
     * 请求全部短评
     */
        private rx.Observable<CommentsItem> requestTotalComments() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.douban.uieee.com/v2/movie/subject/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            TotalCommentsService totalCommentsService = retrofit.create(TotalCommentsService.class);
            return totalCommentsService.getTotalComments(id);
        }

        private void initTotalComments(){
            final List<CommentInfo> totalCommentsList=new ArrayList<>();
            requestTotalComments()
                    .subscribeOn(Schedulers.io())//io加载数据
                    .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                    .subscribe(new Subscriber<CommentsItem>() {
                        @Override
                        public void onCompleted() {
                            titleView.setText(title);
                            adapter=new CommentRecyclerViewAdapter(totalCommentsList,Color.BLACK);
                            totalCommentsRecyclerView.setAdapter(adapter);
                            //等数据加载完再填充背景色比较好看
                            view.setBackgroundColor(backGroundColor);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(CommentsItem commentsItem) {
                            String commenterPisture,commenterName,commentTime,comment;
                            double commenterRating;
                            int usefulCount;
                            for (int i = 0; i < commentsItem.getComments().size(); i++) {
                                CommentsItem.CommentsBean commentsBean=commentsItem.getComments().get(i);
                                commenterPisture=commentsBean.getAuthor().getAvatar();
                                commenterName=commentsBean.getAuthor().getName();
                                commentTime=commentsBean.getCreated_at();
                                comment=commentsBean.getContent();
                                commenterRating=commentsBean.getRating().getValue();
                                usefulCount=commentsBean.getUseful_count();
                                CommentInfo commentInfo=new CommentInfo(commenterPisture,commenterName,commenterRating,commentTime,comment,usefulCount);
                                totalCommentsList.add(commentInfo);
                            }
                        }
                    });
        }

    @Override
    public boolean isActivitySlideBack() {
        return true;
    }
}
