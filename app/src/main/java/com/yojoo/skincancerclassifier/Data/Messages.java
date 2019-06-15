package com.yojoo.skincancerclassifier.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Messages")
public class Messages {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String message;
    private String date;

    public Messages(){}

    @Ignore
    public Messages(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
