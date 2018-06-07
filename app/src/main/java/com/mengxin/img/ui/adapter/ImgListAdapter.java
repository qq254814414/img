package com.mengxin.img.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;

import java.util.ArrayList;

public class ImgListAdapter extends BaseAdapter{

private ArrayList<Img> mData;
private Context mContext;

public ImgListAdapter(ArrayList<Img> mData,Context mContext){
    this.mData = mData;
    this.mContext = mContext;
}

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(mContext,R.layout.item_top_list_img,null);
        }
        ImageView img_bg = convertView.findViewById(R.id.img_bg);
        Glide.with(mContext)
                .load(mData.get(position).getSrc())
                .apply(new RequestOptions().centerCrop())
                .into(img_bg);
        return convertView;
    }
}
