package com.example.jamie.petmonitoring;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Entry.class}, version = 1, exportSchema = false)

public abstract class EntryRoomDatabase extends RoomDatabase {

    public abstract EntryDao entryDao();

    private static EntryRoomDatabase INSTANCE;

    public static EntryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EntryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EntryRoomDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final EntryDao mDao;
        String[] entries = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(EntryRoomDatabase db) {
            mDao = db.entryDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //If we have no entries, then create the initial list of entries
            if(mDao.getAnyEntry().length < 1){
                for(int i = 0; i <= entries.length - 1; i++){
                    Entry entry = new Entry(entries[i]);
                    mDao.insert(entry);
                }
            }
            return null;
        }
    }
}
