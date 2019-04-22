package com.example.jamie.petmonitoring;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "entry_table")
public class Entry {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entry")
    private String mEntry;
    public Entry(@NonNull String entry) {
        this.mEntry = entry;
    }

    public String getEntry(){
        return this.mEntry;
    }

}
