package com.emmanuel_yegon.cipherguard.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = "messages")
public class Message implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "plain_text")
    String plain_text;

    @ColumnInfo(name = "encrypted_text")
    String encrypted_text;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "creation_time")
    Date creation_time;


    public Message() {

    }

    public Message(String plain_text, String encrypted_text, Date creation_time) {
        this.plain_text = plain_text;
        this.encrypted_text = encrypted_text;
        this.creation_time = creation_time;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPlain_text() {
        return plain_text;
    }

    public void setPlain_text(String plain_text) {
        this.plain_text = plain_text;
    }

    public String getEncrypted_text() {
        return encrypted_text;
    }

    public void setEncrypted_text(String encrypted_text) {
        this.encrypted_text = encrypted_text;
    }

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }
}
