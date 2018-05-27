package com.mengxin.img.data.dto;


import java.util.ArrayList;
import java.util.List;

public class Author{
    private Long id;
    private String phoneNumber;
    private String passWord;
    private String name;
    private String headImg;
    private String bgImg;
    private String introduction;
    private ArrayList<Img> imgList = new ArrayList<Img>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public ArrayList<Img> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<Img> imgList) {
        this.imgList = imgList;
    }
}
