package com.mengxin.img.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.adapter.IllustrationsAdapter;
import com.mengxin.img.utils.ToastUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ContributeBeginFragment extends Fragment{
    private Long authorId;

    private Button button;
    private SwipeRefreshLayout srl_refresh;
    private IllustrationsAdapter mAdapter;
    private int CurPage = 0;
    private ArrayList<Img> mData;
    private RecyclerView rec_mz;

    public static ContributeBeginFragment newInstance(){
        return new ContributeBeginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contribute_begin,container,false);
        initView(view);
        clickListener();

        srl_refresh = view.findViewById(R.id.srl_refresh);
        rec_mz = view.findViewById(R.id.rec_img);
        srl_refresh.setOnRefreshListener(() -> {
            CurPage = 0;
            fetchAuthorImg(true);
        });
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rec_mz.setLayoutManager(layoutManager);
        rec_mz.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//加载更多
                    if (layoutManager.getItemCount() - recyclerView.getChildCount() <= layoutManager.findFirstVisibleItemPosition()) {
                        ++CurPage;
                        fetchAuthorImg(false);
                    }
                }
            }
        });

        return view;
    }

    private void initView(View view) {
        button = view.findViewById(R.id.bt_begin_con);
    }

    private void clickListener(){
        button.setOnClickListener(v -> {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.contribute_main,ContributeFragment.newInstance()).commit();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = new ArrayList<Img>();
        mAdapter = new IllustrationsAdapter(getActivity(), mData);
        rec_mz.setAdapter(mAdapter);
        srl_refresh.setRefreshing(true);
        Bundle bundle = getArguments();
        authorId = bundle.getLong("authorId",0);
        fetchAuthorImg(true);
    }

    private void fetchAuthorImg(boolean isRefresh){
        HttpMethods.getInstance().getAuthorImg(new Observer<ArrayList<Img>>() {
            private Disposable d;
            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ArrayList<Img> imgs) {
                if (imgs.isEmpty()){
                    d.dispose();
                    ToastUtils.shortToast("没有更多图片了");
                    srl_refresh.setRefreshing(false);
                }
                if (isRefresh){
                    mAdapter.addAll(imgs);
                } else {
                    mAdapter.loadMore(imgs);
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }

            @Override
            public void onComplete() {
                srl_refresh.setRefreshing(false);
            }
        },authorId,CurPage*20);
    }
}
