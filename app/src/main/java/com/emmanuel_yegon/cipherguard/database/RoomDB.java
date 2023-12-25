package com.emmanuel_yegon.cipherguard.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.emmanuel_yegon.cipherguard.Model.Key;
import com.emmanuel_yegon.cipherguard.Model.Message;
import com.emmanuel_yegon.cipherguard.dao.KeyDao;
import com.emmanuel_yegon.cipherguard.dao.MessageDao;

@Database(entities = {Key.class, Message.class}, version = 1, exportSchema = false)
public  abstract class RoomDB  extends RoomDatabase {

    private static RoomDB database;
    private static String DATABASE_NAME="CipherGuard_DB";

    public synchronized static RoomDB getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract MessageDao mainDao();
    public abstract KeyDao keyDao();


}
