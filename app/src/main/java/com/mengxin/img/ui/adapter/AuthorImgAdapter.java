package com.mengxin.img.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;

import java.util.ArrayList;

public class AuthorImgAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Img> imgs;

    public AuthorImgAdapter(Context context,ArrayList<Img> imgs) {
        this.context = context;
        this.imgs = imgs;
    }

    public void addImgs(ArrayList<Img> data){
        imgs.clear();
        imgs.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return imgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(context, R.layout.author_item_img,null);
        }
        Img img = imgs.get(position);
        ImageView imageView = convertView.findViewById(R.id.author_img_content);
        Glide.with(context)
                .load(img.getSrc())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(imageView);
        /**
         * 图片点击事件
         */
        imageView.setOnClickListener(v -> {

        });
        return convertView;
    }

}
