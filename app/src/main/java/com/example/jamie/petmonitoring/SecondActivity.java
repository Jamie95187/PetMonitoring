package com.example.jamie.petmonitoring;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // TextView date picker
    private TextView mDisplayDate;
    // Date Picker Dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    // Switch button for day or night time
    private Switch mSwitchButtonDN;
    // Time spinner view
    private Spinner mTimeSpinnerButton;
    // Radio group view
    private RadioGroup mRadioGroup;
    // Individual radio buttons
    private static final int RB1_ID = 0;
    private static final int RB2_ID = 1;
    private static final int RB3_ID = 2;

    // Entry bundle string
    public String mEntry;
    
    
    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // Get intent from clicking the dog button
        Intent intent = getIntent();

        RadioButton rb1 = findViewById(R.id.radioButtonBad);
        RadioButton rb2 = findViewById(R.id.radioButtonOk);
        RadioButton rb3 = findViewById(R.id.radioButtonGood);

        // Declare our date textView
        mDisplayDate = (TextView) findViewById(R.id.textViewDate);

        // Declare switch button
        mSwitchButtonDN = (Switch) findViewById(R.id.switchDN);

        // Set onClickListener to the textViewDate.
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create Calendar object cal to set default settings to current day.
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog object.
                DatePickerDialog dialog = new DatePickerDialog(SecondActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                // We add one to month because 0 = January.
                month = month + 1;

                // Take our month and date and set it to the textView.
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        // Create our spinner object and reference it with findViewById
        mTimeSpinnerButton = findViewById(R.id.spinnerButtonTime);
        // Create ArrayAdapter object to populate our spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.minutes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        if(mTimeSpinnerButton != null){
            mTimeSpinnerButton.setAdapter(adapter);
        }
        // Make the spinner react to a click
        mTimeSpinnerButton.setOnItemSelectedListener(this);

        // Find the radioGroup view by using findById method.
        mRadioGroup = findViewById(R.id.radioGroup);

        final Button button = findViewById(R.id.saveInfoButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){

                int spinnerPos = mTimeSpinnerButton.getSelectedItemPosition();

                    mEntry = mDisplayDate.getText().toString();
                    if(mSwitchButtonDN.isChecked()){
                        mEntry = mEntry + ",  Day";
                    }
                    else{
                        mEntry = mEntry + ",  Night";
                    }

                    // Get the value that is selected in the spinner view (duration of walk in minutes).

                    String minutes = null;
                    switch (spinnerPos){
                        case 0:
                            minutes = "< 20 minutes";
                            break;
                        case 1:
                            minutes = "20-30 minutes";
                            break;
                        case 2:
                            minutes = "30-40 minutes";
                            break;
                        case 3:
                            minutes = "40-50 minutes";
                            break;
                        case 4:
                            minutes = "50+ minutes";
                            break;
                    }
                    mEntry = mEntry + ",  " + minutes;

                    // Get the position of the radio button (quality of walk) and concatenate to the end of mEntry string.
                    int posRadioButton = mRadioGroup.getCheckedRadioButtonId();
                    String qualityOfWalk = null;
                    switch (posRadioButton){
                        case R.id.radioButtonBad:
                            qualityOfWalk = "Bad";
                            break;
                        case R.id.radioButtonOk:
                            qualityOfWalk = "Ok";
                            break;
                        case R.id.radioButtonGood:
                            qualityOfWalk = "Good";
                            break;
                    }
                    mEntry = mEntry + ",  " + qualityOfWalk;

                // You want onClick to open the new activity, send the user to ThirdActivity and add an
                // entry to the ThirdActivity
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra(EXTRA_REPLY,mEntry);
                CharSequence dateCS = "Select a date";
                if(!mEntry.contains(dateCS)){
                    Toast.makeText(SecondActivity.this, mEntry, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SecondActivity.this, "Error no date selected!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Get the item at this position and turn it into a string
        String text = parent.getItemAtPosition(position).toString();
        // Create toast to show it when it is selected
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        int radioId = mRadioGroup.getCheckedRadioButtonId();
    }


}
