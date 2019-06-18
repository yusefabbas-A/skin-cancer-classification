package com.yojoo.skincancerclassifier.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.yojoo.skincancerclassifier.Data.Report;

import java.util.List;

@Dao
public interface ReportDao {

    @Insert
    void insertReport(Report report);

    @Query("SELECT * FROM Report")
    List<Report> getAllReports();

    @Query("SELECT id, Classification, date FROM Report")
    List<Report> getReportShow();

    @Update
    void update(Report report);

    @Delete
    void delete(Report report);

    @Query("UPDATE Report SET Classification = :classification WHERE id = :Rid")
    void UpdateClass(int Rid, String classification);

    @Query("SELECT jop_key FROM Report WHERE id = :Rid")
    String getKey(int Rid);

    @Query("SELECT Classification FROM Report WHERE id = :Rid")
    String getClassification(int Rid);
}

