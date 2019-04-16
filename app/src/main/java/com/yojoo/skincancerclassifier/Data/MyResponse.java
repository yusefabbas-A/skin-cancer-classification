package com.yojoo.skincancerclassifier.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyResponse {
    private String key;

    private String message;

    private boolean opSuccess;

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOpSuccess() {
        return opSuccess;
    }
}
