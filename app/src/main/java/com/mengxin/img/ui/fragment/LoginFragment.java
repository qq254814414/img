package com.mengxin.img.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.ui.activity.MainActivity;
import com.mengxin.img.utils.MD5;
import com.mengxin.img.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginFragment extends Fragment{

    private CompositeDisposable mSubscriptions;

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
        mSubscriptions = new CompositeDisposable();


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

            Author author = new Author();
            author.setName(authorName.getText().toString());
            author.setPassWord(MD5.encryption(passWrod.getText().toString(),author.getName()));
            if (author.getName().isEmpty()){
                ToastUtils.shortToast("忘了输入用户名了");
            }
            if(author.getPassWord().toString().isEmpty()){
                ToastUtils.shortToast("忘了输入密码了");
            }

            HttpMethods.getInstance().loginByPassWord(new Observer<String>() {
                private Disposable d;
                @Override
                public void onSubscribe(Disposable d) {
                    this.d = d;
                    mSubscriptions.add(d);
                }

                @Override
                public void onNext(String s) {
                    if (s.equals("false")){
                        ToastUtils.shortToast("用户名或密码错误");
                        return;
                    }
                    SharedPreferences sp = getActivity().getSharedPreferences("loginToken", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("authorId",s);
                    editor.apply();
                    ToastUtils.shortToast("登录成功");
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                @Override
                public void onError(Throwable e) {
                    d.dispose();
                }

                @Override
                public void onComplete() {

                }
            },author);

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
        mSubscriptions.clear();
    }

    private void initView(View view) {
        back = view.findViewById(R.id.bt_login_back);
        authorName = view.findViewById(R.id.et_login);
        passWrod = view.findViewById(R.id.et_passWord);
        login = view.findViewById(R.id.bt_login);
        regist = view.findViewById(R.id.bt_regist);
    }


}
