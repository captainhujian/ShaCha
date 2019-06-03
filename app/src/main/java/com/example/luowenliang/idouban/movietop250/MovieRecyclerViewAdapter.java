package com.example.luowenliang.idouban.movietop250;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.movietop250.entity.Top250Movie;

import java.math.BigDecimal;
import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Top250Movie> top250MovieList;
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

    public MovieRecyclerViewAdapter(Context context) {
        mContext=context;
    }
    public void setData(List<Top250Movie> top250MovieList) {
        this.top250MovieList = top250MovieList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       if(viewType==TYPE_ITEM){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
           return myViewHolder;
       }else if(viewType==TYPE_FOOTER){
           //脚布局
           View view = View.inflate(mContext, R.layout.recycler_load_more_layout, null);
           FootViewHolder footViewHolder = new FootViewHolder(view);
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
        if(viewHolder instanceof MyViewHolder){
            final Top250Movie top250Movie = top250MovieList.get(i);
            Glide.with(MyApplication.getContext()).load(top250Movie.getImage()).into(((MyViewHolder)viewHolder).image);
            ((MyViewHolder)viewHolder).durations.setText(top250Movie.getDurations());
            ((MyViewHolder)viewHolder).year.setText(top250Movie.getYear());
            ((MyViewHolder)viewHolder).genres.setText(top250Movie.getGenres1()+top250Movie.getGenres2());
            ((MyViewHolder)viewHolder).original_title.setText(top250Movie.getOriginal_title());
            ((MyViewHolder)viewHolder).title.setText(top250Movie.getTitle());
            ((MyViewHolder)viewHolder).director_cast.setText(top250Movie.getDirector() + "/" + top250Movie.getCast1()+top250Movie.getCast2());
            ((MyViewHolder)viewHolder).number.setText("No." + (i + 1));
            Log.d("分数", "名字: "+top250Movie.getTitle());
            //星级评分
            double fitRate = fitRating(top250Movie.getRating());
            Log.d("分数", "合理的分数: "+fitRate);
            if(fitRate==0f){
                ((MyViewHolder)viewHolder).ratingBar.setVisibility(View.GONE);
                ((MyViewHolder)viewHolder).rating.setText("暂无评分");
            }else {
                //必须要有这句代码，不然会出现滑动后非0分电影星级条消失的情况
                ((MyViewHolder)viewHolder).ratingBar.setVisibility(View.VISIBLE);
                ((MyViewHolder)viewHolder).rating.setText(String.valueOf(top250Movie.getRating()));
                ((MyViewHolder)viewHolder).ratingBar.setStarCount(5);
                ((MyViewHolder)viewHolder).ratingBar.setStar((float) fitRate);
            }
            //点击事件
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(top250Movie);
                    }
                }
            });
        }else if(viewHolder instanceof FootViewHolder ){
            FootViewHolder footViewHolder = (FootViewHolder)viewHolder;
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

    /**
     * 普通布局
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView original_title;
        TextView rating;
        TextView genres;
        TextView durations;
        TextView year;
        TextView number;
        TextView director_cast;
        com.hedgehog.ratingbar.RatingBar ratingBar;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            original_title = itemView.findViewById(R.id.origin_title);
            rating = itemView.findViewById(R.id.rating);
            genres = itemView.findViewById(R.id.genres);
            durations = itemView.findViewById(R.id.durations);
            year = itemView.findViewById(R.id.year);
            director_cast = itemView.findViewById(R.id.director_cast);
            number = itemView.findViewById(R.id.number);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            ratingBar.setmClickable(false);
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
    public void updateList(List<Top250Movie> newDatas) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            top250MovieList.addAll(newDatas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return top250MovieList != null ? top250MovieList.size() + 1 : 0;
    }

    /**
     * item点击事件
     */
    public interface OnItemClickListener {
        void onClick(Top250Movie top250Movie);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 转换合理的评分以星级显示
     */
    public double fitRating(double rating) {
        double ratingIn5 = 0;
        if(rating>=9.2f){
                ratingIn5 = 5f;
        }else if(rating>=2f){
            BigDecimal tempRating = new BigDecimal(rating).setScale(0, BigDecimal.ROUND_HALF_UP);
            ratingIn5=tempRating.doubleValue()/2f;
        }else {
            if(rating==0f){
                ratingIn5=0f;
            }else {
                ratingIn5=1f;
            }

        }

    return ratingIn5;
    }

}
