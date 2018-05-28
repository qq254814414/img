package com.mengxin.img.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;

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

    private Long id;

    public static ImgDetailFragment newInstance(){
        return new ImgDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.img_detail,container,false);

        id = getArguments().getLong("img_id");

        mSubscriptions = new CompositeDisposable();
        initView(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchImgDetail(id);
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
    }
}
