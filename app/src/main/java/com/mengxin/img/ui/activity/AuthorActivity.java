package com.mengxin.img.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mengxin.img.R;
import com.mengxin.img.ui.fragment.AuthorFragment;

public class AuthorActivity extends AppCompatActivity{

    private Long id;

    private FragmentManager manager;
    private Fragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_content_main);
        Intent intent = getIntent();
        id = intent.getLongExtra("authorId",0);

        manager = getSupportFragmentManager();
        currentFragment = new Fragment();

        changeFragment(AuthorFragment.newInstance());

    }

    private void changeFragment(Fragment fragment){
        if (currentFragment != fragment){
            Bundle bundle = new Bundle();
            bundle.putLong("authorId",id);
            fragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.author_content_container,fragment).commit();
            currentFragment = fragment;
        }
    }

}
