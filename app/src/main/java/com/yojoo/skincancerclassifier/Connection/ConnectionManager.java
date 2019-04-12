package com.yojoo.skincancerclassifier.Connection;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public class ConnectionManager {
    private static  Retrofit retro = null;
    private static final String base_url = "http://10.0.2.2:3000" ;


    private static Retrofit getClient()
    {
        if(retro == null)
        {
            retro  = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return  retro;
    }

    public static SkinAPI getApiServices()
    {
        return  getClient().create(SkinAPI.class);

    }

}
