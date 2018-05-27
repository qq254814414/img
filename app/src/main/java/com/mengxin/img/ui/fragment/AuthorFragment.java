package com.mengxin.img.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.adapter.AuthorImgAdapter;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AuthorFragment extends Fragment{

    private CompositeDisposable mSubscriptions;

    private ImageView headImg;
    private ImageView bgImg;
    private TextView name;
    private TextView introduction;
    private GridView imgList;

    private AuthorImgAdapter adapter;

    public static AuthorFragment newInstance(){
        return new AuthorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.author,container,false);
        mSubscriptions = new CompositeDisposable();

        initView(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AuthorImgAdapter(getActivity());
        imgList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSubscriptions.clear();
    }

    private void initView(View view) {
        headImg = view.findViewById(R.id.iv_header_personal);
        bgImg = view.findViewById(R.id.iv_bg_personal);
        name = view.findViewById(R.id.tv_username_personal);
        introduction = view.findViewById(R.id.tv_signature_personal);
        imgList = view.findViewById(R.id.gv_imgList_personal);
    }

    private void fetchAuthor(long id){
        HttpMethods.getInstance().getAuthorDetail(new Observer<Author>() {
            private Disposable d;
            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
                mSubscriptions.add(d);
            }

            @Override
            public void onNext(Author author) {
                Glide.with(getActivity())
                        .load(author.getHeadImg())
                        .apply(new RequestOptions()
                                .centerCrop())
                        .into(headImg);
                Glide.with(getActivity())
                        .load(author.getBgImg())
                        .apply(new RequestOptions()
                                .centerCrop())
                        .into(bgImg);
                name.setText(author.getName());
                introduction.setText(author.getIntroduction());
                adapter.addImgs(author.getImgList());
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

}
