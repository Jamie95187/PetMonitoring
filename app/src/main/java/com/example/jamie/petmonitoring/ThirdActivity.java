package com.example.jamie.petmonitoring;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private EntryViewModel mEntryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final EntryListAdapter adapter = new EntryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEntryViewModel = ViewModelProviders.of(this).get(EntryViewModel.class);

        mEntryViewModel.getAllEntries().observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable final List<Entry> entries) {
                // Update the cached copy of the words in the adapter.
                adapter.setEntries(entries);
            }
        });

        Intent intent = this.getIntent();
        if(intent.hasExtra(SecondActivity.EXTRA_REPLY)) {
            Entry entry = new Entry(intent.getStringExtra(SecondActivity.EXTRA_REPLY));
            CharSequence dateCS = "Select a date";
            if (!entry.getEntry().contains(dateCS)) {
                mEntryViewModel.insert(entry);
            }
        }

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target){
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
                        int position = viewHolder.getAdapterPosition();
                        Entry myEntry = adapter.getEntryAtPosition(position);
                        Toast.makeText(ThirdActivity.this, "Deleting " + myEntry.getEntry(),
                                Toast.LENGTH_LONG).show();

                        // Delete the entry
                        mEntryViewModel.deleteEntry(myEntry);
                    }
                });
        helper.attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            // Delete all the existing data
            mEntryViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Trying different method to transfer intents between activities.
    /*public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ENTRY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Entry entry = new Entry(data.getStringExtra(SecondActivity.EXTRA_REPLY));
            mEntryViewModel.insert(entry);
        }
        else{
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }*/

}
