package com.mengxin.img.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mengxin.img.R;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.activity.MainActivity;
import com.mengxin.img.utils.NetworkUtils;
import com.mengxin.img.utils.PackageUtils;
import com.mengxin.img.utils.ResUtils;
import com.mengxin.img.utils.ToastUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *     网络请求返回值是ArrayList<Author>
 *     Author属性有id,headImg,name,imgList
 *     headImg是头像的src
 *     imgList为ArrayList<Img>,每一个Img包含id,src
 *     imgList大小做判断，0-3，显示空白-3张图
 *     adapter在ui.adapter中有例子，可以参考AuthorImgAdapter
 */

public class FocusFragment extends Fragment{
    /**
     * 用于模拟的数据
     * 包含两个用户，一个三张图，一个没有图。
     */
    private ArrayList<Author> authors;
    public static FocusFragment newInstance() {
        FocusFragment fragment = new FocusFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.focus,container,false);
        //初始化模拟数据
        initData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        authors = new ArrayList<>();
        ArrayList<Img> imgs = new ArrayList<>();
        Img img = new Img();
        img.setId(1L);
        img.setSrc("http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg");
        Img img2 = new Img();
        img2.setId(1L);
        img2.setSrc("http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg");
        Img img3 = new Img();
        img3.setId(1L);
        img3.setSrc("http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg");
        imgs.add(img);
        imgs.add(img2);
        imgs.add(img3);
        Author author1 = new Author();
        author1.setHeadImg("http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg");
        author1.setId(1L);
        author1.setImgList(imgs);
        Author author2 = new Author();
        author2.setHeadImg("http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg");
        author2.setId(2L);
        author2.setImgList(new ArrayList<>());
    }
}
