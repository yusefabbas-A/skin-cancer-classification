package com.yojoo.skincancerclassifier.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.yojoo.skincancerclassifier.Database.MyTypeConverters;

import java.util.List;

@Entity(tableName = "Report")
public class Report {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "jop_key")
    private String Key;

    private String Classification;

    private String date;

    @TypeConverters(MyTypeConverters.class)
    private List<Sample> sampleList;

    //////////////////////////////

    public Report(){}


    @Ignore
    public Report(String date, String key) {
        this.date = date;
        this.Key = key;

    }

    @Ignore
    public Report(int id, String classification, String date) {
        this.date = date;
        this.id = id;
        this.Classification = classification;

    }

    @Ignore
    public Report(String classification, List<Sample> samples) {
        Classification = classification;
        sampleList = samples;
    }


    public List<Sample> getSampleList() {
        return sampleList;
    }

    public void setSampleList(List<Sample> sampleList) {
        this.sampleList = sampleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getClassification() {
        return Classification;
    }

    public void setClassification(String classification) {
        Classification = classification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
