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
import android.widget.ListView;
import android.widget.TextView;

import com.mengxin.img.R;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.activity.MainActivity;
import com.mengxin.img.ui.adapter.FocusAdapter;
import com.mengxin.img.utils.NetworkUtils;
import com.mengxin.img.utils.PackageUtils;
import com.mengxin.img.utils.ResUtils;
import com.mengxin.img.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

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
    private Long authorId;
    private String type;
    private CompositeDisposable mSubscriptions;

    private FocusAdapter adapter;

    private ListView lvFocusList;

    public static FocusFragment newInstance() {
        FocusFragment fragment = new FocusFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.focus,container,false);
        lvFocusList = view.findViewById(R.id.lv_focusList);
        Bundle bundle = getArguments();
        authorId = bundle.getLong("authorId");
        type = bundle.getString("type");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubscriptions = new CompositeDisposable();

        if (type.equals("关注列表")){
            HttpMethods.getInstance().getFocusList(new Observer<ArrayList<Author>>() {
                private Disposable d;
                @Override
                public void onSubscribe(Disposable d) {
                    this.d=d;
                    mSubscriptions.add(d);
                }

                @Override
                public void onNext(ArrayList<Author> authors) {
                    adapter = new FocusAdapter(getActivity(),authors);
                    lvFocusList.setAdapter(adapter);
                }

                @Override
                public void onError(Throwable e) {
                    d.dispose();
                }

                @Override
                public void onComplete() {

                }
            },authorId);
        } else {
            HttpMethods.getInstance().getFansList(new Observer<ArrayList<Author>>() {
                private Disposable d;
                @Override
                public void onSubscribe(Disposable d) {
                    this.d=d;
                    mSubscriptions.add(d);
                }

                @Override
                public void onNext(ArrayList<Author> authors) {
                    adapter = new FocusAdapter(getActivity(),authors);
                    lvFocusList.setAdapter(adapter);
                }

                @Override
                public void onError(Throwable e) {
                    d.dispose();
                }

                @Override
                public void onComplete() {

                }
            },authorId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }
}
