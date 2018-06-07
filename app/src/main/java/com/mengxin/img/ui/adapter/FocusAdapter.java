package com.mengxin.img.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FocusAdapter extends BaseAdapter{
    private Long authorId;
    private Context context;
    private ArrayList<Author> authors;
    private CompositeDisposable mSubscriptions;

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
        Author author=authors.get(position);
        ArrayList<Img> imgList=author.getImgList();
        int num=imgList.size();
        if(num>=3){
            if(null==convertView){
                convertView= LayoutInflater.from(context).inflate(R.layout.focus_item,null);
            }
            ImageView img1=convertView.findViewById(R.id.iv_img1);
            ImageView img2=convertView.findViewById(R.id.iv_img2);
            ImageView img3=convertView.findViewById(R.id.iv_img3);
            RoundedImageView header=convertView.findViewById(R.id.iv_header_focus);
            TextView authorName=convertView.findViewById(R.id.tv_author_name);
            LinearLayout ll=convertView.findViewById(R.id.ll_imgs_focus);

            Glide.with(context).asBitmap().load(imgList.get(num-1).getSrc()).into(img1);
            Glide.with(context).asBitmap().load(imgList.get(num-2).getSrc()).into(img2);
            Glide.with(context).asBitmap().load(imgList.get(num-3).getSrc()).into(img3);
            Glide.with(context).asBitmap().load(author.getHeadImg()).into(header);
            authorName.setText(author.getName());
        }else if(num==2){
            if(null==convertView){
                convertView= LayoutInflater.from(context).inflate(R.layout.focus_item,null);
            }
            ImageView img1=convertView.findViewById(R.id.iv_img1);
            ImageView img2=convertView.findViewById(R.id.iv_img2);
            ImageView img3=convertView.findViewById(R.id.iv_img3);
            RoundedImageView header=convertView.findViewById(R.id.iv_header_focus);
            TextView authorName=convertView.findViewById(R.id.tv_author_name);
            LinearLayout ll=convertView.findViewById(R.id.ll_imgs_focus);

            Glide.with(context).asBitmap().load(imgList.get(0).getSrc()).into(img1);
            Glide.with(context).asBitmap().load(imgList.get(1).getSrc()).into(img2);
            Glide.with(context).asBitmap().load(author.getHeadImg()).into(header);
            authorName.setText(author.getName());
        }else if(num==1){
            if(null==convertView){
            convertView= LayoutInflater.from(context).inflate(R.layout.focus_item,null);
             }
            ImageView img1=convertView.findViewById(R.id.iv_img1);
            ImageView img2=convertView.findViewById(R.id.iv_img2);
            ImageView img3=convertView.findViewById(R.id.iv_img3);
            RoundedImageView header=convertView.findViewById(R.id.iv_header_focus);
            TextView authorName=convertView.findViewById(R.id.tv_author_name);
            LinearLayout ll=convertView.findViewById(R.id.ll_imgs_focus);

            Glide.with(context).asBitmap().load(imgList.get(0).getSrc()).into(img1);
            Glide.with(context).asBitmap().load(author.getHeadImg()).into(header);
            authorName.setText(author.getName());
        }else{
            if(null==convertView){
                convertView= LayoutInflater.from(context).inflate(R.layout.focus_item_gone,null);
            }
            RoundedImageView header=convertView.findViewById(R.id.iv_header_focus);
            TextView authorName=convertView.findViewById(R.id.tv_author_name);

            Glide.with(context).asBitmap().load(author.getHeadImg()).into(header);
            authorName.setText(author.getName());
        }
        return convertView;
    }
}
