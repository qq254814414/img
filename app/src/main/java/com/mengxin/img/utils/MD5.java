package com.mengxin.img.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    /**
     *加密算法：
     *      SHA1（盐值 + MD5密码）
     *
     * @param originPassword  MD5密码
     * @param salt  盐值
     * @return  加密后的十六进制密码
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String encryption(String originPassword,String salt){
        //加密密码和盐值
        StringBuffer buffer = null;
        try {
            buffer = new StringBuffer().append(encoderByMd5(originPassword)).append(salt);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //计算加密结果
        MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] temp = new byte[0];
        try {
            temp = sha1.digest(buffer.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //返还十六进制

        return bytesToHexString(temp);
    }

    public static String encoderByMd5(String temp) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确认加密算法
        MessageDigest md5 = MessageDigest.getInstance("md5");
        //计算加密结果
        String md5String = android.util.Base64.encodeToString(md5.digest(temp.getBytes("UTF-8")), android.util.Base64.DEFAULT);
        return md5String;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
