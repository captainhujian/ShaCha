package com.example.luowenliang.idouban.moviedetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.moviedetail.adapter.MyImageAdapter;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;

import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private PhotoViewPager mViewPager;
    private int currentPosition;
    private List<StagePhotoInfo>stagePictureUrls;
    private TextView mTvImageCount;
    private MyImageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 状态栏透明
         */
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.view_pager_avtivity);
        initView();
        initData();
    }

    private void initView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.stage_photo_view);
        mTvImageCount = (TextView) findViewById(R.id.tv_image_count);
    }

    private void initData() {
        Intent intent = getIntent();
        currentPosition=intent.getIntExtra("currentPosition",0);
        stagePictureUrls= (List<StagePhotoInfo>) intent.getSerializableExtra("stagePhotoInfos");
        adapter = new MyImageAdapter(stagePictureUrls,this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition,false);
        mTvImageCount.setText(currentPosition+1 + "/" + stagePictureUrls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition=position;
                mTvImageCount.setText(currentPosition + 1 + "/" + stagePictureUrls.size());

            }
        });

    }

}
