package com.mengxin.img.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mengxin.img.R;
import com.mengxin.img.ui.fragment.FocusFragment;
import com.r0adkll.slidr.Slidr;

public class FocusActivity extends AppCompatActivity{
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        Slidr.attach(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        Long authorId = intent.getLongExtra("authorId",0L);
        String type = intent.getStringExtra("type");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(type);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        FocusFragment focusFragment=FocusFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putLong("authorId",authorId);
        bundle.putString("type",type);
        focusFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.focus_container, focusFragment).commit();

    }
}
