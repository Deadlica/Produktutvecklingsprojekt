package com.firstapp.vitals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    public static final int ADD_CONTACT_REQUEST = 1;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FloatingActionButton buttonAddContact = findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_CONTACT_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        final ContactAdapter adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                adapter.setContacts(contacts);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK) {
                String replyName = data.getStringExtra(AddActivity.EXTRA_REPLYNAME);
                String replyLastName = data.getStringExtra(AddActivity.EXTRA_REPLYNAME2);
                //replyName += " " + replyLastName;
                String replyAge = data.getStringExtra(AddActivity.EXTRA_REPLYAGE);
                String replyGender = data.getStringExtra(AddActivity.EXTRA_REPLYGENDER);
                String replyAlarm = data.getStringExtra(AddActivity.EXTRA_REPLYALARM);
                int priority = data.getIntExtra(AddActivity.EXTRA_REPLYPRIO, 1);

                Contact contact = new Contact(replyName, replyLastName, replyAge, replyGender, replyAlarm, priority);
                contactViewModel.insert(contact);

            Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Profile not saved", Toast.LENGTH_SHORT).show();
        }
    }

}