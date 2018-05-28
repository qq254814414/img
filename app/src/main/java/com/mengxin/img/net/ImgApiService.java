package com.mengxin.img.net;

import com.mengxin.img.data.dto.Author;
import com.mengxin.img.data.dto.Img;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImgApiService {
    @GET("图片/getIllList/{num}")
    Observable<ArrayList<Img>> fetchIllImg(
            @Path("num") int num
    );
    @GET("author/getAuthorDetail/{id}")
    Observable<Author> getAuthorDetail(
            @Path("id") long id
    );
    @GET("图片/getById/{id}")
    Observable<Img> getImgDetail(
            @Path("id") long id
    );
}
