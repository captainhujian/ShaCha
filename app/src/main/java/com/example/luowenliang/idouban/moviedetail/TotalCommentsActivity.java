package com.example.luowenliang.idouban.moviedetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.luowenliang.idouban.moviedetail.utils.SetMovieDetailData;
import com.example.luowenliang.idouban.moviedetail.utils.SharePreferencesUtil;
import com.example.luowenliang.idouban.moviedetail.utils.SmartRecyclerViewScrollLsnr;
import com.hedgehog.ratingbar.RatingBar;

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
    private double ratingNumber;
    private int start,count=100;
    private TextView titleView,rating,noneRating,back;
    private Toolbar commentsToolbar;
    private RatingBar ratingBar;
    private RecyclerView totalCommentsRecyclerView;
    private LinearLayout totalCommentsLayout;
    private CommentRecyclerViewAdapter adapter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view =LayoutInflater.from(this).inflate(R.layout.activity_total_comments, null, false);
        setContentView(view);
        back=findViewById(R.id.total_comments_back);
        titleView=findViewById(R.id.comments_title);
        commentsToolbar=findViewById(R.id.comments_toolbar);
        rating=findViewById(R.id.total_comments_rating);
        noneRating=findViewById(R.id.total_comments_none_rating);
        ratingBar=findViewById(R.id.total_comments_rating_bar);
        totalCommentsLayout=findViewById(R.id.comments_rating_view);
        totalCommentsRecyclerView=findViewById(R.id.total_comments_recyclerView);
        totalCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        totalCommentsRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter=new CommentRecyclerViewAdapter(Color.BLACK);
        totalCommentsRecyclerView.setAdapter(adapter);
        //获取背景颜色并使用
        backGroundColor= SharePreferencesUtil.getInt(MyApplication.getContext(),"background_color",0);
        commentsToolbar.setBackgroundColor(backGroundColor);
        //接收传递数据
        Intent intent=getIntent();
        id = intent.getStringExtra("movieId");
        title=intent.getStringExtra("movieTitle");
        ratingNumber=intent.getDoubleExtra("rating",0);
        quitActivity();
        initTotalComments(0);
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

    /**
     * 请求全部短评
     */
        private rx.Observable<CommentsItem> requestTotalComments(int start) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.douban.uieee.com/v2/movie/subject/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            TotalCommentsService totalCommentsService = retrofit.create(TotalCommentsService.class);
            return totalCommentsService.getTotalComments(id,String.valueOf(start),String.valueOf(count));
        }

        private void initTotalComments(int start){
            final List<CommentInfo> totalCommentsList=new ArrayList<>();
            requestTotalComments(start)
                    .subscribeOn(Schedulers.io())//io加载数据
                    .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                    .subscribe(new Subscriber<CommentsItem>() {
                        @Override
                        public void onCompleted() {
                            adapter.setData(totalCommentsList);
                            adapter.notifyDataSetChanged();
                            //等数据加载完再填充背景色比较好看
                            view.setBackgroundColor(backGroundColor);
                            setTitleView();
                            loadMoreComments(totalCommentsRecyclerView);
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

    /**
     * 监听滑动，加载更多评论（分页请求）
     */
    private void loadMoreComments(RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(new SmartRecyclerViewScrollLsnr(this) {
            @Override
            public void onLoadMore() {
                start++;
                initTotalComments(count*start);
            }
        });
    }

    /**
     * 全部评论标题栏赋值
     */
    private void setTitleView() {
        totalCommentsLayout.setVisibility(View.VISIBLE);
        titleView.setText(title);
        //星级评分
        double fitRate =SetMovieDetailData.fitRating(ratingNumber);
        Log.d("分数", "合理的分数: " + fitRate);
        //暂无评价
        if (fitRate == 0f) {
            ratingBar.setVisibility(View.GONE);
            rating.setVisibility(View.GONE);
            noneRating.setVisibility(View.VISIBLE);

        } else {
            ratingBar.setVisibility(View.VISIBLE);
            rating.setText(String.valueOf(ratingNumber));
            ratingBar.setStarCount(5);
            ratingBar.setStar((float) fitRate);
        }
    }

    @Override
    public boolean isActivitySlideBack() {
        return true;
    }
}
