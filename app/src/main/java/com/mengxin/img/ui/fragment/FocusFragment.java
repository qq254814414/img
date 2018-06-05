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

public class FocusFragment extends Fragment{

    private long id;
    //管理网络请求线程
    private CompositeDisposable mSubscriptions;

    public static FocusFragment newInstance() {
        FocusFragment fragment = new FocusFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.focus,container,false);

        //初始化网络线程管理者
        mSubscriptions = new CompositeDisposable();
        //判断用户是否登录，0没有，1登录。
        id = NetworkUtils.isLogin(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * 网络请求方法格式，返回值是ArrayList<Author>
         *     Author属性有id,headImg,name,imgList
         *     headImg是头像的src
         *     imgList为ArrayList<Img>,每一个Img包含id,src
         *     imgList大小做判断，0-3，显示空白-3张图
         *     操作在onNext函数中写，onNext参数即为网络请求返回值
         *     adapter在ui.adapter中有例子，可以参考
         */
        HttpMethods.getInstance().getFocusList(new Observer<ArrayList<Author>>() {
            private Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                this.disposable = d;
                mSubscriptions.add(d);
            }

            @Override
            public void onNext(ArrayList<Author> authors) {

            }

            @Override
            public void onError(Throwable e) {
                disposable.dispose();
            }

            @Override
            public void onComplete() {

            }
        },id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }
}
