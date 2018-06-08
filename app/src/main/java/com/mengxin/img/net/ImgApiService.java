package com.mengxin.img.net;

import com.alibaba.fastjson.JSONObject;
import com.mengxin.img.data.dto.Author;
import com.mengxin.img.data.dto.Comment;
import com.mengxin.img.data.dto.Img;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ImgApiService {
    @GET("img/getIllList/{num}")
    Observable<ArrayList<Img>> fetchIllImg(
            @Path("num") int num
    );

    @GET("author/getAuthorDetail/{id}")
    Observable<Author> getAuthorDetail(
            @Path("id") long id
    );

    @GET("img/getById/{id}")
    Observable<Img> getImgDetail(
            @Path("id") long id
    );

    @POST("author/loginByPassWord")
    Observable<String> loginByPassWord(
            @Body Author author
    );

    @POST("author/regist")
    Observable<Boolean> regist(
            @Body JSONObject object
    );

    @GET("author/isNameUse/{name}")
    Observable<Boolean> isNameUse(
            @Path("name") String name
    );

    @POST("author/getVcode")
    Observable<JSONObject> getVcode(
            @Body String phoneNumber
    );

    @GET("img/getHeadImg/{id}")
    Observable<String> getHeadImg(
            @Path("id") Long id
    );

    @POST("author/likeImg")
    Observable<Boolean> likeImg(
            @Body JSONObject object
    );

    @POST("author/unLikeImg")
    Observable<Boolean> unLikeImg(
            @Body JSONObject object
    );

    @POST("author/isLikeImg")
    Observable<Boolean> isLikeImg(
            @Body JSONObject object
    );

    @GET("img/getAuthorImg/{id}/{num}")
    Observable<ArrayList<Img>> getAuthorImg(
            @Path("id") long id,
            @Path("num") int num
    );

    @GET("author/getFocusList/{id}")
    Observable<ArrayList<Author>> getFocusList(
            @Path("id") long id
    );

    @GET("author/getFansList/{id}")
    Observable<ArrayList<Author>> getFansList(
            @Path("id") long id
    );

    @GET("author/getFocusNum/{id}")
    Observable<Integer> getFocusNum(
            @Path("id") long id
    );

    @GET("author/getFansNum/{id}")
    Observable<Integer> getFansNum(
            @Path("id") long id
    );

    @GET("author/isFocus/{focus}/{fans}")
    Observable<Boolean> isFocus(
            @Path("focus") long focus,
            @Path("fans") long fans
    );

    @POST("author/focus")
    Observable<Boolean> focus(
            @Body JSONObject object
    );

    @POST("author/unFocus")
    Observable<Boolean> unFocus(
            @Body JSONObject object
    );

    @POST("img/saveComment")
    Observable<Boolean> saveComment(
            @Body Comment comment
    );

    @GET("img/getComment/{id}")
    Observable<ArrayList<Comment>> getComment(
            @Path("id") long id
    );
}
