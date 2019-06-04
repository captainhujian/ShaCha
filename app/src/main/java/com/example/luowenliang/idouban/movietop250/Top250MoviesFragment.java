package com.example.luowenliang.idouban.movietop250;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.luowenliang.idouban.moviedetail.MovieDetailActivity;
import com.example.luowenliang.idouban.R;
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
    private List<Top250Movie> movieList = new ArrayList<>();
    private List<Top250Movie>addedMovieList=new ArrayList<>();
    private MovieRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;
    int lastVisibleItem;
    private  int start = 0;
    private static int count = 10;
    //用来控制进入getdata()的次数
    boolean isLoading = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_movies_top250, null);
        progressBar = view.findViewById(R.id.progress_bar);
        movieRecyclerView = view.findViewById(R.id.movies_list);
        //网络请求top250
        Log.d(TAG, "第1次请求成功");
        initMovieTop250Data(String.valueOf(start));
        Log.d(TAG, "第1次请求成功");
        return view;
    }



    private void initData() {
        linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        movieRecyclerView.setLayoutManager(linearLayoutManager);
        movieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MovieRecyclerViewAdapter(getContext());
        movieRecyclerView.setAdapter(adapter);
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
                    if (start < 24) {
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
        Log.d(TAG, "initMovieTop250Data: ");
        requsetMovieData(start)
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<MovieItem>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        progressBar.setVisibility(View.GONE);
                        initData();
                        // 然后调用updateRecyclerview方法更新RecyclerView
                       // updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                        adapter.setData(movieList);
                        adapter.notifyDataSetChanged();
                        MovieRecyclerViewOnClickItem();
                        //上拉加载标志位更新
                        isLoading=false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(MovieItem movieItem) {
                        String cast1,cast2 ,genres1,genres2;
                        Log.d(TAG, "onNext: ");
                        progressBar.setVisibility(View.VISIBLE);
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
                            String durations = movieItem.getSubjects().get(i).getDurations().get(0);
                            String year = "(" + movieItem.getSubjects().get(i).getYear() + ")";
                            String director = movieItem.getSubjects().get(i).getDirectors().get(0).getName();
                            if(movieItem.getSubjects().get(i).getCasts().size()>=2){
                             cast1 = movieItem.getSubjects().get(i).getCasts().get(0).getName();
                             cast2 = " "+movieItem.getSubjects().get(i).getCasts().get(1).getName();
                            }else {
                                cast1 = movieItem.getSubjects().get(i).getCasts().get(0).getName();
                                cast2 ="";
                            }
                            if(movieItem.getSubjects().get(i).getGenres().size()>=2){
                                genres1 = movieItem.getSubjects().get(i).getGenres().get(0);
                                genres2 = " "+movieItem.getSubjects().get(i).getGenres().get(1);
                            }else {
                                genres1 = movieItem.getSubjects().get(i).getGenres().get(0);
                                genres2 = "";
                            }

//                            Log.d(TAG, "海报：" + image + " 影片名：" + title + " 原名：" + original_title + " 主演：" + cast1 +"/"+cast2+ " 导演：" +director+ " 评分：" + rating
//                                    + " 类型：" + genres1+"/"+genres2 + " 时长：" + durations + " 年份：" + year + "\n");
                            top250Movie = new Top250Movie(image, title, original_title, cast1,cast2, rating, genres1,genres2,
                                    durations, year, director,id);
                            movieList.add(top250Movie);
                        }
                       // Log.d(TAG, "movieList: " + movieList.size());

                    }

                });
    }
    

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

//    // 上拉加载时调用的更新RecyclerView的方法
//    private void updateRecyclerView(int fromIndex, int toIndex) {
//        // 获取从fromIndex到toIndex的数据
//        List<Top250Movie> newDatas = getDatas(fromIndex, toIndex);
//        if (newDatas.size() > 0) {
//            // 然后传给Adapter，并设置hasMore为true
//            adapter.updateList(newDatas);
//        } else {
//            adapter.updateList(null);
//        }
//    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}


