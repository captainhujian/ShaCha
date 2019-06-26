package com.example.luowenliang.idouban.moviehot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.moviedetail.MovieDetailActivity;
import com.example.luowenliang.idouban.moviehot.adapter.ComingSoonRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviehot.adapter.HotMovieRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviehot.adapter.PublicPraiseRecyclerViewAdapter;
import com.example.luowenliang.idouban.moviehot.entity.HotMovieInfo;
import com.example.luowenliang.idouban.moviehot.entity.HotMovieItem;
import com.example.luowenliang.idouban.moviehot.entity.PublicPraiseItem;
import com.example.luowenliang.idouban.moviehot.entity.SearchInfo;
import com.example.luowenliang.idouban.moviehot.entity.SearchItem;
import com.example.luowenliang.idouban.moviehot.service.ComingSoonService;
import com.example.luowenliang.idouban.moviehot.service.HotMovieService;
import com.example.luowenliang.idouban.moviehot.service.PublicPraiseService;
import com.example.luowenliang.idouban.moviehot.service.SearchService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.luowenliang.idouban.moviedetail.MovieDetailActivity.PICTURE_PLACE_HOLDER;

public class HotMoviesFragment extends Fragment {
    private static final String TAG = "热门";
    private static String TOTAL_HOT_MOVIES_URL="in_theaters";
    private static String TOTAL_COMING_SOON_URL="coming_soon";
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText search;
    private TextView hotMovieTitle,hotMovieTotal,comingSoonTitle,comingSoonTotal,publicPraiseTitle,publicPraiseUpdate;
    private String searchText,hotMovieTotalCount,comingSoonTotalCount;
    private SearchInfo searchInfo;
    private HotMovieInfo hotMovieInfo;
    private List<HotMovieInfo> hotMovieInfos = new ArrayList<>();
    private List<HotMovieInfo> comingSoonMovieInfos = new ArrayList<>();
    private List<HotMovieInfo> publicPraiseInfos = new ArrayList<>();
    private RecyclerView hotMovieRecyclerView;
    private HotMovieRecyclerViewAdapter hotMovieRecyclerViewAdapter;
    private RecyclerView comingSoonRecyclerView;
    private ComingSoonRecyclerViewAdapter comingSoonRecyclerViewAdapter;
    private RecyclerView publicPraiseRecyclerView;
    private PublicPraiseRecyclerViewAdapter publicPraiseRecyclerViewAdapter;
    private ProgressBar hotMovieProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_hot, null);
        Log.d("进度条", "热门onCreateView: ");
        search = view.findViewById(R.id.search_text);
        hotMovieTitle = view.findViewById(R.id.hot_movie_title);
        hotMovieTotal=view.findViewById(R.id.hot_movie_total);
        comingSoonTitle = view.findViewById(R.id.coming_soon_title);
        comingSoonTotal=view.findViewById(R.id.coming_soon_total);
        publicPraiseTitle = view.findViewById(R.id.public_praise_title);
        publicPraiseUpdate=view.findViewById(R.id.public_praise_update);
        hotMovieRecyclerView = view.findViewById(R.id.hot_movie_recycler_view);
        comingSoonRecyclerView = view.findViewById(R.id.coming_soon_recycler_view);
        publicPraiseRecyclerView = view.findViewById(R.id.public_praise_recycler_view);
        hotMovieProgressBar = view.findViewById(R.id.hot_movie_progress_bar);
        //网格recyclerView的样式初始化

        //评论不可滑动recyclerview
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        //评论不可滑动recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        hotMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        hotMovieRecyclerView.setLayoutManager(gridLayoutManager);
        comingSoonRecyclerView.setItemAnimator(new DefaultItemAnimator());
        comingSoonRecyclerView.setLayoutManager(gridLayoutManager2);
        publicPraiseRecyclerView.setItemAnimator(new DefaultItemAnimator());
        publicPraiseRecyclerView.setLayoutManager(layoutManager);
        //下拉刷新
        swipeRefreshLayout=view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);//设置加载默认图标
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#CD853F"));
        //设置触发刷新的距离
        swipeRefreshLayout.setDistanceToTriggerSync(200);

        searchMovie();
        initHotMovieData();
        initComingSoonMovieData();
        initPublicPraiseMovieData();
        RefreshMovieData();
        showTotalMovie();


        return view;
    }

    /**
     * 下拉刷新数据
     */
    private void RefreshMovieData(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()

        {
            @Override
            public void onRefresh () {
                Log.d(TAG, "刷新啦！");
                //先清空list防止数据重复加载
                hotMovieInfos.clear();
                comingSoonMovieInfos.clear();
                publicPraiseInfos.clear();
                //重新做网络请求
                initHotMovieData();
                initComingSoonMovieData();
                initPublicPraiseMovieData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 搜索功能
     */
    private void searchMovie() {
        Log.d(TAG, "searchText:"+searchText);
        //回车监听必须加上singleline=true，不然再次搜索会变回换行,发现监听执行了两次，
        // 因为onkey事件包含了down和up事件，所以只需要加入其中一个即可。（选择down按下）
         search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
             @Override
             public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                 if ((actionId==EditorInfo.IME_ACTION_SEARCH ||(keyEvent!=null&&keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER
                         &&keyEvent.getAction() == KeyEvent.ACTION_DOWN))) {
                     if(search.getText()!=null){
                             searchText = search.getText().toString();
                             Log.d(TAG, "点击搜索");
                             //do something
                             initSearchData(searchText);
                         //打开、关闭软键盘
                         InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                         imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        // hideInput();
                     }
                     return true;
                 }
                 return false;
             }
         });
    }

    /**
     * 搜索的网络请求
     * @param searchText
     * @return
     */
    private rx.Observable<SearchItem> requsetSearchMovieData(String searchText) {
        Log.d(TAG, "搜索条件："+searchText);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://douban.uieee.com/v2/book/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        SearchService searchService = retrofit.create(SearchService.class);
        return searchService.getSearchResult(searchText);//

    }
    private void initSearchData(String searchText) {
        requsetSearchMovieData(searchText)
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<SearchItem>() {
                    @Override
                    public void onCompleted() {
                        Intent intent =new Intent(getActivity(),MovieDetailActivity.class);
                        intent.putExtra("id",searchInfo.getSearchId());
                        startActivity(intent);
                        Log.d(TAG, "id:"+searchInfo.getSearchId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.toString());
                    }

                    @Override
                    public void onNext(SearchItem searchItem) {
                        if(searchItem.getBooks()!=null){
                                String id =searchItem.getBooks().get(0).getId();
                                searchInfo=new SearchInfo(id);
                        }

                    }
                });
    }



    /**
     * 影院热门的网络请求(6个)
     * @return
     */
    private rx.Observable<HotMovieItem> requestHotMovieData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://douban.uieee.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        HotMovieService hotMovieService = retrofit.create(HotMovieService.class);
        return hotMovieService.getHotMovieResult();
    }

    /**
     * 影院热门页面电影数据传递到主线程
     */
    private void initHotMovieData() {
        requestHotMovieData()
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<HotMovieItem>() {
                    @Override
                    public void onCompleted() {
                        hotMovieProgressBar.setVisibility(View.GONE);
                        showTitle();
                        hotMovieRecyclerViewAdapter=new HotMovieRecyclerViewAdapter(hotMovieInfos);
                        hotMovieRecyclerView.setAdapter(hotMovieRecyclerViewAdapter);
                        hotMovieTotal.setText("全部"+hotMovieTotalCount+">");
                        HotMovieRecyclerViewOnClickItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.toString());

                    }

                    @Override
                    public void onNext(HotMovieItem hotMovieItem) {

                        hotMovieProgressBar.setVisibility(View.VISIBLE);
                        setHotMovieData(hotMovieItem,hotMovieInfos);
                        hotMovieTotalCount= String.valueOf(hotMovieItem.getTotal());
                    }
                });
    }

    /**
     * 即将上映的网络请求(6个)
     * @return
     */
    private rx.Observable<HotMovieItem> requestComingSoonData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://douban.uieee.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ComingSoonService comingSoonService = retrofit.create(ComingSoonService.class);
        return comingSoonService.getComingSoonResult();
    }
    /**
     * 即将上映电影数据传递到主线程
     */
    private void initComingSoonMovieData() {
        requestComingSoonData()
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<HotMovieItem>() {
                    @Override
                    public void onCompleted() {
                        comingSoonRecyclerViewAdapter=new ComingSoonRecyclerViewAdapter(comingSoonMovieInfos);
                        comingSoonRecyclerView.setAdapter(comingSoonRecyclerViewAdapter);
                        comingSoonTotal.setText("全部"+comingSoonTotalCount+">");
                        Log.d(TAG, "全部："+comingSoonTotalCount);
                        ComingSoonRecyclerViewOnClickItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onErrorComingSoon: "+e.toString());
                    }

                    @Override
                    public void onNext(HotMovieItem hotMovieItem) {
                        setHotMovieData(hotMovieItem,comingSoonMovieInfos);
                        comingSoonTotalCount= String.valueOf(hotMovieItem.getTotal());
                    }
                });
    }
    /**
     * 口碑榜的网络请求
     * @return
     */
    private rx.Observable<PublicPraiseItem> requestPublicPraiseData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://douban.uieee.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        PublicPraiseService publicPraiseService = retrofit.create(PublicPraiseService.class);
        return publicPraiseService.getPublicPraiseResult();
    }
    /**
     * 口碑榜电影数据传递到主线程
     */
    private void initPublicPraiseMovieData() {
        requestPublicPraiseData()
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<PublicPraiseItem>() {
                    @Override
                    public void onCompleted() {
                        publicPraiseRecyclerViewAdapter=new PublicPraiseRecyclerViewAdapter(publicPraiseInfos);
                        publicPraiseRecyclerView.setAdapter(publicPraiseRecyclerViewAdapter);
                        PublicPraiseRecyclerViewOnClickItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onErrorPraise: "+e.toString());
                    }

                    @Override
                    public void onNext(PublicPraiseItem publicPraiseItem) {
                        setPublicPraiseData(publicPraiseItem);

                    }
                });
    }


    /**
     * 影院热门、即将上映电影数据加载到tempList中传递给adapter
     */
    private void setHotMovieData(HotMovieItem hotMovieItem,List<HotMovieInfo>movieInfoList){
        List<HotMovieInfo>tempList=new ArrayList<>();
        //防止有的图片为空导致recyclerView不显示，这里设置占位图
        String hotMoviePicture;
        double hotMovieRating;
        for(int i=0;i<hotMovieItem.getSubjects().size();i++){
            Log.d(TAG, "item: "+hotMovieItem);
            if(hotMovieItem.getSubjects().get(i).getImages()==null){
                hotMoviePicture=PICTURE_PLACE_HOLDER;
            }
            else{
                hotMoviePicture=hotMovieItem.getSubjects().get(i).getImages().getLarge();
                Log.d(TAG, "directorpicture: "+hotMoviePicture);
            }
            String hotMovieTitle=hotMovieItem.getSubjects().get(i).getTitle();
            if(hotMovieItem.getSubjects().get(i).getRating()!=null){
               hotMovieRating= hotMovieItem.getSubjects().get(i).getRating().getAverage();
            }else {
                hotMovieRating=0f;
            }
            String hotMovieId=hotMovieItem.getSubjects().get(i).getId();
            //星级评分
            double fitFilmRate = fitRating(hotMovieRating);
            Log.d("分数", "合理的分数: " + fitFilmRate);
            //想看人数
            String hotMovieCollect=hotMovieItem.getSubjects().get(i).getCollect_count()+"人想看";
            //大陆上映日期
            String hotMoviePubDate;
            if(hotMovieItem.getSubjects().get(i).getMainland_pubdate()!=null){
                hotMoviePubDate= fitDateFormat(hotMovieItem.getSubjects().get(i).getMainland_pubdate());
            }else {
                hotMoviePubDate="时间待定";
            }
            hotMovieInfo=new HotMovieInfo(hotMoviePicture,hotMovieTitle,hotMovieRating,hotMovieId,fitFilmRate,"",hotMovieCollect,hotMoviePubDate);
            tempList.add(hotMovieInfo);
        }
        movieInfoList.addAll(tempList);
    }

    /**
     * 口碑榜数据防空逻辑
     * @param publicPraiseItem
     */
    private void setPublicPraiseData(PublicPraiseItem publicPraiseItem) {
        String publicPraisePicture;
        double publicPraiseRating;
        for (int i = 0; i < publicPraiseItem.getSubjects().size(); i++) {
            Log.d(TAG, "item: " + publicPraiseItem);
            if (publicPraiseItem.getSubjects().get(i).getSubject().getImages() == null) {
                publicPraisePicture = PICTURE_PLACE_HOLDER;
            } else {
                publicPraisePicture = publicPraiseItem.getSubjects().get(i).getSubject().getImages().getLarge();
                Log.d(TAG, "directorpicture: " + publicPraisePicture);
            }
            String publicPraiseTitle = publicPraiseItem.getSubjects().get(i).getSubject().getTitle();
            if (publicPraiseItem.getSubjects().get(i).getSubject().getRating() != null) {
                publicPraiseRating = publicPraiseItem.getSubjects().get(i).getSubject().getRating().getAverage();
            } else {
                publicPraiseRating = 0f;
            }
            String publicPraiseId = publicPraiseItem.getSubjects().get(i).getSubject().getId();
            //星级评分
            double fitFilmRate = fitRating(publicPraiseRating);
            Log.d("分数", "合理的分数: " + fitFilmRate);
            String publicPraiseMessage = setPublicPraiseMesssage(publicPraiseItem, i);
            hotMovieInfo = new HotMovieInfo(publicPraisePicture, publicPraiseTitle, publicPraiseRating, publicPraiseId, fitFilmRate,publicPraiseMessage,"","");
            publicPraiseInfos.add(hotMovieInfo);
        }
    }
    /**
     * 排行榜详细信息数据加载(做防空指针操作)
     */
    private String setPublicPraiseMesssage (PublicPraiseItem publicPraiseItem,int i){
        String year, genre1, genre2, director,cast1 = null, cast2 = null;
        //年份
        if(publicPraiseItem.getSubjects().get(i).getSubject().getYear()!=null){
            year=publicPraiseItem.getSubjects().get(i).getSubject().getYear()+"/";
        }else {
            year="";
        }
        //类型
        if (publicPraiseItem.getSubjects().get(i).getSubject().getGenres() == null) {
            genre1 = "";
            genre2 = "";
        } else {
            if (publicPraiseItem.getSubjects().get(i).getSubject().getGenres().size() > 1) {
                genre1 = publicPraiseItem.getSubjects().get(i).getSubject().getGenres().get(0);
                genre2 = " " + publicPraiseItem.getSubjects().get(i).getSubject().getGenres().get(1) + "/";
            } else {
                genre1 = publicPraiseItem.getSubjects().get(i).getSubject().getGenres().get(0);
                genre2 = "/";
            }
        }
        //导演
        if(publicPraiseItem.getSubjects().get(i).getSubject().getDirectors()!=null){
            director=publicPraiseItem.getSubjects().get(i).getSubject().getDirectors().get(0).getName()+"/";
        }else {
            director="";
        }
        //卡司
        if (publicPraiseItem.getSubjects().get(i).getSubject().getCasts() == null) {
            cast1 = "";
            cast2 = "";
        } else {
            if (publicPraiseItem.getSubjects().get(i).getSubject().getCasts().size() > 1) {
                cast1 = publicPraiseItem.getSubjects().get(i).getSubject().getCasts().get(0).getName();
                cast2 = " " + publicPraiseItem.getSubjects().get(i).getSubject().getCasts().get(1).getName();
            } else if (publicPraiseItem.getSubjects().get(i).getSubject().getCasts().size() == 1) {
                cast1 =  publicPraiseItem.getSubjects().get(i).getSubject().getCasts().get(0).getName();
                cast2 = "";
            }
        }
        return year + genre1 + genre2 + director + cast1 + cast2;
    }


    /**
     * 显示标题
     */
    private void showTitle() {
        hotMovieTitle.setVisibility(View.VISIBLE);
        hotMovieTotal.setVisibility(View.VISIBLE);
        comingSoonTitle.setVisibility(View.VISIBLE);
        comingSoonTotal.setVisibility(View.VISIBLE);
        publicPraiseTitle.setVisibility(View.VISIBLE);
        publicPraiseUpdate.setVisibility(View.VISIBLE);
    }

    /**
     * 跳转显示热门和即将上映的全部电影
     */
    private void showTotalMovie() {
        //热门
        hotMovieTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),TotalMoviesActivity.class);
                intent.putExtra("total_url",TOTAL_HOT_MOVIES_URL);
                intent.putExtra("total_title","影院热映");
                intent.putExtra("total_count",Integer.valueOf(hotMovieTotalCount));
                startActivity(intent);
            }
        });
        //即将上映
        comingSoonTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),TotalMoviesActivity.class);
                intent.putExtra("total_url",TOTAL_COMING_SOON_URL);
                intent.putExtra("total_title","即将上映");
                intent.putExtra("total_count",Integer.valueOf(comingSoonTotalCount));
                startActivity(intent);
            }
        });

    }

    /**
     * 热门电影点击事件
     */
    private void HotMovieRecyclerViewOnClickItem() {
        hotMovieRecyclerViewAdapter.setOnHotMovieClickListener(new HotMovieRecyclerViewAdapter.OnHotMovieClickListener() {
            @Override
            public void onClick(String hotMovieId) {
                if(hotMovieId!=null){
                    Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                    intent.putExtra("id", hotMovieId);
                    startActivity(intent);
                }
            }
        });
    }
    /**
     * 即将上映的点击事件
     */
    private void ComingSoonRecyclerViewOnClickItem() {
        comingSoonRecyclerViewAdapter.setOnComingSoonClickListener(new ComingSoonRecyclerViewAdapter.OnComingSoonClickListener() {
            @Override
            public void onClick(String hotMovieId) {
                if(hotMovieId!=null){
                    Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                    intent.putExtra("id", hotMovieId);
                    startActivity(intent);
                }
            }
        });
    }
    /**
     * 口碑榜的点击事件
     */
    private void PublicPraiseRecyclerViewOnClickItem() {
        publicPraiseRecyclerViewAdapter.setOnPublicPraiseClickListener(new PublicPraiseRecyclerViewAdapter.OnpublicPraiseClickListener() {
            @Override
            public void onClick(String hotMovieId) {
                if(hotMovieId!=null){
                    Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                    intent.putExtra("id", hotMovieId);
                    startActivity(intent);
                }
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

    /**
     * 转换时间格式
     * @return 返回短时间字符串格式MM月dd日
     */
    public static String fitDateFormat(String commentDate) {
        //创建SimpleDateFormat对象sdf1,指定日期模式为yyyy-MM-dd
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;//字符串转成date对象类型
        try {
            date = sdf.parse(commentDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat sdf2=new SimpleDateFormat("MM月dd日");
        String str2= sdf2.format(date);//date类型转换成字符串
        return str2;
    }

}
