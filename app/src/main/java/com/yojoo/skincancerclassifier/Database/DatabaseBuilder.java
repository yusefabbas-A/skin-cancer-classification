package com.yojoo.skincancerclassifier.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.yojoo.skincancerclassifier.Data.Messages;
import com.yojoo.skincancerclassifier.Data.Report;

@Database(entities = {Report.class, Messages.class},version = 2)
public abstract class DatabaseBuilder extends RoomDatabase {

    public abstract ReportDao getReportDao();

    public abstract MessagesDao getMessagesDao();

}
