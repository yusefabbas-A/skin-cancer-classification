
package com.yojoo.skincancerclassifier.Data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SampleResult {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("op_status")
    @Expose
    public float opStatus;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("response_available_samples")
    @Expose
    public List<String> responseAvailableSamples = null;
    @SerializedName("samples_scores")
    @Expose
    public SamplesScores samplesScores;

    public String getId() {
        return id;
    }

    public float getOpStatus() {
        return opStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getResponseAvailableSamples() {
        return responseAvailableSamples;
    }

    public SamplesScores getSamplesScores() {
        return samplesScores;
    }
}
