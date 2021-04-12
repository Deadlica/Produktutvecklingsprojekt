package com.firstapp.vitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    public static final int TEXT_REQUEST = 5;
    private static final String LOG_TAG = ProfileActivity.class.getSimpleName();
    private TextView mReplyName;
    private TextView mReplyAge;
    private TextView mReplyGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //To be able to insert the reply intent messages
        mReplyName = findViewById(R.id.text_name_reply);
        mReplyAge = findViewById(R.id.text_age_reply);
        mReplyGender = findViewById(R.id.text_gender_reply);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_profile:
                        return true;
                    case R.id.ic_settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void launchAdd(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(getApplicationContext(), AddActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TEXT_REQUEST) {
            if(resultCode == RESULT_OK) {
                String replyName = data.getStringExtra(AddActivity.EXTRA_REPLYNAME);
                String replyLastName = data.getStringExtra(AddActivity.EXTRA_REPLYNAME2);
                replyName += " " + replyLastName;
                String replyAge = data.getStringExtra(AddActivity.EXTRA_REPLYAGE);
                String replyGender = data.getStringExtra(AddActivity.EXTRA_REPLYGENDER);

                mReplyName.setText(replyName);
                mReplyName.setVisibility(View.VISIBLE);

                mReplyAge.setText(replyAge);
                mReplyAge.setVisibility(View.VISIBLE);

                mReplyGender.setText(replyGender);
                mReplyGender.setVisibility(View.VISIBLE);
            }
        }
    }
}