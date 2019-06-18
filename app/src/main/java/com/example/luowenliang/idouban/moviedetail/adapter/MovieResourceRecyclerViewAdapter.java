package com.example.luowenliang.idouban.moviedetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.MovieResourceInfo;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;
import java.util.List;

public class MovieResourceRecyclerViewAdapter extends RecyclerView.Adapter<MovieResourceRecyclerViewAdapter.ViewHolder>  {
    private List<MovieResourceInfo> movieResourceInfos= new ArrayList<>();

    public MovieResourceRecyclerViewAdapter(List<MovieResourceInfo> movieResourceInfos) {
        this.movieResourceInfos = movieResourceInfos;
    }

    @NonNull
    @Override
    public MovieResourceRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sheet_movie_resource_item,viewGroup,false);
        MovieResourceRecyclerViewAdapter.ViewHolder viewHolder=new MovieResourceRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieResourceRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        final String resourcePicture=movieResourceInfos.get(i).getResourceIcon();
        Glide.with(MyApplication.getContext())
                .load(resourcePicture)
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .apply(new RequestOptions().error(R.drawable.placeholder))
                .into(viewHolder.movieResourcePicture);
        viewHolder.movieResourceName.setText(movieResourceInfos.get(i).getResourceName());
        if(movieResourceInfos.get(i).isNeedPay()){
            viewHolder.needPay.setText("VIP免费观看");
        }else {
            viewHolder.needPay.setText("免费观看");
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movieResourceInfos.get(i).getResourceUrl()!=null){
                    listener.onClick(movieResourceInfos.get(i).getResourceUrl(),movieResourceInfos.get(i).getResourceName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieResourceInfos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public NiceImageView movieResourcePicture;
        public TextView movieResourceName;
        public TextView needPay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieResourcePicture = itemView.findViewById(R.id.resource_picture);
            movieResourceName=itemView.findViewById(R.id.resource_name);
            needPay=itemView.findViewById(R.id.need_pay);
        }
    }

    /**
     * item点击事件
     */
    public interface OnMovieResourceClickListener {
        void onClick(String resourceUrl,String resourceName);
    }

    private MovieResourceRecyclerViewAdapter.OnMovieResourceClickListener listener;

    public void setOnMovieResourceClickListener(MovieResourceRecyclerViewAdapter.OnMovieResourceClickListener listener) {
        this.listener = listener;
    }
}
