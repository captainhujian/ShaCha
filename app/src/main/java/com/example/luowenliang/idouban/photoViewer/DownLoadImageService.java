package com.example.luowenliang.idouban.photoViewer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.luowenliang.idouban.application.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Glide是实现图片下载到相册
 */
public class DownLoadImageService implements Runnable {
    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private File currentFile;

    public DownLoadImageService(String url, Context context, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        File file =null;
        try {
            file=Glide.with(context)
                    .load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (file != null){
                // 在这里执行图片保存方法
                saveImageToGallery(context,file);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(file!=null){
                callBack.onDownLoadSuccess(file);
            }else {
                callBack.onDownLoadFailed();
            }
        }
    }


    public void saveImageToGallery(Context context,File file){
        //首先保存图片
        Log.d("保存", "saveImageToGallery: ");
        File pictureFolder =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        String folderName="沙茶";
        File appDir=new File(pictureFolder,folderName);
        if(!appDir.exists()){
            appDir.mkdirs();
            Log.d("保存", "建立文件夹 "+appDir.mkdirs());
        }
        String fileName=System.currentTimeMillis()+".jpg";
        currentFile=new File(appDir,fileName);
        copy(file,currentFile);
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));

    }

    /**
     * 监听下载情况
     */
    public interface ImageDownLoadCallBack {
        void onDownLoadSuccess(File file);
        void onDownLoadSuccess(Bitmap bitmap);

        void onDownLoadFailed();
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
