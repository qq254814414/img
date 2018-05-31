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

import com.alibaba.fastjson.JSONObject;
import com.mengxin.img.R;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.utils.MD5;
import com.mengxin.img.utils.ResUtils;
import com.mengxin.img.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RegistFragment extends Fragment{

    private CompositeDisposable mSubscriptions;

    private Button back;
    private EditText name;
    private EditText passWord;
    private EditText rePassWord;
    private EditText phoneNumber;
    private EditText vCode;
    private Button regist;
    private Button getVode;
    private View fir_line;
    private View sec_line;
    private View thr_line;
    private View for_line;
    private View fiv_line;
    private View six_line;

    private Boolean nameIsOk = false;
    private Boolean passWordIsOk = false;
    private Boolean phoneIsOk = false;


    private FragmentManager manager;

    public static RegistFragment newInstance(){
        return new RegistFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register,container,false);
        mSubscriptions = new CompositeDisposable();

        initView(view);

        manager = getActivity().getSupportFragmentManager();

        back.setOnClickListener(v -> {
            manager.beginTransaction().replace(R.id.login_content_container,LoginFragment.newInstance()).commit();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }else {
                    HttpMethods.getInstance().isNameUse(new Observer<Boolean>() {
                        private Disposable d;
                        @Override
                        public void onSubscribe(Disposable d) {
                            this.d = d;
                            mSubscriptions.add(d);
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean == true){
                                nameIsOk = false;
                                fir_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                                sec_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                                ToastUtils.longToast("用户名已经被使用");
                            } else {
                                nameIsOk = true;
                                fir_line.setBackgroundColor(ResUtils.getColor(R.color.register_ok));
                                sec_line.setBackgroundColor(ResUtils.getColor(R.color.register_ok));
                                ToastUtils.longToast("用户名可以使用");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            d.dispose();
                        }

                        @Override
                        public void onComplete() {

                        }
                    },name.getText().toString());
                }
            }
        });
        rePassWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String fir = passWord.getText().toString();
                    String sec = rePassWord.getText().toString();
                    if (!fir.equals(sec)){
                        passWordIsOk = false;
                        thr_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                        for_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                        ToastUtils.longToast("两次密码输入不同");
                    } else {
                        passWordIsOk = true;
                        thr_line.setBackgroundColor(ResUtils.getColor(R.color.register_ok));
                        for_line.setBackgroundColor(ResUtils.getColor(R.color.register_ok));
                    }
                }
            }
        });
        passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String fir = passWord.getText().toString();
                    String sec = rePassWord.getText().toString();
                    if (!fir.equals(sec)){
                        passWordIsOk = false;
                        thr_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                        for_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                        ToastUtils.longToast("两次密码输入不同");
                    } else {
                        passWordIsOk = true;
                        thr_line.setBackgroundColor(ResUtils.getColor(R.color.register_ok));
                        for_line.setBackgroundColor(ResUtils.getColor(R.color.register_ok));
                    }
                }
            }
        });
        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String phone = phoneNumber.getText().toString();
                    if (phone.length() != 11){
                        phoneIsOk = false;
                        fiv_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                        six_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                        ToastUtils.longToast("手机号格式不正确");
                    }
                    if (phone.matches("^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$")){
                        phoneIsOk = true;
                        fiv_line.setBackgroundColor(ResUtils.getColor(R.color.register_ok));
                        six_line.setBackgroundColor(ResUtils.getColor(R.color.register_ok));
                    } else {
                        phoneIsOk = false;
                        fiv_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                        six_line.setBackgroundColor(ResUtils.getColor(R.color.colorAccent));
                        ToastUtils.longToast("手机号格式不正确");
                    }

                }
            }
        });
        getVode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phoneIsOk){
                    return;
                }
                HttpMethods.getInstance().getVcode(new Observer<JSONObject>() {
                    Integer time = 90;
                    private Disposable d;
                    @Override
                    public void onSubscribe(Disposable d) {
                        this.d = d;
                        mSubscriptions.add(d);
                    }

                    @Override
                    public void onNext(JSONObject object) {
                        if (object.containsKey("error")){
                            ToastUtils.longToast(object.getString("error"));
                        } else {
                            ToastUtils.longToast("验证码已发送");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                },phoneNumber.getText().toString());
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameIsOk && passWordIsOk && phoneIsOk){
                    String authorName = name.getText().toString();
                    String pass = passWord.getText().toString();
                    Author author = new Author();
                    author.setName(authorName);
                    author.setPassWord(MD5.encryption(pass,authorName));
                    author.setPhoneNumber(phoneNumber.getText().toString());
                    String code = vCode.getText().toString();
                    JSONObject object = new JSONObject();
                    object.put("author",author);
                    object.put("vCode",code);
                    HttpMethods.getInstance().regist(new Observer<Boolean>() {
                        private Disposable d;
                        @Override
                        public void onSubscribe(Disposable d) {
                            this.d = d;
                            mSubscriptions.add(d);
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean){
                                ToastUtils.shortToast("注册成功");
                                manager.beginTransaction().replace(R.id.login_content_container,LoginFragment.newInstance()).commit();
                            } else {
                                ToastUtils.shortToast("验证码错误");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    },object);
                } else {
                    ToastUtils.longToast("注册信息有错误");
                    return;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
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
        fir_line = view.findViewById(R.id.first_line);
        sec_line = view.findViewById(R.id.second_line);
        thr_line = view.findViewById(R.id.thr_line);
        for_line = view.findViewById(R.id.for_line);
        fiv_line = view.findViewById(R.id.fiv_line);
        six_line = view.findViewById(R.id.six_line);
    }

}
