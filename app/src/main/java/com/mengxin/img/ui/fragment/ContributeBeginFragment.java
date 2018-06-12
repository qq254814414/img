package com.mengxin.img.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mengxin.img.R;

public class ContributeBeginFragment extends Fragment{
    private Button button;

    public static ContributeBeginFragment newInstance(){
        return new ContributeBeginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contribute_begin,container,false);
        initView(view);
        clickListener();
        return view;
    }

    private void initView(View view) {
        button = view.findViewById(R.id.bt_begin_con);
    }

    private void clickListener(){
        button.setOnClickListener(v -> {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.contribute_main,ContributeFragment.newInstance()).commit();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
