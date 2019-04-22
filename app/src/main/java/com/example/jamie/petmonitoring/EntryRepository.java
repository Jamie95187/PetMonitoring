package com.example.jamie.petmonitoring;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class EntryRepository {
    private EntryDao mEntryDao;
    private LiveData<List<Entry>> mAllEntries;

    public EntryRepository(Application application){
        EntryRoomDatabase db = EntryRoomDatabase.getDatabase(application);
        mEntryDao = db.entryDao();
        mAllEntries = mEntryDao.getAllEntries();
    }

    public LiveData<List<Entry>> getAllEntries(){
        return mAllEntries;
    }

    public void insert (Entry entry){
        new insertAsyncTask(mEntryDao).execute(entry);
    }

    private static class insertAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao mAsyncTaskDao;

        insertAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllEntriesAsyncTask extends AsyncTask<Void, Void, Void>{
        private EntryDao mAsyncTaskDao;
        deleteAllEntriesAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteAll(){
        new deleteAllEntriesAsyncTask(mEntryDao).execute();
    }

    private static class deleteEntryAsyncTask extends AsyncTask<Entry, Void, Void>{
        private EntryDao mAsyncTaskDao;
        deleteEntryAsyncTask(EntryDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params){
            mAsyncTaskDao.deleteEntry(params[0]);
            return null;
        }
    }

    public void deleteEntry(Entry entry){
        new deleteEntryAsyncTask(mEntryDao).execute(entry);
    }
}
