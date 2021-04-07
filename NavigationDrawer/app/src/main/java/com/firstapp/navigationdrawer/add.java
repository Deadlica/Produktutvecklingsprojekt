package com.firstapp.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.firstapp.navigationdrawer.ui.main.AddFragment;

public class add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AddFragment.newInstance())
                    .commitNow();
        }
    }
}