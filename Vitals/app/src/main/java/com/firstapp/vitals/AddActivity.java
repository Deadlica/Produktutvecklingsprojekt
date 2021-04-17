package com.firstapp.vitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    public static final String EXTRA_REPLYNAME = "com.firstapp.vitals.extra.REPLYNAME";
    public static final String EXTRA_REPLYNAME2 = "com.firstapp.vitals.extra.REPLYNAME2";
    public static final String EXTRA_REPLYAGE = "com.firstapp.vitals.extra.REPLYAGE";
    public static final String EXTRA_REPLYGENDER = "com.firstapp.vitals.extra.REPLYGENDER";
    public static final String EXTRA_REPLYALARM = "com.firstapp.vitals.extra.REPLYALARM";
    public static final String EXTRA_REPLYPRIO = "com.firstapp.vitals.extra.REPLYPRIO";
    Button select_image;
    ImageView profile_picture;
    int SELECT_PICTURE = 200;

    //Declaring containers for the user input
    private EditText firstName;
    private EditText lastName;
    private EditText age;
    private Spinner gender;
    private Spinner alarm;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Finding corresponding input fields
        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        age = findViewById(R.id.Age);
        gender = findViewById(R.id.Gender);
        alarm = findViewById(R.id.Alarm);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Profile");

        select_image = findViewById(R.id.select_image);
        profile_picture = findViewById(R.id.profile_picture);

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        Spinner genderDropdown = findViewById(R.id.Gender);
        String[] genderOptions = new String[]{"Male", "Female", "Gender"};
        HintAdapter hintAdapter = new HintAdapter(this, android.R.layout.simple_list_item_1, genderOptions);
        genderDropdown.setAdapter(hintAdapter);
        genderDropdown.setSelection(hintAdapter.getCount());

        Spinner alarmDropdown = findViewById(R.id.Alarm);
        String[] alarmOptions = new String[]{"Silke", "Sluttning", "Stjärnskådning", "Stråla", "Topp", "Vid kusten", "Alarmsignal"};
        HintAdapter hintAdapter1 = new HintAdapter(this, android.R.layout.simple_list_item_1, alarmOptions);
        alarmDropdown.setAdapter(hintAdapter1);
        alarmDropdown.setSelection(hintAdapter1.getCount());
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    profile_picture.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void saveContact() {
        String replyFirstName = firstName.getText().toString();
        String replyLastName = lastName.getText().toString();
        String replyAge = age.getText().toString();
        String replyGender = gender.getSelectedItem().toString();
        String replyAlarm = alarm.getSelectedItem().toString();
        int priority = numberPickerPriority.getValue();

        if(replyFirstName.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your first name.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_REPLYNAME, replyFirstName);
        data.putExtra(EXTRA_REPLYNAME2, replyLastName);
        data.putExtra(EXTRA_REPLYAGE, replyAge);
        data.putExtra(EXTRA_REPLYGENDER, replyGender);
        data.putExtra(EXTRA_REPLYALARM, replyAlarm);
        data.putExtra(EXTRA_REPLYPRIO, priority);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contact:
                saveContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void cancelAdd(View view) {
        finish();
    }

    public void saveAdd(View view) {
        String replyFirstName = firstName.getText().toString();
        String replyLastName = lastName.getText().toString();
        String replyAge = age.getText().toString();
        String replyGender = gender.getSelectedItem().toString();
        String replyAlarm = alarm.getSelectedItem().toString();

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLYNAME, replyFirstName);
        replyIntent.putExtra(EXTRA_REPLYNAME2, replyLastName);
        replyIntent.putExtra(EXTRA_REPLYAGE, replyAge);
        replyIntent.putExtra(EXTRA_REPLYGENDER, replyGender);
        replyIntent.putExtra(EXTRA_REPLYALARM, replyAlarm);


        setResult(RESULT_OK, replyIntent);
        finish();

    }
}