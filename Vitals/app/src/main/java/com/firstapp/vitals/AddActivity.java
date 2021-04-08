package com.firstapp.vitals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    Button select_image;
    ImageView profile_picture;
    int SELECT_PICTURE = 200;
    EditText firstName, lastName, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        select_image = findViewById(R.id.select_image);
        profile_picture = findViewById(R.id.profile_picture);

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        Spinner genderDropdown = findViewById(R.id.gender);
        String[] genderOptions = new String[] {"Male", "Female", "Gender"};
        HintAdapter hintAdapter = new HintAdapter(this, android.R.layout.simple_list_item_1, genderOptions);
        genderDropdown.setAdapter(hintAdapter);
        genderDropdown.setSelection(hintAdapter.getCount());

        Spinner alarmDropdown = findViewById(R.id.alarm);
        String[] alarmOptions = new String[] {"Silke", "Sluttning", "Stjärnskådning", "Stråla", "Topp", "Vid kusten", "Alarmsignal"};
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

        if(resultCode == RESULT_OK) {
            if(requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if(null != selectedImageUri) {
                    profile_picture.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public void cancelAdd(View view) {
        finish();
    }

    public void saveAdd(View view) {
        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        age = findViewById(R.id.Age);
        
    }
}