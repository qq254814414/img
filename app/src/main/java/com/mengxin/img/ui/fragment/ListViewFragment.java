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
import com.mengxin.img.ui.adapter.ImgListAdapter;

import java.util.ArrayList;

import View.HorizontalListView;

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
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Img img = new Img();
            img.setSrc("http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg");
            mData.add(img);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ImgListAdapter(mData,getActivity());
        list_img.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
