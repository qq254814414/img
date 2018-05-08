package com.mengxin.img;

import android.app.Application;

/**
 * 描述：Application类
 *
 */
public class ImgApp extends Application {

    private static ImgApp context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ImgInit.initTimber();
        ImgInit.initOKHttp(this);
    }

    public static ImgApp getContext() {
        return context;
    }
}
