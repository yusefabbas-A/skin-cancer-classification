
package com.yojoo.skincancerclassifier.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sample {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("score")
    @Expose
    private double score;

    public String getType() {
        return type;
    }

    public Float getScore() {
//        String st = Double.toString(score);
        float f1 = (float) score;
        return f1;
    }
}
