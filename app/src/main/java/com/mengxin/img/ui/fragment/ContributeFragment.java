package com.mengxin.img.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mengxin.img.R;
import com.mengxin.img.net.HttpMethods;
import com.mengxin.img.utils.ToastUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ContributeFragment extends Fragment{
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private ImageView imageView;
    private EditText name;
    private EditText introduction;
    private Button button;
//    private EditText type;

    private Uri uri;

    public static ContributeFragment newInstance(){
        return new ContributeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contribute,container,false);
        initView(view);
        clickListener();
        return view;
    }

    private void initView(View view) {
        imageView = view.findViewById(R.id.iv_con);
        name = view.findViewById(R.id.con_name);
        introduction = view.findViewById(R.id.con_introduction);
        button = view.findViewById(R.id.bt_con_ok);
    }

    private void clickListener(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
        });
        button.setOnClickListener(v -> {
            HttpMethods.getInstance().upLoad(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    ToastUtils.shortToast(s);
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.shortToast(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            },getRealPathFromURI(uri));
        });
    }

    private String getRealPathFromURI(Uri contentUri) { //传入图片uri地址
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY){
            if (data != null){
                uri = data.getData();
                Glide.with(getActivity())
                        .load(uri)
                        .into(imageView);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
