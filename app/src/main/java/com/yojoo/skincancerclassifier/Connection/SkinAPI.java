package com.yojoo.skincancerclassifier.Connection;



import com.yojoo.skincancerclassifier.Data.MyResponse;
import com.yojoo.skincancerclassifier.Data.SampleResult;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SkinAPI {

    @GET("/api/skins/reports/1?")
    Call<SampleResult> getSamples(@Query("key") String theKey);

    @Multipart
    @POST("/api/skins/subjects/")
    Call<MyResponse> uploadImage(@Part MultipartBody.Part image);
}
