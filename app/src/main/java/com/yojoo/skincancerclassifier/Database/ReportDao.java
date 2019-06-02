package com.yojoo.skincancerclassifier.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.yojoo.skincancerclassifier.Data.Report;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ReportDao {

    @Insert
    void insertReport(Report report);

    @Query("SELECT * FROM Report")
    List<Report> getAllReports();

    @Query("SELECT message FROM Report")
    List<String>getAllMessages();

    @Update
    void update(Report report);

    @Delete
    void delete(Report report);

    @Query("UPDATE Report SET Classification = :classification WHERE id = :Rid")
    int UpdateClass(long Rid, String classification);

}

