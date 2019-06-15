package com.yojoo.skincancerclassifier.Database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.Update;
import android.content.Context;

import com.yojoo.skincancerclassifier.Data.Messages;
import com.yojoo.skincancerclassifier.Data.Report;

import java.util.List;

public class DatabaseManager {
    private static DatabaseManager databaseManager;
    private static final String DATABASE_NAME = "SkinDB";
//    private DatabaseBuilder databaseBuilder;
    private ReportDao reportDao;
    private MessagesDao messagesDao;


    public static DatabaseManager getInstance(Context context){
        if (databaseManager == null)
            databaseManager = new DatabaseManager(context);

        return databaseManager;

    }

    private DatabaseManager(Context context){
        DatabaseBuilder databaseBuilder = Room
                .databaseBuilder(context, DatabaseBuilder.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        reportDao = databaseBuilder.getReportDao();
        messagesDao = databaseBuilder.getMessagesDao();

    }

    //ReportDao Delegate Methods

    @Insert
    public void insertReport(Report... report) {
        reportDao.insertReport(report);
    }

    @Query("SELECT * FROM Report")
    public List<Report> getAllReports() {
        return reportDao.getAllReports();
    }

    @Query("SELECT id, Classification, date FROM Report")
    public List<Report> getReportShow() {
        return reportDao.getReportShow();
    }

    @Update
    public void update(Report report) {
        reportDao.update(report);
    }

    @Delete
    public void delete(Report report) {
        reportDao.delete(report);
    }

    @Query("UPDATE Report SET Classification = :classification WHERE id = :Rid")
    public int UpdateClass(long Rid, String classification) {
        return reportDao.UpdateClass(Rid, classification);
    }

    //MessagesDao Delegate Methods

    @Insert
    public void insertMessage(Messages... messages) {
        messagesDao.insertMessage(messages);
    }

    @Update
    public void update(Messages messages) {
        messagesDao.update(messages);
    }

    @Delete
    public void delete(Messages messages) {
        messagesDao.delete(messages);
    }

    @Query("SELECT * FROM Messages")
    public List<Messages> getAllMessages() {
        return messagesDao.getAllMessages();
    }
}
