package com.example.luowenliang.idouban;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "罗";
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.douban_view_pager);
        tabLayout = findViewById(R.id.douban_sliding_tabs);
        setupViewPager(mViewPager);
        if (tabLayout != null) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.setupWithViewPager(mViewPager);
            Log.d(TAG, "onCreate: ");
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        DoubanPagerAdapter pagerAdapter = new DoubanPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new MoviesFragment(), getApplicationContext().getResources().getString(R.string.movies_title));
        pagerAdapter.addFragment(new BooksFragment(), getApplicationContext().getResources().getString(R.string.books_title));
        viewPager.setAdapter(pagerAdapter);
    }
}
