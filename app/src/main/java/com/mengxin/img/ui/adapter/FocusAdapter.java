package com.mengxin.img.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.ui.activity.AuthorActivity;
import com.mengxin.img.ui.activity.PictureDetailActivity;

import java.util.ArrayList;

public class FocusAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Author> authors;

    public FocusAdapter(Context context, ArrayList<Author> authors) {
        this.context = context;
        this.authors = authors;
    }

    @Override
    public int getCount() {
        return authors.size();
    }

    @Override
    public Object getItem(int position) {
        return authors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Author author = authors.get(position);
        ArrayList<Img> imgList = author.getImgList();
        int num = imgList.size();
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.focus_item,null);
        }
        RoundedImageView header = convertView.findViewById(R.id.iv_header_focus);
        TextView authorName = convertView.findViewById(R.id.tv_author_name);
        ImageView img1 = convertView.findViewById(R.id.iv_img1);
        ImageView img2 = convertView.findViewById(R.id.iv_img2);
        ImageView img3 = convertView.findViewById(R.id.iv_img3);
        ArrayList<ImageView> imageViews = new ArrayList<>(3);
        imageViews.add(img1);
        imageViews.add(img2);
        imageViews.add(img3);
        for (int i = 0; i < num; i++){
            ImageView view = imageViews.get(i);
            Img img = imgList.get(i);
            view.setClickable(true);
            Glide.with(context).asBitmap().load(img.getSrc()).into(view);
            view.setOnClickListener(v -> {
                Intent intent = new Intent(context, PictureDetailActivity.class);
                intent.putExtra("img_id",img.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            });
        }
        Glide.with(context).asBitmap().load(author.getHeadImg()).into(header);
        header.setOnClickListener(v -> {
            Intent intent = new Intent(context, AuthorActivity.class);
            intent.putExtra("authorId",author.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        });
        authorName.setText(author.getName());
        return convertView;
    }
}
