package com.mengxin.img.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.adapter.TopListAdapter;
import com.mengxin.img.utils.TimeUtils;
import com.mengxin.img.utils.ToastUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TopImgListFragment extends Fragment {

    public static TopImgListFragment newInstance(){
        return new TopImgListFragment();
    }

    private SwipeRefreshLayout srl_refresh;
    private TopListAdapter mAdapter;
    private ListView rec_mz;
    private ArrayList<Img> mData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_list,container,false);

        srl_refresh = view.findViewById(R.id.srl_refresh);
        rec_mz = view.findViewById(R.id.lv_top);

        srl_refresh.setOnRefreshListener(() -> {
            fetchTop();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchTop();
        mData = new ArrayList<Img>();
        mAdapter = new TopListAdapter(getActivity(),mData);
        srl_refresh.setRefreshing(true);
        rec_mz.setAdapter(mAdapter);

    }

    private void fetchTop(){
        HttpMethods.getInstance().get10RankingList(new Observer<ArrayList<Img>>() {
            @Override
            public void onSubscribe(Disposable d) {
                srl_refresh.setRefreshing(true);
            }

            @Override
            public void onNext(ArrayList<Img> imgs) {
                mAdapter.addImgs(imgs);
                if (imgs.isEmpty()){
                    ToastUtils.shortToast("没有更多啦");
                }
                srl_refresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.shortToast("网络异常，请重试。");
                srl_refresh.setRefreshing(false);
            }

            @Override
            public void onComplete() {

            }
        }, TimeUtils.getTime(),TimeUtils.getTomorrowTime());
    }

}
