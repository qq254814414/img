package com.mengxin.img.net;

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
}
