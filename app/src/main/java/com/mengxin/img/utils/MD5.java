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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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
        return temp.toString();
    }

    public static String encoderByMd5(String temp) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确认加密算法
        MessageDigest md5 = MessageDigest.getInstance("md5");
        //计算加密结果
        String md5String = md5.digest(temp.getBytes("UTF-8")).toString();
        return md5String;
    }

}
