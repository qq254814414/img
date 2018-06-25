package com.mengxin.img.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import View.HorizontalListView;

import com.mengxin.img.R;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.adapter.IllustrationsAdapter;
import com.mengxin.img.ui.adapter.ImgListAdapter;
import com.mengxin.img.utils.RxSchedulers;
import com.mengxin.img.utils.TimeUtils;
import com.mengxin.img.utils.ToastUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 图片显示List
 */
public class ImgListFragment extends Fragment{

    private Long authorId;
    private String page;

    private SwipeRefreshLayout srl_refresh;
    private FloatingActionButton fab_top;
    private RecyclerView rec_mz;
    private CompositeDisposable mSubscriptions;
    private IllustrationsAdapter mAdapter;
    private int CurPage = 0;
    private ArrayList<Img> mData;
    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public static ImgListFragment newInstance(){
        return new ImgListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_img_content, container, false);
        srl_refresh = view.findViewById(R.id.srl_refresh);
        rec_mz = view.findViewById(R.id.rec_mz);
        fab_top = view.findViewById(R.id.fab_top);
        srl_refresh.setOnRefreshListener(() -> {
            CurPage = 0;
            fetchImg(true);
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
                        fetchImg(false);
                    }
                }
                if (layoutManager.findFirstVisibleItemPosition() != 0) {
                    fabInAnim();
                } else {
                    fabOutAnim();
                }
            }
        });
        fab_top.setOnClickListener(v -> {
            LinearLayoutManager manager = (LinearLayoutManager) rec_mz.getLayoutManager();
            //如果超过50项直接跳到开头，不然要滚好久
            if(manager.findFirstVisibleItemPosition() < 50) {
                rec_mz.smoothScrollToPosition(0);
            } else {
                rec_mz.scrollToPosition(0);
                fabOutAnim();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubscriptions = new CompositeDisposable();
        mData = new ArrayList<Img>();
        mAdapter = new IllustrationsAdapter(getActivity(), mData);
        rec_mz.setAdapter(mAdapter);
        srl_refresh.setRefreshing(true);
        fetchImg(true);
    }

    /* 拉取插画数据 */
    private void fetchImg(boolean isRefresh) {
        HttpMethods.getInstance().fetchIllImg(new Observer<ArrayList<Img>>() {
            private Disposable d;
            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
                mSubscriptions.add(this.d);
                srl_refresh.setRefreshing(true);
            }

            @Override
            public void onNext(ArrayList<Img> imgs) {
                if (imgs.isEmpty()){
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
            public void onError(Throwable t) {
                ToastUtils.shortToast("Error");
                d.dispose();
                RxSchedulers.processRequestException(t);
            }

            @Override
            public void onComplete() {
                srl_refresh.setRefreshing(false);
            }
        },CurPage*20);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

    /* 悬浮按钮显示动画 */
    private void fabInAnim() {
        if (fab_top.getVisibility() == View.GONE) {
            fab_top.setVisibility(View.VISIBLE);
            ViewCompat.animate(fab_top).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null).start();
        }
    }

    /* 悬浮图标隐藏动画 */
    private void fabOutAnim() {
        if (fab_top.getVisibility() == View.VISIBLE) {
            ViewCompat.animate(fab_top).scaleX(0.0F).scaleY(0.0F).alpha(0.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {
                    fab_top.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            }).start();
        }
    }

}
