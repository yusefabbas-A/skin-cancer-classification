package com.yojoo.skincancerclassifier.Database;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.yojoo.skincancerclassifier.Data.Report;

import java.util.List;

public class DatabaseManager {
    private static DatabaseManager databaseManager;
    private static final String DATABASE_NAME = "SkinDB";
//    private DatabaseBuilder databaseBuilder;
    private ReportDao reportDao;

    public static DatabaseManager getInstance(Context context){
        if (databaseManager == null)
            databaseManager = new DatabaseManager(context);

        return databaseManager;

    }

    private DatabaseManager(Context context){
        DatabaseBuilder databaseBuilder = Room
                .databaseBuilder(context, DatabaseBuilder.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        reportDao = databaseBuilder.getReportDao();
    }

    @Insert
    public void insertReport(Report report) {
        reportDao.insertReport(report);
    }

    @Query("SELECT * FROM Report")
    public List<Report> getAllReports() {
        return reportDao.getAllReports();
    }

    @Query("SELECT message FROM Report")
    public List<String> getAllMessages() {
        return reportDao.getAllMessages();
    }

}
