package com.mengxin.img.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mengxin.img.R;
import com.mengxin.img.ui.activity.MainActivity;
import com.mengxin.img.utils.PackageUtils;
import com.mengxin.img.utils.ResUtils;
import com.mengxin.img.utils.ToastUtils;

public class SystemSettingFragment extends Fragment{

    private TextView tv_app_version;
    private TextView quit;

    public static SystemSettingFragment newInstance() {
        SystemSettingFragment fragment = new SystemSettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_setting,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String version = PackageUtils.packageName();
        if(version != null) {
            String msg = String.format(ResUtils.getString(R.string.cur_version), version);
            tv_app_version.setText(msg);
        }
        quit.setOnClickListener(v -> {
            SharedPreferences sp = getActivity().getSharedPreferences("loginToken", Context.MODE_PRIVATE);
            String authorId = sp.getString("authorId","0");
            if (authorId.equals("0")){
                ToastUtils.shortToast("你还没有登录");
            }else {
                sp.edit().remove("authorId").apply();
                ToastUtils.shortToast("退出成功");
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        tv_app_version = view.findViewById(R.id.tv_app_version);
        quit = view.findViewById(R.id.quit);
    }
}
