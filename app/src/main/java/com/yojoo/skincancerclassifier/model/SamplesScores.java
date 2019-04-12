package com.yojoo.skincancerclassifier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Constructor;
import java.util.List;

public class SamplesScores extends SamplesRes {


    public double nv;

    public double getNv() {
        return nv;
    }

    public double getBcc() {
        return bcc;
    }

    public double getBkl() {
        return bkl;
    }

    public double bcc;
    public double bkl;

//    private String classified_as;
//    private Float score;
//
//
//    SamplesScores (String classified_as,Float score){
//        this.classified_as = classified_as;
//        this.score = score;
//    }
//    public String getClassified_as() {
//        return classified_as;
//    }
//
//    public Float getScore() {
//        return score;
//    }
}
