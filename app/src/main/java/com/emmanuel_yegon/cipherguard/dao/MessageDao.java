package com.emmanuel_yegon.cipherguard.dao;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.emmanuel_yegon.cipherguard.Model.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert(onConflict = REPLACE)
    void saveItem(Message message);

    @Query("select * from messages order by creation_time desc")
    List<Message> getAllMessages();

    @Delete
    void delete(Message message);

    @Query("delete from messages")
    void deleteAllMessages();



}
