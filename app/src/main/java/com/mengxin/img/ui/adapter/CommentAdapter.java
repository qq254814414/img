package com.mengxin.img.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Comment;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Comment> comments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    public void addComments(ArrayList<Comment> data){
        comments.clear();
        comments.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(context, R.layout.comment_item,null);
        }
        RoundedImageView header = convertView.findViewById(R.id.iv_comment_header);
        TextView name = convertView.findViewById(R.id.tv_comment_name);
        TextView time = convertView.findViewById(R.id.tv_comment_time);
        TextView content = convertView.findViewById(R.id.tv_comment_content);
        Comment comment = comments.get(position);
        name.setText(comment.getAuthor().getName());
        time.setText(comment.getPublishTime().toString());
        content.setText(comment.getContent());
        Glide.with(context)
                .load(comment.getAuthor().getHeadImg())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(header);
        return convertView;
    }

}
