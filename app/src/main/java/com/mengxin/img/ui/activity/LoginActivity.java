package com.mengxin.img.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mengxin.img.R;
import com.mengxin.img.ui.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity{

    private FragmentManager manager;
    private Fragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_content_main);

        manager = getSupportFragmentManager();
        currentFragment = new Fragment();

        changeFragment(LoginFragment.newInstance());
    }



    private void changeFragment(Fragment fragment){
        if (currentFragment != fragment){
            manager.beginTransaction().replace(R.id.login_content_container,fragment).commit();
            currentFragment = fragment;
        }
    }

}
