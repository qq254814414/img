package com.mengxin.img.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mengxin.img.ui.activity.MainActivity;

/**
 * 描述：网络相关工具类
 *
 */
public class NetworkUtils {
    /** 获取网络信息 */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /** 判断网络是否可用 */
    public static boolean isAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    /** 判断用户是否登录 */
    public static Long isLogin(Context context){
        SharedPreferences sp = context.getSharedPreferences("loginToken",Context.MODE_PRIVATE);
        String authorId = sp.getString("authorId","0");
        return Long.parseLong(authorId);
    }
}
