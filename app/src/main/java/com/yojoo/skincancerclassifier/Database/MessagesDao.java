package com.yojoo.skincancerclassifier.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.yojoo.skincancerclassifier.Data.Messages;

import java.util.List;

@Dao
public interface MessagesDao {

    @Insert
    void insertMessage(Messages... messages);

    @Update
    void update(Messages messages);

    @Delete
    void delete(Messages messages);

    @Query("SELECT * FROM Messages")
    List<Messages> getAllMessages();

}
