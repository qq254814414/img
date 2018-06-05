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

public class FocusFragment extends Fragment{

    public static FocusFragment newInstance() {
        FocusFragment fragment = new FocusFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.focus,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
