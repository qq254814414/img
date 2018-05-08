package com.mengxin.img.ui.activity;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mengxin.img.R;
import com.mengxin.img.utils.PackageUtils;
import com.mengxin.img.utils.ResUtils;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer_layout;
    private NavigationView nav_view;
    private TextView tv_nav_title;
    private ConstraintLayout cly_main_content;
    private FragmentManager mFgManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFgManager = getSupportFragmentManager();
        initView();
        initData();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        nav_view = findViewById(R.id.nav_view);
        tv_nav_title = nav_view.getHeaderView(0).findViewById(R.id.tv_nav_title);
        drawer_layout = findViewById(R.id.drawer_layout);
        cly_main_content = findViewById(R.id.cly_main_content);

        setSupportActionBar(toolbar);
        nav_view.setItemIconTintList(null);
//        nav_view.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initData() {
//        mFgManager.beginTransaction().replace(R.id.cly_main_content,
//                LittleSisterFragment.newInstance(), DryConstant.FG_LITTLE_SISTER).commit();
//        toolbar.setTitle(ResUtils.getString(R.string.menu_see_little_sister));
        String version = PackageUtils.packageName();
        if(version != null) {
            String msg = String.format(ResUtils.getString(R.string.menu_drysister_version), version);
            tv_nav_title.setText(msg);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
