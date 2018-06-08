package com.mengxin.img.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.activity.FocusActivity;
import com.mengxin.img.ui.activity.MainActivity;
import com.mengxin.img.ui.activity.SettingActivity;
import com.mengxin.img.ui.adapter.AuthorImgAdapter;
import com.mengxin.img.utils.NetworkUtils;
import com.mengxin.img.utils.ToastUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AuthorFragment extends Fragment{

    private CompositeDisposable mSubscriptions;

    private FragmentManager manager;

    private Long meId;
    private Long authorId;
    private ImageView headImg;
    private ImageView bgImg;
    private TextView name;
    private TextView introduction;
    private GridView imgList;
    private Button back;
    private TextView fansNum;
    private TextView focusNum;
    private TextView viewAll;
    private TextView focus;
    private Boolean isFocus;
    private ImageView setting;

    private AuthorImgAdapter adapter;

    public static AuthorFragment newInstance(){
        return new AuthorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.author,container,false);
        meId = NetworkUtils.isLogin(getActivity());
        isFocus = false;
        mSubscriptions = new CompositeDisposable();
        manager = getActivity().getSupportFragmentManager();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AuthorImgAdapter(getActivity(),new ArrayList<Img>());
        imgList.setAdapter(adapter);

        authorId = getArguments().getLong("authorId");

        if (meId != authorId){
            setting.setVisibility(View.GONE);
        }

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
        },authorId);
        HttpMethods.getInstance().getFansNum(new Observer<Integer>() {
            private Disposable d;
            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
                mSubscriptions.add(d);
            }

            @Override
            public void onNext(Integer integer) {
                fansNum.setText(integer.toString());
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }

            @Override
            public void onComplete() {

            }
        },authorId);
        HttpMethods.getInstance().getFocusNum(new Observer<Integer>() {
            private Disposable d;
            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
                mSubscriptions.add(d);
            }

            @Override
            public void onNext(Integer integer) {
                focusNum.setText(integer.toString());
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }

            @Override
            public void onComplete() {

            }
        },authorId);
        if (meId.equals(authorId)){
            focus.setText("");
        } else {
            HttpMethods.getInstance().isFocus(new Observer<Boolean>() {
                private Disposable d;
                @Override
                public void onSubscribe(Disposable d) {
                    this.d = d;
                    mSubscriptions.add(d);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    isFocus = aBoolean;
                    if (isFocus){
                        focus.setText("已关注");
                    } else {
                        focus.setText("关注");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    d.dispose();
                }

                @Override
                public void onComplete() {

                }
            },authorId,meId);
        }

        clickListener();
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
        back = view.findViewById(R.id.bt_author_back);
        fansNum = view.findViewById(R.id.tv_fansNum_personal);
        focusNum = view.findViewById(R.id.tv_attentionNum_personal);
        viewAll = view.findViewById(R.id.tv_view_all);
        focus = view.findViewById(R.id.tv_focus);
        setting = view.findViewById(R.id.iv_settings_personal);
    }

    private void clickListener() {
        viewAll.setOnClickListener(v -> {
            AuthorImgListFragment fragment = AuthorImgListFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putLong("authorId",authorId);
            fragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.author_content_container,fragment).commit();
        });
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        fansNum.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),FocusActivity.class);
            intent.putExtra("authorId",authorId);
            intent.putExtra("type","粉丝列表");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        focusNum.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),FocusActivity.class);
            intent.putExtra("authorId",authorId);
            intent.putExtra("type","关注列表");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        focus.setOnClickListener(v -> {
            if (isFocus){
                HttpMethods.getInstance().unFocus(new Observer<Boolean>() {
                    private Disposable d;
                    @Override
                    public void onSubscribe(Disposable d) {
                        this.d = d;
                        mSubscriptions.add(d);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean == true){
                            isFocus = false;
                            focus.setText("关注");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        d.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                },authorId,meId);
            } else {
                if (meId == 0L){
                    ToastUtils.shortToast("尚未登录");
                } else {
                    HttpMethods.getInstance().focus(new Observer<Boolean>() {
                        private Disposable d;
                        @Override
                        public void onSubscribe(Disposable d) {
                            this.d = d;
                            mSubscriptions.add(d);
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean){
                                isFocus = true;
                                focus.setText("正在关注");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            d.dispose();
                        }

                        @Override
                        public void onComplete() {

                        }
                    },authorId,meId);
                }
            }
        });
        setting.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
