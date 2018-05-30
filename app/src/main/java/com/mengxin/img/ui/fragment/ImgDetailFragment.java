package com.mengxin.img.ui.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.activity.LoginActivity;
import com.mengxin.img.ui.activity.MainActivity;
import com.mengxin.img.utils.NetworkUtils;
import com.mengxin.img.utils.ResUtils;
import com.mengxin.img.utils.ToastUtils;

import org.w3c.dom.Attr;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ImgDetailFragment extends Fragment{

    private CompositeDisposable mSubscriptions;

    private ImageView imgView;
    private Button back;
    private Button share;
    private RoundedImageView authorHeader;
    private TextView imgName;
    private TextView imgIntroduction;
    private TextView authorName;
    private TextView publishTime;
    private TextView clickNum;
    private TextView likeNum;
    private FloatingActionButton fab_like;
    private Boolean like = false;

    private JSONObject object = new JSONObject();

    private Long authorId;
    private Long id;

    public static ImgDetailFragment newInstance(){
        return new ImgDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.img_detail,container,false);

        authorId = NetworkUtils.isLogin(getActivity());
        id = getArguments().getLong("img_id");

        mSubscriptions = new CompositeDisposable();
        initView(view);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        if (authorId != 0){
            object.put("authorId",authorId);
            object.put("imgId",id);
            HttpMethods.getInstance().isLikeImg(new Observer<Boolean>() {
                private Disposable d;
                @Override
                public void onSubscribe(Disposable d) {
                    this.d = d;
                    mSubscriptions.add(d);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    like = aBoolean;
                }

                @Override
                public void onError(Throwable e) {
                    d.dispose();
                }

                @Override
                public void onComplete() {

                }
            },object);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchImgDetail(id);
        if (like){
            ColorStateList colorStateList = ColorStateList.valueOf(ResUtils.getColor(R.color.colorAccent));
            fab_like.setBackgroundTintList(colorStateList);
        } else {
            ColorStateList colorStateList = ColorStateList.valueOf(ResUtils.getColor(R.attr.colorControlHighlight));
            fab_like.setBackgroundTintList(colorStateList);
        }
        fab_like.setOnClickListener(v -> {
            if (authorId == 0){
                ToastUtils.shortToast("还没有登录!");
                return;
            }
            if (like){
                HttpMethods.getInstance().unLikeImg(new Observer<Boolean>() {
                    private Disposable d;
                    @Override
                    public void onSubscribe(Disposable d) {
                        this.d = d;
                        mSubscriptions.add(d);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        like = false;
                        ColorStateList colorStateList = ColorStateList.valueOf(ResUtils.getColor(R.attr.colorControlHighlight));
                        fab_like.setBackgroundTintList(colorStateList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        d.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                },object);
            } else {
                HttpMethods.getInstance().likeImg(new Observer<Boolean>() {
                    private Disposable d;
                    @Override
                    public void onSubscribe(Disposable d) {
                        this.d = d;
                        mSubscriptions.add(d);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        like = true;
                        ColorStateList colorStateList = ColorStateList.valueOf(ResUtils.getColor(R.color.colorAccent));
                        fab_like.setBackgroundTintList(colorStateList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                },object);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSubscriptions.clear();
    }

    private void fetchImgDetail(long id){
        HttpMethods.getInstance().getImgDetail(new Observer<Img>() {
            private Disposable d;
            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
                mSubscriptions.add(d);
            }

            @Override
            public void onNext(Img img) {
                Glide.with(getActivity())
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
                Glide.with(getActivity())
                        .load(img.getAuthor().getHeadImg())
                        .apply(new RequestOptions()
                                .centerCrop())
                        .into(authorHeader);
                imgName.setText(img.getName());
                imgIntroduction.setText(img.getIntroduction());
                authorName.setText(img.getAuthor().getName());
                publishTime.setText(img.getPublishTime().toString());
                clickNum.setText(img.getClickNum().toString());
                likeNum.setText(img.getFavoriteNum().toString());

            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }

            @Override
            public void onComplete() {

            }
        },id);
    }

    private void initView(View view) {
        back = view.findViewById(R.id.bt_img_detail_back);
        share = view.findViewById(R.id.bt_img_detail_share);
        imgView = view.findViewById(R.id.iv_detail_img);
        authorHeader = view.findViewById(R.id.iv_author_header);
        imgName = view.findViewById(R.id.tv_img_name);
        imgIntroduction = view.findViewById(R.id.tv_img_introduction);
        authorName = view.findViewById(R.id.tv_author_name);
        publishTime = view.findViewById(R.id.tv_publishTime);
        clickNum = view.findViewById(R.id.tv_img_click_num);
        likeNum = view.findViewById(R.id.tv_img_like_num);
        fab_like = view.findViewById(R.id.fab_like);
    }
}
