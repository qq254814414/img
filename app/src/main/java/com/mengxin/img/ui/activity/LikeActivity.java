package com.mengxin.img.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mengxin.img.R;
import com.mengxin.img.ui.fragment.AuthorLikeFragment;
import com.mengxin.img.utils.NetworkUtils;
import com.r0adkll.slidr.Slidr;

public class LikeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Long id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like_content);
        Slidr.attach(this);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("我的收藏");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        AuthorLikeFragment fragment = new AuthorLikeFragment();

        id = NetworkUtils.isLogin(this);
        Bundle bundle = new Bundle();
        bundle.putLong("authorId",id);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.like_main,fragment).commit();

    }


}
