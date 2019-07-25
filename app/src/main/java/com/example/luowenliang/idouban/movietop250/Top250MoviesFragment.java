package com.example.luowenliang.idouban.movietop250;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luowenliang.idouban.moviedetail.MovieDetailActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.moviedetail.utils.NetworkUtil;
import com.example.luowenliang.idouban.movietop250.entity.MovieItem;
import com.example.luowenliang.idouban.movietop250.entity.Top250Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.luowenliang.idouban.moviedetail.MovieDetailActivity.PICTURE_PLACE_HOLDER;

public class Top250MoviesFragment extends Fragment {
    private static final String TAG = "豆瓣";
    private RecyclerView movieRecyclerView;
    private Top250Movie top250Movie;
    private MovieRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    private  int start = 0;
    private static int count = 25;
    private boolean isLoading = false;
    private boolean isFirstRequest=true;
    private RelativeLayout errorView,loadingView;
    private CoordinatorLayout succeedView;
    private TextView request250Error;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_movies_top250, null);
        errorView=view.findViewById(R.id.top250_error);
        succeedView=view.findViewById(R.id.top250_succeed);
        loadingView=view.findViewById(R.id.top250_progress_bar);
//        progressBar = view.findViewById(R.id.progress_bar);
        request250Error=view.findViewById(R.id.request250_error);
        movieRecyclerView = view.findViewById(R.id.movies_list);
        linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        movieRecyclerView.setLayoutManager(linearLayoutManager);
        movieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //adapter实例化完成，等待数据加入，不能重复new和setAdapter，否则每次都会回到第一个item
        adapter = new MovieRecyclerViewAdapter(getContext());
        movieRecyclerView.setAdapter(adapter);
        //网络请求top250
        Log.d(TAG, "第1次请求成功");
        initMovieTop250Data(String.valueOf(start));
        Log.d(TAG, "第1次请求成功");
        loadingView.setVisibility(View.VISIBLE);
        succeedView.setVisibility(View.GONE);
        clickToRefresh();
        return view;
    }

    /**
     * 请求失败时点击重新请求
     */
    private void clickToRefresh() {
       request250Error.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               errorView.setVisibility(View.GONE);
               loadingView.setVisibility(View.VISIBLE);
               succeedView.setVisibility(View.GONE);
               initMovieTop250Data(String.valueOf(start));
           }
       });

    }


    private void initData() {
        //给recyclerView添加滑动监听
        movieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (start < 9) {
                        Log.e(TAG, "onScrollStateChanged: " + "进来了");
                        isLoading = true;
                        adapter.changeState(1);
                        start++;
                        Log.d(TAG, "第"+(start+1)+"次请求开始");
                        initMovieTop250Data(String.valueOf(start*count));
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

    private rx.Observable<MovieItem> requsetMovieData(String start) {
        Log.d(TAG, "start:"+start+"count:"+count);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://douban.uieee.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Top250MovieService movieService = retrofit.create(Top250MovieService.class);
//        rx.Observable<MovieItem> top250Movie = movieService.getMovie();//"0b2bdeda43b5688921839c8ecb20399b"

        return movieService.getMovie(start,String.valueOf(count));//

    }

    private void initMovieTop250Data(String start) {
        final List<Top250Movie>updateMovieList=new ArrayList<>();
        Log.d(TAG, "initMovieTop250Data: ");
        requsetMovieData(start)
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<MovieItem>() {
                    @Override
                    public void onCompleted() {
                        if (isFirstRequest){
                            errorView.setVisibility(View.GONE);
                            loadingView.setVisibility(View.GONE);
                            succeedView.setVisibility(View.VISIBLE);
                            isFirstRequest=false;
                        }
                        Log.d(TAG, "onCompleted: ");
//                        progressBar.setVisibility(View.GONE);
                        initData();
                        adapter.setData(updateMovieList);
                        adapter.notifyDataSetChanged();
                        MovieRecyclerViewOnClickItem();
                        //上拉加载标志位更新
                        isLoading=false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        //判断网络情况，更改请求失败提示词
                        if(NetworkUtil.getNetWorkStart(getContext())==1){
                            request250Error.setText(getContext().getText(R.string.no_network));
                        }else {
                            request250Error.setText(getContext().getText(R.string.request_error));
                        }
                            errorView.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            succeedView.setVisibility(View.GONE);
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(MovieItem movieItem) {
                        if(isFirstRequest){
                            errorView.setVisibility(View.GONE);
                            loadingView.setVisibility(View.VISIBLE);
                            succeedView.setVisibility(View.GONE);
                        }
                        String durations,director,cast1=null,cast2=null ,genres1=null,genres2=null;
                        Log.d(TAG, "onNext: ");
//                        progressBar.setVisibility(View.VISIBLE);
                        for (int i = 0; i < movieItem.getSubjects().size(); i++) {
                            String id = movieItem.getSubjects().get(i).getId();
                            //防止有的图片为空导致recyclerView不显示，这里设置占位图
                            String image = null;
                            if(movieItem.getSubjects().get(i).getImages()==null){
                                image=PICTURE_PLACE_HOLDER;
                            }
                            else{
                                image=movieItem.getSubjects().get(i).getImages().getSmall();
                            }
                            String title = movieItem.getSubjects().get(i).getTitle();
                            String original_title = movieItem.getSubjects().get(i).getOriginal_title();
                            double rating = movieItem.getSubjects().get(i).getRating().getAverage();
                            if(movieItem.getSubjects().get(i).getDurations()!=null){
                                durations = movieItem.getSubjects().get(i).getDurations().get(0);
                            }else {
                                durations="";
                            }
                            String year = "(" + movieItem.getSubjects().get(i).getYear() + ")";
                            if(movieItem.getSubjects().get(i).getDirectors()!=null){
                                director = movieItem.getSubjects().get(i).getDirectors().get(0).getName();
                            }else {
                                director="";
                            }
                           if(movieItem.getSubjects().get(i).getCasts()!=null){
                               if(movieItem.getSubjects().get(i).getCasts().size()>1){
                                   cast1 = movieItem.getSubjects().get(i).getCasts().get(0).getName();
                                   cast2 = " "+movieItem.getSubjects().get(i).getCasts().get(1).getName();
                               }else if(movieItem.getSubjects().get(i).getCasts().size()==1){
                                   cast1 = movieItem.getSubjects().get(i).getCasts().get(0).getName();
                                   cast2 ="";
                               }
                           }else {
                                cast1="";cast2="";
                           }
                            if(movieItem.getSubjects().get(i).getGenres()!=null){
                                if(movieItem.getSubjects().get(i).getGenres().size()>1){
                                    genres1 = movieItem.getSubjects().get(i).getGenres().get(0);
                                    genres2 = " "+movieItem.getSubjects().get(i).getGenres().get(1);
                                }else if(movieItem.getSubjects().get(i).getGenres().size()==1){
                                    genres1 = movieItem.getSubjects().get(i).getGenres().get(0);
                                    genres2 = "";
                                }
                            }else{
                                genres1="";genres2="";
                            }
//                            Log.d(TAG, "海报：" + image + " 影片名：" + title + " 原名：" + original_title + " 主演：" + cast1 +"/"+cast2+ " 导演：" +director+ " 评分：" + rating
//                                    + " 类型：" + genres1+"/"+genres2 + " 时长：" + durations + " 年份：" + year + "\n");
                            top250Movie = new Top250Movie(image, title, original_title, cast1,cast2, rating, genres1,genres2,
                                    durations, year, director,id);
                            updateMovieList.add(top250Movie);
                        }
                       // Log.d(TAG, "movieList: " + movieList.size());

                    }

                });
    }

    /**
     * 点击事件
     */
    private void MovieRecyclerViewOnClickItem() {
        adapter.setOnItemClickListener(new MovieRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(Top250Movie top250Movie) {
                Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra("id", top250Movie.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}


