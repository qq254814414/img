package com.mengxin.img.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mengxin.img.R;
import com.mengxin.img.ui.activity.MainActivity;

public class LoginFragment extends Fragment{

    private FragmentManager manager;

    private Button back;
    private EditText authorName;
    private EditText passWrod;
    private Button login;
    private Button regist;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login,container,false);
        manager = getActivity().getSupportFragmentManager();

        initView(view);

        regist.setOnClickListener(v ->{
            manager.beginTransaction().replace(R.id.login_content_container,RegistFragment.newInstance()).commit();
        });

        back.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        login.setOnClickListener(v -> {

        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView(View view) {
        back = view.findViewById(R.id.bt_login_back);
        authorName = view.findViewById(R.id.et_login);
        passWrod = view.findViewById(R.id.et_passWord);
        login = view.findViewById(R.id.bt_login);
        regist = view.findViewById(R.id.bt_regist);
    }


}
