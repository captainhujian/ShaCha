package com.example.luowenliang.idouban.aboutI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.luowenliang.idouban.R;
import com.example.luowenliang.idouban.moviehot.utils.ImageFilter;

public class AboutIFragment extends Fragment {
    private ImageView iBackGround;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about_i,null);
        iBackGround=view.findViewById(R.id.i_backgound);
        iBackGround.setColorFilter(Color.parseColor("#ee141414"));
        Bitmap background = ImageFilter.blufBitmap(getActivity(),BitmapFactory.decodeResource(getResources(),R.drawable.ic_head_portrait),25f);
        iBackGround.setImageBitmap(background);
        return view;
    }
}
