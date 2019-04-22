package com.example.jamie.petmonitoring;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EntryListAdapter extends RecyclerView.Adapter<EntryListAdapter.EntryViewHolder> {

    private final LayoutInflater mInflater;
    private List<Entry> mEntries; // Cached copy of entries

    EntryListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position){
        if(mEntries != null){
            Entry current = mEntries.get(position);
            holder.entryItemView.setText(current.getEntry());
        } else {
            // Covers the case of data not being ready yet.
            holder.entryItemView.setText("No Word");
        }
    }

    void setEntries(List<Entry> entries){
        mEntries = entries;
        notifyDataSetChanged();
    }

    // getItemCount() is called many time, and when it is first called,
    // mEntries has not been updated (means initially, it's null and we can't return null).
    @Override
    public int getItemCount(){
        if(mEntries != null)
            return mEntries.size();
        else return 0;
    }

    class EntryViewHolder extends RecyclerView.ViewHolder{
        private final TextView entryItemView;

        private EntryViewHolder(View itemView){
            super(itemView);
            entryItemView = itemView.findViewById(R.id.textView);
        }
    }

    //Method to access the entry at a given position.
    public Entry getEntryAtPosition(int position){
        return mEntries.get(position);
    }

}
