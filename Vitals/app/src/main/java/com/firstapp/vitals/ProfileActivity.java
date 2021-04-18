package com.firstapp.vitals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firstapp.vitals.room.Contact;
import com.firstapp.vitals.room.ContactAdapter;
import com.firstapp.vitals.room.ContactViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    public static final int ADD_CONTACT_REQUEST = 1;
    public static final int EDIT_CONTACT_REQUEST = 2;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FloatingActionButton buttonAddContact = findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AddEditContactActivity.class);
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
                adapter.submitList(contacts);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ProfileActivity.this, "Profile deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent = new Intent(ProfileActivity.this, AddEditContactActivity.class);
                intent.putExtra(AddEditContactActivity.EXTRA_ID, contact.getId());
                intent.putExtra(AddEditContactActivity.EXTRA_REPLYNAME, contact.getFirstName());
                intent.putExtra(AddEditContactActivity.EXTRA_REPLYNAME2, contact.getLastName());
                intent.putExtra(AddEditContactActivity.EXTRA_REPLYAGE, contact.getAge());
                intent.putExtra(AddEditContactActivity.EXTRA_REPLYGENDER, contact.getGender());
                intent.putExtra(AddEditContactActivity.EXTRA_REPLYALARM, contact.getAlarm());
                intent.putExtra(AddEditContactActivity.EXTRA_REPLYPRIO, contact.getPriority());
                startActivityForResult(intent, EDIT_CONTACT_REQUEST);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.ic_profile:
                        return true;
                    case R.id.ic_settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK) {
            String replyName = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYNAME);
            String replyLastName = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYNAME2);
            //replyName += " " + replyLastName;
            String replyAge = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYAGE);
            String replyGender = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYGENDER);
            String replyAlarm = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYALARM);
            int priority = data.getIntExtra(AddEditContactActivity.EXTRA_REPLYPRIO, 1);

            Contact contact = new Contact(replyName, replyLastName, replyAge, replyGender, replyAlarm, priority);
            contactViewModel.insert(contact);

            Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_CONTACT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditContactActivity.EXTRA_ID, -1);

            if(id == -1) {
                Toast.makeText(this, "Contact can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String replyName = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYNAME);
            String replyLastName = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYNAME2);
            //replyName += " " + replyLastName;
            String replyAge = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYAGE);
            String replyGender = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYGENDER);
            String replyAlarm = data.getStringExtra(AddEditContactActivity.EXTRA_REPLYALARM);
            int priority = data.getIntExtra(AddEditContactActivity.EXTRA_REPLYPRIO, 1);

            Contact contact = new Contact(replyName, replyLastName, replyAge, replyGender, replyAlarm, priority);
            contact.setId(id);
            contactViewModel.update(contact);

            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Profile not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_profiles:
                contactViewModel.deleteAllContacts();
                Toast.makeText(this, "All profiles deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}