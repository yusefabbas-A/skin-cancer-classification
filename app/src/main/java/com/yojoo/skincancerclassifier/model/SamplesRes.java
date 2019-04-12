package com.yojoo.skincancerclassifier.model;

import java.util.List;

public class SamplesRes {


    public String id;

    public int opStatus;

    public String message;

    public String imageUrl;

    public List<String> responseAvailableSamples = null;

    public SamplesScores samplesScores;

    public String getId() {
        return id;
    }

    public int getOpStatus() {
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

    //    public String id;
//
//    public int opStatus;
//
//    public String message;
//
//    public String imageUrl;
//
//    public List<SamplesScores> responseAvailableSamples = null;
//
//    public SamplesScores samplesScores;
//
//
//    public String getId() {
//        return id;
//    }
//
//    public int getOpStatus() {
//        return opStatus;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public List<SamplesScores> getResponseAvailableSamples() {
//        return  responseAvailableSamples;
//    }
//
//    public SamplesScores getSamplesScores() {
//        return samplesScores;
//    }
}
