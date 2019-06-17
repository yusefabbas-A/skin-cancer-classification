
package com.yojoo.skincancerclassifier.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SampleResult {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("samples_scores")
    @Expose
    private List<Sample> samples;
    @SerializedName("classified_as")
    @Expose
    private String classifiedAs;

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public String getClassifiedAs() {
        return classifiedAs;
    }


}
