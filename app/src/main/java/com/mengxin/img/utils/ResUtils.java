package com.mengxin.img.utils;


import com.mengxin.img.ImgApp;

/**
 * 描述：获取文件资源工具类
 *
 */

public class ResUtils {
    /* 获取文件资源 */
    public static String getString(int strId) {
        return ImgApp.getContext().getResources().getString(strId);
    }
    /* 获取颜色 */
    public static int getColor(int colId){
        return ImgApp.getContext().getResources().getColor(colId);
    }
}
