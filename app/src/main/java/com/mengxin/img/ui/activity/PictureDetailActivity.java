package com.mengxin.img.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mengxin.img.R;
import com.mengxin.img.ui.fragment.ImgDetailFragment;

public class PictureDetailActivity extends AppCompatActivity{
    private FragmentManager manager;
    private Fragment currentFragment;
    private Long id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_detail_content_main);

        id = getIntent().getLongExtra("img_id",0);

        manager = getSupportFragmentManager();
        currentFragment = new Fragment();

        changeFragment(ImgDetailFragment.newInstance());
    }



    private void changeFragment(Fragment fragment){
        if (currentFragment != fragment){
            Bundle bundle = new Bundle();
            bundle.putLong("img_id",id);
            fragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.img_detail_content_container,fragment).commit();
            currentFragment = fragment;
        }
    }
}
