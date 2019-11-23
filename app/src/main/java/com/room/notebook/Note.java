package com.room.notebook;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "subject")
    private String subject;

    @ColumnInfo(name = "body")
    private String body;



    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }


}


