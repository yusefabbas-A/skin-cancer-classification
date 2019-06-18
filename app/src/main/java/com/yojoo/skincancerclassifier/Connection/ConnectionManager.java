package com.yojoo.skincancerclassifier.Connection;

import com.yojoo.skincancerclassifier.Data.MyResponse;
import com.yojoo.skincancerclassifier.Data.SampleResult;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public class ConnectionManager {
    private static ConnectionManager connectionManager;
    private SkinAPI api;
//http://10.0.2.2:3000 https://7ef5fa43.ngrok.io
    private ConnectionManager (){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://d4447a0d.ngrok.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        api = retrofit.create(SkinAPI.class);

    }

    public static ConnectionManager getInstance(){
        if (connectionManager == null)
            connectionManager = new ConnectionManager();

        return connectionManager;
    }

    @GET("/api/skins/reports/{job_key}")
    public Call<SampleResult> getSamples(String key) {
        return api.getSamples(key);
    }


    @Multipart
    @POST("/api/skins/subjects/")
    public Call<MyResponse> uploadImage(MultipartBody.Part image) {
        return api.uploadImage(image);
    }
}
//    private static  Retrofit retro = null;
//    private static final String base_url = "http://10.0.2.2:3000" ;
//
//
//    private static Retrofit getClient()
//    {
//        if(retro == null)
//        {
//            retro  = new Retrofit.Builder()
//                    .baseUrl(base_url)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//
//        return  retro;
//    }
//
//    public static SkinAPI getApiServices()
//    {
//        return  getClient().create(SkinAPI.class);
//
//    }


