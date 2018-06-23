package com.mengxin.img.net;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mengxin.img.ImgInit;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.data.dto.Comment;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.utils.RxSchedulers;
import com.mengxin.img.utils.ToastUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.Multipart;

/**
 * Created by zt on 2017/3/10.
 */

public class HttpMethods {

    private static final String BASE_URL = "http://10.0.2.2:8080/";
    private ImgApiService imgApiService;

    private HttpMethods() {
        /**
         * 由于retrofit底层的实现是通过okhttp实现的，所以可以通过okHttp来设置一些连接参数
         * 如超时等
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(ImgInit.mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        imgApiService = retrofit.create(ImgApiService.class);
    }


    private static class sinalInstance {
        public static final HttpMethods instance = new HttpMethods();
    }

    public  static HttpMethods getInstance(){
        return sinalInstance.instance;
    }

    public void fetchIllImg(Observer<ArrayList<Img>> observer,int num){
         imgApiService.fetchIllImg(num)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getAuthorDetail(Observer<Author> observer,long id){
        imgApiService.getAuthorDetail(id)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getImgDetail(Observer<Img> observer,long id){
        imgApiService.getImgDetail(id)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void loginByPassWord(Observer<String> observer,Author author){
        imgApiService.loginByPassWord(author)
            .compose(RxSchedulers.obcompose())
            .subscribe(observer);
    }

    public void isNameUse(Observer<Boolean> observer,String name){
        imgApiService.isNameUse(name)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getVcode(Observer<JSONObject> observer,String phoneNumber){
        imgApiService.getVcode(phoneNumber)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void regist(Observer<Boolean> observer,JSONObject object){
        imgApiService.regist(object)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getHeadImg(Observer<String> observer,Long id){
        imgApiService.getHeadImg(id)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void likeImg(Observer<Boolean> observer,JSONObject object){
        imgApiService.likeImg(object)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void unLikeImg(Observer<Boolean> observer,JSONObject object){
        imgApiService.unLikeImg(object)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void isLikeImg(Observer<Boolean> observer,JSONObject object){
        imgApiService.isLikeImg(object)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getAuthorImg(Observer<ArrayList<Img>> observer,long id,int num){
        imgApiService.getAuthorImg(id,num)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getFocusList(Observer<ArrayList<Author>> observer,long id){
        imgApiService.getFocusList(id)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getFansList(Observer<ArrayList<Author>> observer,long id){
        imgApiService.getFansList(id)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getFocusNum(Observer<Integer> observer,long id){
        imgApiService.getFocusNum(id)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getFansNum(Observer<Integer> observer,long id){
        imgApiService.getFansNum(id)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void isFocus(Observer<Boolean> observer,long focus,long fans){
        imgApiService.isFocus(focus,fans)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void focus(Observer<Boolean> observer,long focus,long fans){
        JSONObject object = new JSONObject();
        object.put("focus",focus);
        object.put("fans",fans);
        imgApiService.focus(object)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void unFocus(Observer<Boolean> observer,long focus,long fans){
        JSONObject object = new JSONObject();
        object.put("focus",focus);
        object.put("fans",fans);
        imgApiService.unFocus(object)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void saveComment(Observer<Boolean> observer, Comment comment){
        imgApiService.saveComment(comment)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void getComment(Observer<ArrayList<Comment>> observer,long id){
        imgApiService.getComment(id)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void upLoad(Observer<Boolean> observer,String path,Img img){
        File file = new File(path);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"),file);
        builder.addFormDataPart("file",file.getName(),fileRequestBody);
        MultipartBody.Part part1 = builder.build().part(0);

        imgApiService.upLoad(part1,JSON.toJSONString(img))
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }

    public void get10RankingList(Observer<ArrayList<Img>> observer,String beginTime,String endTime){
        imgApiService.get10RankingList(beginTime,endTime)
                .compose(RxSchedulers.obcompose())
                .subscribe(observer);
    }
}