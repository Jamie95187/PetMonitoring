package com.example.jamie.petmonitoring;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("SELECT * from entry_table LIMIT 1")
    Entry[] getAnyEntry();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Entry entry);

    @Query("DELETE FROM entry_table")
    void deleteAll();

    @Query("SELECT * from entry_table ORDER BY entry ASC")
    LiveData<List<Entry>> getAllEntries();

    @Delete
    void deleteEntry(Entry entry);

}
