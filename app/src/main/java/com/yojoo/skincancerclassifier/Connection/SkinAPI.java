package com.yojoo.skincancerclassifier.Connection;



import com.yojoo.skincancerclassifier.model.SamplesRes;
import com.yojoo.skincancerclassifier.model.SamplesScores;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SkinAPI {

    @GET
    Call<List<SamplesRes>> getSamples();

    @Multipart
    @POST("/api/skins/subjects/")
    Call<MyResponse> uploadImage(@Part MultipartBody.Part image);
}
