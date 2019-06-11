package com.example.luowenliang.idouban.photoViewer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luowenliang.idouban.BaseActivity;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.StagePhotoInfo;
import com.example.luowenliang.idouban.moviedetail.utils.PhotoViewPager;

import java.io.File;
import java.util.List;

public class ViewPagerActivity extends BaseActivity {
    private PhotoViewPager mViewPager;
    private int currentPosition;
    private List<StagePhotoInfo>stagePictureUrls;
    private TextView mTvImageCount;
    private MyImageAdapter adapter;
    private DownLoadImageService service;
    //长按后显示的 Item
    final String[] items = new String[] {"保存图片"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_avtivity);
        //设置左滑退出
        setSlideable(isActivitySlideBack());
        initView();
        initData();
    }

    @Override
    public boolean isActivitySlideBack() {
        return true;
    }

    private void initView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.stage_photo_view);
        mTvImageCount = (TextView) findViewById(R.id.tv_image_count);
    }

    private void initData() {
        Intent intent = getIntent();
        currentPosition=intent.getIntExtra("currentPosition",0);
        stagePictureUrls= (List<StagePhotoInfo>) intent.getSerializableExtra("photo");
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
        imageLongClickSave();
    }

    /**
     * 长按图片保存
     */
    private void imageLongClickSave() {
        adapter.setOnImageLongClickListener(new MyImageAdapter.OnImageLongClickListener() {
            @Override
            public void onLongClick(int position, final String stagePhotoUrl) {
                Log.d("下载", "urls："+stagePhotoUrl);
                Log.d("下载", "第"+position+"个");
                //弹出的“保存图片”的Dialog,不可用全局上下文
               final AlertDialog.Builder builder = new AlertDialog.Builder(ViewPagerActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch(i) {
                            case 0:
                                Log.d("下载", "image: "+stagePhotoUrl);
                                service=new DownLoadImageService(stagePhotoUrl, MyApplication.getContext(),
                              new DownLoadImageService.ImageDownLoadCallBack() {
                                  @Override
                                  public void onDownLoadSuccess(File file) {
                                      Log.d("下载", "onDownLoadSuccess: ");
                                      try {
                                          Toast toast = null;
                                          toast= Toast.makeText(MyApplication.getContext(),"图片已保存",Toast.LENGTH_SHORT);
                                          toast.show();
                                      } catch (Exception e) {
                                          //解决在子线程中调用Toast的异常情况处理
                                          Looper.prepare();
                                          Toast.makeText(MyApplication.getContext(),"图片已保存",Toast.LENGTH_SHORT).show();
                                          Looper.loop();
                                      }
                                  }

                                  @Override
                                  public void onDownLoadSuccess(Bitmap bitmap) {

                                  }

                                  @Override
                                  public void onDownLoadFailed() {
                                      Log.d("下载", "onDownLoadFailed: ");
                                      //Toast.makeText(MyApplication.getContext(),"下载失败",Toast.LENGTH_SHORT).show();
                                  }
                              });
                        //启动图片下载线程
                        new Thread(service).start();
                                break;
                            default:
                                break;
                        }
//
                    }
                }).create().show();
                Log.d("保存", "444444444444444");
            }
        });
    }

}
