package com.firstapp.vitals.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String firstName;

    private String lastName;

    private String age;

    private String gender;

    private String alarm;

    private int priority;

    //private Test profilePicture;

    public Contact(String firstName, String lastName, String age, String gender, String alarm, int priority) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.alarm = alarm;
        this.priority = priority;
        //this.profilePicture = profilePicture;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getAlarm() {
        return alarm;
    }

    public int getPriority() {
        return priority;
    }

    /*public ImageView getProfilePicture() {
        return profilePicture;
    }*/
}
