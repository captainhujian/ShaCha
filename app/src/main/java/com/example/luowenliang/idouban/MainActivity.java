package com.example.luowenliang.idouban;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.luowenliang.idouban.AboutI.AboutIFragment;
import com.example.luowenliang.idouban.moviehot.HotMoviesFragment;
import com.example.luowenliang.idouban.movietop250.Top250MoviesFragment;

/**
 * author: Nick
 * start_date:2019/5/24
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "沙茶";
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return false;
    }

    private void setupViewPager(ViewPager viewPager) {
        ShaChaPagerAdapter pagerAdapter = new ShaChaPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new HotMoviesFragment(),getApplicationContext().getResources().getString(R.string.hot_movies_title));
        pagerAdapter.addFragment(new Top250MoviesFragment(), getApplicationContext().getResources().getString(R.string.top250_movies_title));
        pagerAdapter.addFragment(new AboutIFragment(), getApplicationContext().getResources().getString(R.string.about_i_title));
        //设置缓存fragment页面数
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pagerAdapter);
    }
}
