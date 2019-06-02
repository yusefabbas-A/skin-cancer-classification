package com.yojoo.skincancerclassifier.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.yojoo.skincancerclassifier.Data.Report;

@Database(entities = {Report.class}, version = 1)
public abstract class DatabaseBuilder extends RoomDatabase {

    public abstract ReportDao getReportDao();

}
