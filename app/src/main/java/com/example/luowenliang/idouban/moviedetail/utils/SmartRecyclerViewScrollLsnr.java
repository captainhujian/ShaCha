package com.example.luowenliang.idouban.moviedetail.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;

/**
 ** 快速滑动时候，停止加载图片, 加载更多
 */
public abstract class SmartRecyclerViewScrollLsnr extends RecyclerView.OnScrollListener{

        private static final String TAG = "SmartRecyclerViewScroll";
        private Context mContext;
        private int previousFirstVisibleItem = 0;
        private long previousEventTime = 0;
        private double speed = 0;
        private LinearLayoutManager mLinearLayoutManager;

        public SmartRecyclerViewScrollLsnr(Context context) {
            mContext = context;
        }


        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//            resumeImageLoad();
//        }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            if (!recyclerView.canScrollVertically(1)){//不能再向上,滑动到底部
                resumeImageLoad();
                onLoadMore();
                return;
            }

            if (!recyclerView.canScrollVertically(-1)){//不能再向下
                resumeImageLoad();
                return;
            }

            //速度降低时候，也加载
            if (mLinearLayoutManager==null){
                mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            }
            int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
            if (previousFirstVisibleItem != firstVisibleItem) {
                long currTime = System.currentTimeMillis();
                long timeToScrollOneElement = currTime - previousEventTime;
                speed = ((double) 1 / timeToScrollOneElement) * 1000;
                previousFirstVisibleItem = firstVisibleItem;
                previousEventTime = currTime;
                if (speed > 6) {
                    pauseImageLoad();
                } else {
                    resumeImageLoad();
                }
                Log.d(TAG, "Speed: " + speed + " elements/second");
            }
        }

        private void pauseImageLoad() {
            try {
                if (mContext != null) Glide.with(mContext).pauseRequests();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void resumeImageLoad() {
            try {
                if (mContext != null && Glide.with(mContext).isPaused()) Glide.with(mContext).resumeRequests();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /**
         * 提供一个抽闲方法，在Activity中监听到这个EndLessOnScrollListener
         * 并且实现这个方法
         * */
        public abstract void onLoadMore();
    }
