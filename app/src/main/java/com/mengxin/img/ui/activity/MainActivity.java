package com.mengxin.img.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengxin.img.ImgConstant;
import com.mengxin.img.R;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.fragment.IllustrationsFragment;
import com.mengxin.img.utils.NetworkUtils;
import com.mengxin.img.utils.PackageUtils;
import com.mengxin.img.utils.ResUtils;
import com.mengxin.img.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable mSubscriptions;

    private Toolbar toolbar;
    private DrawerLayout drawer_layout;
    private NavigationView nav_view;
    private TextView tv_nav_title;
    private ConstraintLayout cly_main_content;
    private FragmentManager mFgManager;
    private ImageView headerImg;
    private Context mcontext;
    private Long authorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSubscriptions = new CompositeDisposable();
        mFgManager = getSupportFragmentManager();
        mcontext = this;
        authorId = NetworkUtils.isLogin(this);
        initView();
        initData();


    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        nav_view = findViewById(R.id.nav_view);
        tv_nav_title = nav_view.getHeaderView(0).findViewById(R.id.tv_nav_title);
        headerImg = nav_view.getHeaderView(0).findViewById(R.id.img_head_icon);
        drawer_layout = findViewById(R.id.drawer_layout);
        cly_main_content = findViewById(R.id.cly_main_content);


        setSupportActionBar(toolbar);
        nav_view.setItemIconTintList(null);
        nav_view.setNavigationItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
//                case R.id.nav_see_little_sister:
//                    if (mFgManager.findFragmentByTag(DryConstant.FG_LITTLE_SISTER) == null) {
//                        mFgManager.beginTransaction().replace(R.id.cly_main_content,
//                                LittleSisterFragment.newInstance(), DryConstant.FG_LITTLE_SISTER).commit();
//                        toolbar.setTitle(ResUtils.getString(R.string.menu_see_little_sister));
//                    }
//                    break;
                case R.id.nav_else_setting:
                    intent = new Intent(mcontext,SettingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.nav_focus:
                    intent = new Intent(mcontext,FocusActivity.class);
                    intent.putExtra("type","关注列表");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.nav_fans:
                    intent = new Intent(mcontext,FocusActivity.class);
                    intent.putExtra("type","粉丝列表");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.nav_like:
                    intent = new Intent(mcontext,LikeActivity.class);
                    intent.putExtra("authorId",authorId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    intent = new Intent(mcontext,SearchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.nav_con:
                    if (authorId == 0L){
                        ToastUtils.shortToast("还没登陆");
                    } else {
                        intent = new Intent(mcontext,ContributeActivity.class);
                        intent.putExtra("authorId",authorId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    break;
            }
            drawer_layout.closeDrawer(GravityCompat.START);
            return true;
        });
        headerImg.setOnClickListener(v -> {
            if (authorId == 0){
                Intent intent = new Intent(mcontext,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Intent intent = new Intent(mcontext,AuthorActivity.class);
                intent.putExtra("authorId",authorId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        /** 用户登陆后显示用户头像 **/
        if ( authorId != 0){
            HttpMethods.getInstance().getHeadImg(new Observer<String>() {
                private Disposable d;
                @Override
                public void onSubscribe(Disposable d) {
                    this.d = d;
                    mSubscriptions.add(d);
                }

                @Override
                public void onNext(String s) {
                    Glide.with(mcontext)
                            .load(s)
                            .apply(new RequestOptions()
                                    .centerCrop())
                            .into(headerImg);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            },authorId);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initData() {
        mFgManager.beginTransaction().replace(R.id.cly_main_content,
                IllustrationsFragment.newInstance(), ImgConstant.FG_LITTLE_SISTER).commit();
        toolbar.setTitle(ResUtils.getString(R.string.menu_see_ill_img));
        String version = PackageUtils.packageName();
        if(version != null) {
            String msg = String.format(ResUtils.getString(R.string.menu_Img_version), version);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }
}
