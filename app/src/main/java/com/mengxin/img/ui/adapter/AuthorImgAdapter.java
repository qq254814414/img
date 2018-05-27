package com.mengxin.img.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;

import java.util.ArrayList;

public class AuthorImgAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Img> imgs;

    public AuthorImgAdapter(Context context) {
        this.context = context;
    }

    public void addImgs(ArrayList<Img> imgs){
        imgs.clear();
        imgs.addAll(imgs);
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
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.author_item_img,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Img img = imgs.get(position);
        Glide.with(context)
                .load(img.getSrc())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(viewHolder.item_img);

        return null;
    }

    class ViewHolder {
        private ImageView item_img;
        public ViewHolder(View convertView){
            item_img = convertView.findViewById(R.id.author_img_content);
        }
    }

}
