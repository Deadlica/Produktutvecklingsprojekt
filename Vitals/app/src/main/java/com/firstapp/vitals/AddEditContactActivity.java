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

import com.firstapp.vitals.bluetooth.BluetoothActivity;

public class AddEditContactActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.firstapp.vitals.extra.ID";
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

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Profile");
            firstName.setText(intent.getStringExtra(EXTRA_REPLYNAME));
            lastName.setText(intent.getStringExtra(EXTRA_REPLYNAME2));
            age.setText(intent.getStringExtra(EXTRA_REPLYAGE));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_REPLYPRIO, 1));

            String genderCheck = intent.getStringExtra(EXTRA_REPLYGENDER);
            Spinner genderDropdown = findViewById(R.id.Gender);
            String[] genderOptions = new String[]{"Male", "Female", genderCheck};
            HintAdapter hintAdapter = new HintAdapter(this, android.R.layout.simple_list_item_1, genderOptions);
            genderDropdown.setAdapter(hintAdapter);
            genderDropdown.setSelection(hintAdapter.getCount());

            String alarmCheck = intent.getStringExtra(EXTRA_REPLYALARM);
            Spinner alarmDropdown = findViewById(R.id.Alarm);
            String[] alarmOptions = new String[]{"Silke", "Sluttning", "Stjärnskådning", "Stråla", "Topp", "Vid kusten", alarmCheck};
            HintAdapter hintAdapter1 = new HintAdapter(this, android.R.layout.simple_list_item_1, alarmOptions);
            alarmDropdown.setAdapter(hintAdapter1);
            alarmDropdown.setSelection(hintAdapter1.getCount());
        } else {
            setTitle("Add Profile");
        }

        select_image = findViewById(R.id.select_image);
        profile_picture = findViewById(R.id.profile_picture);

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        if(!(intent.hasExtra(EXTRA_ID))) {
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

        if (replyFirstName.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your first name.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (replyLastName.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your last name.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (replyAge.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your age.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_REPLYNAME, replyFirstName);
        data.putExtra(EXTRA_REPLYNAME2, replyLastName);
        data.putExtra(EXTRA_REPLYAGE, replyAge);
        data.putExtra(EXTRA_REPLYGENDER, replyGender);
        data.putExtra(EXTRA_REPLYALARM, replyAlarm);
        data.putExtra(EXTRA_REPLYPRIO, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

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

    public void setSpinText(Spinner spin, String text) {
        for (int i = 0; i < spin.getAdapter().getCount(); i++) {
            if (spin.getAdapter().getItem(i).toString().contains(text)) {
                spin.setSelection(i);
            }
        }
    }

    public void bluetoothSearch(View view) {
        Intent intent = new Intent(this, BluetoothActivity.class);
        startActivity(intent);
    }
}