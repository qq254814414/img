package com.mengxin.img.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mengxin.img.R;
import com.mengxin.img.ui.fragment.ContributeBeginFragment;
import com.r0adkll.slidr.Slidr;

public class ContributeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);
        Slidr.attach(this);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("你的作品");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        getSupportFragmentManager().beginTransaction().replace(R.id.contribute_main, ContributeBeginFragment.newInstance()).commit();

    }


}
