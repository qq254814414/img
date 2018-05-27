package com.mengxin.img.net;

import com.mengxin.img.ImgInit;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.data.dto.Img;
import com.mengxin.img.utils.RxSchedulers;

import java.util.ArrayList;

import io.reactivex.Observer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by zt on 2017/3/10.
 */

public class HttpMethods {

    private static final String BASE_URL = "http://10.7.86.85:8080/";
    private Retrofit retrofit;
    private ImgApiService imgApiService;

    private HttpMethods() {
        /**
         * 构造函数私有化
         * 并在构造函数中进行retrofit的初始化
         */
//        OkHttpClient client=new OkHttpClient();
//        client.newBuilder().connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        /**
         * 由于retrofit底层的实现是通过okhttp实现的，所以可以通过okHttp来设置一些连接参数
         * 如超时等
         */
        retrofit = new Retrofit.Builder()
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

}