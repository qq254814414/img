package com.mengxin.img.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.adapter.ImgListAdapter;
import com.mengxin.img.utils.TimeUtils;

import java.util.ArrayList;

import View.HorizontalListView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ListViewFragment extends Fragment{

    private ArrayList<Img> mData;
    private HorizontalListView list_img;
    private ImgListAdapter adapter;

    public static ListViewFragment newInstance(){return new ListViewFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_top_list,container,false);
        list_img = view.findViewById(R.id.hlv_top);

        initData();

        return view;
    }

    private void initData() {
        HttpMethods.getInstance().get10RankingList(new Observer<ArrayList<Img>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList<Img> imgs) {
                mData = imgs;
                adapter = new ImgListAdapter(mData,getActivity());
                list_img.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, TimeUtils.getTime(),TimeUtils.getTomorrowTime());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
