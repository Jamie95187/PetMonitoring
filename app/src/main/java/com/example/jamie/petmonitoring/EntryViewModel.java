package com.example.jamie.petmonitoring;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.jamie.petmonitoring.Entry;
import com.example.jamie.petmonitoring.EntryRepository;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {

    private EntryRepository mRepository;
    private LiveData<List<Entry>> mAllEntries;

    public EntryViewModel(Application application){
        super(application);
        mRepository = new EntryRepository(application);
        mAllEntries = mRepository.getAllEntries();
    }

    LiveData<List<Entry>> getAllEntries(){
        return mAllEntries;
    }

    public void insert(Entry entry){
        mRepository.insert(entry);
    }

    public void deleteAll(){mRepository.deleteAll();}

    public void deleteEntry(Entry entry){mRepository.deleteEntry(entry);}
}
