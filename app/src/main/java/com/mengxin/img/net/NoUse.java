package com.mengxin.img.net;

import com.mengxin.img.ImgInit;
import com.mengxin.img.data.dto.Img;

import java.util.ArrayList;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class NoUse {
    private static String BASE_URL = "http://www.ssdr.fun/";

    public APIs apis;

    private static NoUse instance;

    public static NoUse getInstance() {
        if (instance == null) {
            instance = new NoUse();
        }
        return instance;
    }

    private NoUse() {
        Retrofit storeRestAPI = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(ImgInit.mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        apis = storeRestAPI.create(APIs.class);
    }

    public interface APIs{

        /* 插画时间顺序图片 */
        @GET("图片/getIllList/{num}")
        Flowable<ArrayList<Img>> fetchIll(
                @Path("num") int num
        );

    }
}
