package com.mengxin.img.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.ui.activity.PictureDetailActivity;

import java.util.ArrayList;

public class IllustrationsAdapter extends RecyclerView.Adapter<IllustrationsAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Img> imgs;

    public IllustrationsAdapter(Context mContext, ArrayList<Img> imgs) {
        this.mContext = mContext;
        this.imgs = imgs;
    }

    public void addAll(ArrayList<Img> data){
        imgs.clear();
        imgs.addAll(data);
        notifyDataSetChanged();
    }

    public void loadMore(ArrayList<Img> data){
        imgs.addAll(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false));
    }

    @Override
    public void onBindViewHolder(IllustrationsAdapter.ViewHolder holder, int position) {
        holder.bind(imgs.get(position));
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_content;

        ViewHolder(View itemView) {
            super(itemView);
            img_content = itemView.findViewById(R.id.img_content);
        }

        void bind(Img data) {
            Glide.with(mContext)
                    .load(data.getSrc())
                    .apply(new RequestOptions()
                            .centerCrop())
                    .into(img_content);
            //图片点击事件
            img_content.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, PictureDetailActivity.class);
//                intent.putExtra("pic_url", data.getSrc());
                mContext.startActivity(intent);
            });
        }
    }

}
