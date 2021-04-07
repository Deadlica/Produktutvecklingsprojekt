package com.firstapp.vitals;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Spinner genderDropdown = findViewById(R.id.gender);
        String[] genderOptions = new String[] {"Male", "Female", "Gender"};
        HintAdapter hintAdapter = new HintAdapter(this, android.R.layout.simple_list_item_1, genderOptions);
        genderDropdown.setAdapter(hintAdapter);
        genderDropdown.setSelection(hintAdapter.getCount());

        Spinner alarmDropdown = findViewById(R.id.alarm);
        String[] alarmOptions = new String[] {"Silke", "Sluttning", "Stjärnskådning", "Stråla", "Topp", "Vid kusten", "Alarmsignal"};
        HintAdapter hintAdapter1 = new HintAdapter(this, android.R.layout.simple_list_item_1, alarmOptions);
        alarmDropdown.setAdapter(hintAdapter);
        alarmDropdown.setSelection(hintAdapter.getCount());
    }
}