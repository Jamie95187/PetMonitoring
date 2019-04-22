package com.example.jamie.petmonitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    // This constant uses the name of the class itself as the tag.
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // ImageView button picture of the dog.
    private ImageView dogButton;
    // Button which shows the dog diary.
    private Button diaryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dogButton = (ImageView) findViewById(R.id.imageButtonDog);
        Picasso.with(this)
                .load(R.drawable.dog_pic)
                .resize(500,500)
                .into(dogButton);
        dogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSecondActivity();
            }
        });

        diaryButton = (Button) findViewById(R.id.historyButton);
        diaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchThirdActivity();
            }
        });

    }

    public void launchSecondActivity() {
        Log.d(LOG_TAG, "Dog button clicked!");
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void launchThirdActivity(){
        Log.d(LOG_TAG, "Loading dog diary");
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

}
