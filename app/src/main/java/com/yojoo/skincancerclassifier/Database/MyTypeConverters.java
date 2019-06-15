package com.yojoo.skincancerclassifier.Database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yojoo.skincancerclassifier.Data.Sample;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class MyTypeConverters {

    @TypeConverter
    public static List<Sample> StringToSampleList(String data){
        if (data == null){
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Sample>>() {}.getType();
        return gson.fromJson(data,listType);
    }

    @TypeConverter
    public static String SampleListToString (List<Sample> samples){
        Gson gson = new Gson();
        return gson.toJson(samples);
    }


}
