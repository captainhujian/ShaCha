package com.example.luowenliang.idouban;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.luowenliang.idouban.book.BooksFragment;
import com.example.luowenliang.idouban.moviehot.HotMoviesFragment;
import com.example.luowenliang.idouban.movietop250.Top250MoviesFragment;
import com.example.luowenliang.idouban.photoViewer.DoubanPagerAdapter;

public class MainActivity extends BaseActivity {
    private static final String TAG = "豆瓣";
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setSlideable(isActivitySlideBack());
        setContentView(LayoutInflater.from(this).inflate(R.layout.activity_main,null,false));
        mViewPager = findViewById(R.id.douban_view_pager);
        tabLayout = findViewById(R.id.douban_sliding_tabs);
        setupViewPager(mViewPager);
        if (tabLayout != null) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.setupWithViewPager(mViewPager);
            Log.d(TAG, "onCreate: ");
        }
    }

    /**
     * 是否开启左侧滑动退出
     * @return
     */
    @Override
    public boolean isActivitySlideBack() {
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        DoubanPagerAdapter pagerAdapter = new DoubanPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new Top250MoviesFragment(), getApplicationContext().getResources().getString(R.string.top250_movies_title));
        pagerAdapter.addFragment(new HotMoviesFragment(),getApplicationContext().getResources().getString(R.string.hot_movies_title));
        pagerAdapter.addFragment(new BooksFragment(), getApplicationContext().getResources().getString(R.string.books_title));
        //设置缓存fragment页面数
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pagerAdapter);
    }
}
