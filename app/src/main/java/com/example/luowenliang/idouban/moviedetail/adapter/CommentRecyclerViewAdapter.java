package com.example.luowenliang.idouban.moviedetail.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.application.MyApplication;
import com.example.luowenliang.idouban.moviedetail.entity.CommentInfo;
import com.hedgehog.ratingbar.RatingBar;
import com.shehuan.niv.NiceImageView;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {
    private List<CommentInfo>commentInfos=new ArrayList<>();
    private int textColor;

    public CommentRecyclerViewAdapter(List<CommentInfo> commentInfos, int textColor) {
        this.commentInfos = commentInfos;
        this.textColor = textColor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item,viewGroup,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CommentInfo commentInfo=commentInfos.get(i);
        Glide.with(MyApplication.getContext()).load(commentInfo.getCommenterPisture()).into(viewHolder.conmentePicture);
        viewHolder.commenterName.setText(commentInfo.getCommenterName());
        viewHolder.commentTime.setText(fitDateFormat(commentInfo.getCommentTime()));
        viewHolder.usefulCount.setText(fitUsefulCount(commentInfo.getUsefulCount()));
        //适配android 9.0的行间距
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O_MR1) {
            viewHolder.comment.setLineSpacing(1.1f,1f);
        }
        viewHolder.comment.setText(commentInfo.getComment());
        //星级评分
        double fitRate = commentInfo.getCommenterRating();
            viewHolder.commenterRatingBar.setStarCount(5);
            viewHolder.commenterRatingBar.setStar((float) fitRate);
    }

    @Override
    public int getItemCount() {
        return commentInfos.size();
    }
     public class ViewHolder extends RecyclerView.ViewHolder{
        public NiceImageView conmentePicture;
        public TextView commenterName;
        public RatingBar commenterRatingBar;
        public TextView commentTime;
        public TextView comment;
        public TextView usefulCount;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
            conmentePicture= itemView.findViewById(R.id.commenter_picture);
            commenterName=itemView.findViewById(R.id.commenter_name);
            commenterRatingBar=itemView.findViewById(R.id.commenter_rating_bar);
            commenterRatingBar.setmClickable(false);
            commentTime=itemView.findViewById(R.id.commenter_time);
            comment=itemView.findViewById(R.id.comment);
            usefulCount=itemView.findViewById(R.id.useful_count);
            //设置文字颜色
            commenterName.setTextColor(textColor);
            commentTime.setTextColor(textColor);
            comment.setTextColor(textColor);
            usefulCount.setTextColor(textColor);
         }
     }

    /**
     * 转换时间格式
     * @return 返回短时间字符串格式yyyy年MM月dd日
     */
    public String fitDateFormat(String commentDate) {
       //创建SimpleDateFormat对象sdf1,指定日期模式为yyyy-MM-dd
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;//字符串转成date对象类型
        try {
            date = sdf.parse(commentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat sdf2=new SimpleDateFormat("yyyy年MM月dd日");
        String str2= sdf2.format(date);//date类型转换成字符串
        return str2;
    }

    /**
     * 评论点赞个数格式转换
     * 1000以下不变，1000以上转1.0k
     * 防止先转换后接收数据
     * @param usefulCount
     * @return
     */
    public String fitUsefulCount(int usefulCount){
        String count=null;
            if(usefulCount>=1000){
                double tempCount = div((double)usefulCount,1000.0,1);
                DecimalFormat df=new DecimalFormat("赞 0.0k");
                count = df.format(tempCount);
            }else {
                count="赞 "+usefulCount;
            }
        return count;
    }
    /**保留一位小数*/
    public static double div(double v1,double v2,int scale){
        BigDecimal b1=new BigDecimal(Double.toString(v1));
        BigDecimal b2=new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
