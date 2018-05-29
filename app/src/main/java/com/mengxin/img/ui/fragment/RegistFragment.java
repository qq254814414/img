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
import android.widget.EditText;

import com.mengxin.img.R;

public class RegistFragment extends Fragment{

    private Button back;
    private EditText name;
    private EditText passWord;
    private EditText rePassWord;
    private EditText phoneNumber;
    private EditText vCode;
    private Button regist;
    private Button getVode;

    private FragmentManager manager;

    public static RegistFragment newInstance(){
        return new RegistFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register,container,false);

        initView(view);

        manager = getActivity().getSupportFragmentManager();

        back.setOnClickListener(v -> {
            manager.beginTransaction().replace(R.id.login_content_container,LoginFragment.newInstance()).commit();
        });
        return view;
    }

    private void initView(View view) {
        back = view.findViewById(R.id.bt_register_back);
        name = view.findViewById(R.id.et_register_name);
        passWord = view.findViewById(R.id.et_register_passWord);
        rePassWord = view.findViewById(R.id.et_register_re_passWord);
        phoneNumber = view.findViewById(R.id.et_register_phone_number);
        vCode = view.findViewById(R.id.et_register_verification_code);
        regist = view.findViewById(R.id.bt_register_ok);
        getVode = view.findViewById(R.id.bt_register_get_code);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
