package com.mengxin.img.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.mengxin.img.ImgApp;

/**
 * 描述：应用包相关的工具类
 *
 */

public class PackageUtils {

    public static int packageCode() {
        PackageManager manager = ImgApp.getContext().getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(ImgApp.getContext().getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String packageName() {
        PackageManager manager = ImgApp.getContext().getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(ImgApp.getContext().getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
}
