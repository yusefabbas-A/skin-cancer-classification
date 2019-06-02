package com.yojoo.skincancerclassifier.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity
public class Report {
    @PrimaryKey(autoGenerate = true)
    private String id;

    private String Key;

    private String Classification;

    private String date;

    @ColumnInfo(name = "message")
    private String message;

    //////////////////////////////


    public Report(String id, String classification, String date) {
        this.id = id;
        Classification = classification;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
