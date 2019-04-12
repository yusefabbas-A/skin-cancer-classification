
package com.yojoo.skincancerclassifier.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SamplesScores extends SampleResult{

    @SerializedName("nv")
    @Expose
    public float nv;
    @SerializedName("bcc")
    @Expose
    public float bcc;
    @SerializedName("bkl")
    @Expose
    public float bkl;


}
