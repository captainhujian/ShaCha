package com.example.luowenliang.idouban.moviehot.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;

/**
 * 使bitmap图片高斯模糊
 */
public class ImageFilter {
    //进行图片缩放
    private static final float BITMAP_SCALE=0.4f;

    /**
     * 模糊图片的方法
     * @param context
     * @param bitmap
     * @param blurRadius
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blufBitmap(Context context, Bitmap bitmap, float blurRadius){
//        //计算图片缩小后的长宽
//        int width=Math.round(bitmap.getWidth()*BITMAP_SCALE);
//        int height=Math.round(bitmap.getHeight()*BITMAP_SCALE);
        //创建一张渲染后的输出图片
        Bitmap outputBitmap=Bitmap.createBitmap(bitmap);
        //创建RenderScript内核对象
        RenderScript rs=RenderScript.create(context);
        //创建一个模糊效果的RS的工具对象
        ScriptIntrinsicBlur blurScript=ScriptIntrinsicBlur.create(rs,Element.U8_4(rs));
        Allocation tmpln=Allocation.createFromBitmap(rs,bitmap);
        Allocation tmpOut=Allocation.createFromBitmap(rs,outputBitmap);
        //设置渲染模糊程度，25f是最大模糊程度
        blurScript.setRadius(blurRadius);
        //设置BlurScript对象的输入内存
        blurScript.setInput(tmpln);
        //将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        //将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);
        return  outputBitmap;
    }
}
