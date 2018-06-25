package com.mengxin.img.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.ui.activity.PictureDetailActivity;

import java.util.ArrayList;

public class TopListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Img> imgs;

    public TopListAdapter(Context context, ArrayList<Img> imgs) {
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
            convertView = View.inflate(context, R.layout.top_item,null);
        }
        Img img = imgs.get(position);
        ImageView imgView = convertView.findViewById(R.id.iv_top_item);
        Glide.with(context)
                .load(img.getSrc())
                .apply(new RequestOptions()
                        .centerCrop())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (imgView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imgView.getLayoutParams();
                        int viewWidth = imgView.getWidth();
                        float scale = (float) viewWidth / (float) resource.getIntrinsicWidth();
                        int viewHeight = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = viewHeight;

                        imgView.setLayoutParams(params);

                        return false;
                    }
                })
                .into(imgView);
        /**
         * 图片点击事件
         */
        imgView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PictureDetailActivity.class);
            intent.putExtra("img_id",img.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        });
        return convertView;
    }

}
