package com.mengxin.img.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;

import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.adapter.IllustrationsAdapter;
import com.mengxin.img.utils.ToastUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 图片显示List
 */
public class SearchFragment extends Fragment{

    private Long authorId;

    private Button button;
    private SwipeRefreshLayout srl_refresh;
    private RecyclerView rec_mz;
    private CompositeDisposable mSubscriptions;
    private IllustrationsAdapter mAdapter;
    private EditText editText;
    private ArrayList<Img> mData;
    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public static SearchFragment newInstance(){
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);
        srl_refresh = view.findViewById(R.id.srl_refresh);
        rec_mz = view.findViewById(R.id.rec_mz);
        editText = view.findViewById(R.id.tv_search);
        button = view.findViewById(R.id.bt_search);

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rec_mz.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubscriptions = new CompositeDisposable();
        mData = new ArrayList<Img>();
        mAdapter = new IllustrationsAdapter(getActivity(), mData);
        rec_mz.setAdapter(mAdapter);

        button.setOnClickListener(v -> {
            fetchImg();
        });

    }

    /* 拉取插画数据 */
    public void fetchImg(){
        HttpMethods.getInstance().search(new Observer<ArrayList<Img>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList<Img> imgs) {
                if (imgs.isEmpty()){
                    ToastUtils.shortToast("没有更多了");
                    srl_refresh.setRefreshing(false);
                    return;
                }
                mAdapter.addAll(imgs);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                srl_refresh.setRefreshing(false);
            }
        },editText.getText().toString());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

}
