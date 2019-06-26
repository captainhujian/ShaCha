package com.example.luowenliang.idouban.moviehot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviehot.entity.HotMovieInfo;
import com.hedgehog.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class TotalMoviesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<HotMovieInfo> totalMovieInfos=new ArrayList<>();
    //普通布局的type
    static final int TYPE_ITEM = 0;
    //脚布局
    static final int TYPE_FOOTER = 1;

    // 上拉加载更多
    static final int PULL_LOAD_MORE = 0;
    //正在加载更多
    static final int LOADING_MORE = 1;
    //没有更多
    static final int NO_MORE = 2;
    //脚布局当前的状态,默认为没有更多
    int footer_state = 1;

    public TotalMoviesRecyclerViewAdapter(Context context) {
        mContext=context;
    }
    public void setData(List<HotMovieInfo> updatetotalMovieInfos) {
        totalMovieInfos.addAll(updatetotalMovieInfos);
    }


    @NonNull
    @Override
    /**
     * 复用口碑榜的item
     */
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType==TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_praise_item, viewGroup, false);
            TotalMoviesRecyclerViewAdapter.TotalViewHolder viewHolder = new TotalMoviesRecyclerViewAdapter.TotalViewHolder(view);
            return viewHolder;
        }else if(viewType==TYPE_FOOTER){
            //脚布局
            View view = View.inflate(mContext, R.layout.recycler_load_more_layout, null);
            TotalMoviesRecyclerViewAdapter.FootViewHolder footViewHolder = new TotalMoviesRecyclerViewAdapter.FootViewHolder(view);
            return footViewHolder;
        }
        return null;
    }
    @Override
    public int getItemViewType(int position) {
        //如果position加1正好等于所有item的总和,说明是最后一个item,将它设置为脚布局
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof TotalViewHolder) {
            final HotMovieInfo totalMovieInfo = totalMovieInfos.get(i);
            ((TotalViewHolder)viewHolder).totalMovieRank.setText("No." + (i + 1));
            Glide.with(MyApplication.getContext()).load(totalMovieInfo.getHotMoviePicture()).into(((TotalViewHolder)viewHolder).totalMoviePicture);
            ((TotalViewHolder)viewHolder).totalMovieTitle.setText(totalMovieInfo.getHotMovieTitle());
            ((TotalViewHolder)viewHolder).totalMovieMessage.setText(totalMovieInfo.getHotMovieMessage());
            if (totalMovieInfo.getFitMovieRate() == 0f) {
                ((TotalViewHolder)viewHolder).totalMovieRatingBar.setVisibility(View.GONE);
                ((TotalViewHolder)viewHolder).totalMovieRating.setText("暂无评分");
            } else {
                //必须要有这句代码，不然会出现滑动后非0分电影星级条消失的情况
                ((TotalViewHolder)viewHolder).totalMovieRatingBar.setVisibility(View.VISIBLE);
                ((TotalViewHolder)viewHolder).totalMovieRating.setText(String.valueOf(totalMovieInfo.getHotMovieRating()));
                ((TotalViewHolder)viewHolder).totalMovieRatingBar.setStarCount(5);
                ((TotalViewHolder)viewHolder).totalMovieRatingBar.setStar((float) totalMovieInfo.getFitMovieRate());
            }
            //大陆上映时间
            ((TotalViewHolder)viewHolder).totalPubDateCard.setVisibility(View.VISIBLE);
            ((TotalViewHolder)viewHolder).totalPubDate.setText(totalMovieInfo.getHotMovieUpDate());
            //点击事件
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(totalMovieInfo.getHotMovieId());
                }
            });
        }else if(viewHolder instanceof TotalMoviesRecyclerViewAdapter.FootViewHolder){
            TotalMoviesRecyclerViewAdapter.FootViewHolder footViewHolder = (TotalMoviesRecyclerViewAdapter.FootViewHolder)viewHolder;
            if(i==0){
                Log.d("脚布局", "隐藏脚布局");
                //如果第一个就是脚布局,,那就让他隐藏
                footViewHolder.mProgressBar.setVisibility(View.GONE);
                Log.d("脚布局", "进度条隐藏");
                footViewHolder.tv_line1.setVisibility(View.GONE);
                footViewHolder.tv_line2.setVisibility(View.GONE);
                footViewHolder.tv_state.setText("");
            }else {
                switch (footer_state){
                    //根据状态来让脚布局发生改变
                    case PULL_LOAD_MORE://上拉加载
                        Log.d("脚布局", "上拉加载更多");
                        footViewHolder.mProgressBar.setVisibility(View.GONE);
                        footViewHolder.tv_state.setText("上拉加载更多");
                        break;
                    case LOADING_MORE:
                        Log.d("脚布局", "正在加载");
                        footViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                        footViewHolder.tv_line1.setVisibility(View.GONE);
                        footViewHolder.tv_line2.setVisibility(View.GONE);
                        footViewHolder.tv_state.setText("正在加载...");
                        break;
                    case NO_MORE:
                        Log.d("脚布局", "我是有底线的");
                        footViewHolder.mProgressBar.setVisibility(View.GONE);
                        footViewHolder.tv_line1.setVisibility(View.VISIBLE);
                        footViewHolder.tv_line2.setVisibility(View.VISIBLE);
                        footViewHolder.tv_state.setText("我是有底线的");
                        footViewHolder.tv_state.setTextColor(Color.parseColor("#CD853F"));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return totalMovieInfos != null ? totalMovieInfos.size() + 1 : 0;
    }

    /**
     * 普通布局的viewholder
     */
    public class TotalViewHolder extends RecyclerView.ViewHolder{
        public TextView totalMovieRank;
        public ImageView totalMoviePicture;
        public TextView totalMovieTitle;
        public TextView totalMovieRating;
        public TextView totalMovieMessage;
        public RatingBar totalMovieRatingBar;
        public CardView totalPubDateCard;
        public TextView totalPubDate;
        public TotalViewHolder(@NonNull View itemView) {
            super(itemView);
            totalMovieRank=itemView.findViewById(R.id.praise_rank);
            totalMoviePicture = itemView.findViewById(R.id.praise_image);
            totalMovieTitle=itemView.findViewById(R.id.praise_title);
            totalMovieRating=itemView.findViewById(R.id.praise_rating);
            totalMovieMessage=itemView.findViewById(R.id.praise_message);
            totalMovieRatingBar=itemView.findViewById(R.id.praise_rating_bar);
            totalMovieRatingBar.setmClickable(false);
            totalPubDateCard=itemView.findViewById(R.id.praise_pubdates_card);
            totalPubDate=itemView.findViewById(R.id.praise_pubdates);
            //适配android 9.0的行间距
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O_MR1) {
                totalMovieMessage.setLineSpacing(1.1f,1f);
            }
        }
    }
    /**
     * 脚布局的ViewHolder
     */
    public class FootViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;
        private TextView tv_state;
        private TextView tv_line1;
        private TextView tv_line2;


        public FootViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            tv_state = (TextView) itemView.findViewById(R.id.foot_view_item_tv);
            tv_line1 = (TextView) itemView.findViewById(R.id.tv_line1);
            tv_line2 = (TextView) itemView.findViewById(R.id.tv_line2);

        }

    }
    /**
     * 改变脚布局的状态的方法,在activity根据请求数据的状态来改变这个状态
     *
     * @param state
     */
    public void changeState(int state) {
        this.footer_state = state;
        notifyDataSetChanged();
    }
    /**
     * 影视的点击事件
     */
    public interface OnTotalMovieClickListener{
        void onClick(String movieId);
    }
    public TotalMoviesRecyclerViewAdapter.OnTotalMovieClickListener listener;
    public void setOnTotalMovieClickListener(TotalMoviesRecyclerViewAdapter.OnTotalMovieClickListener listener){
        this.listener=listener;
    }
}
