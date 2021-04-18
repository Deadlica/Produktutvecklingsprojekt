package com.firstapp.vitals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private List<Contact> contacts = new ArrayList<>();

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currentContact = contacts.get(position);
        String name = currentContact.getFirstName();
        name += " ";
        name += currentContact.getLastName();
        holder.textViewName.setText(name);
        holder.textViewAge.setText(currentContact.getAge());
        holder.textViewGender.setText(currentContact.getGender());
        holder.textViewAlarm.setText(currentContact.getAlarm());
        holder.textViewPriority.setText(String.valueOf(currentContact.getPriority()));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public Contact getContactAt(int position) {
        return contacts.get(position);
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView textViewFirstName;
        private TextView textViewLastName;
        private TextView textViewName;
        private TextView textViewAge;
        private TextView textViewGender;
        private TextView textViewAlarm;
        private TextView textViewPriority;

        public ContactHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewAge = itemView.findViewById(R.id.text_view_age);
            textViewGender = itemView.findViewById(R.id.text_view_gender);
            textViewAlarm = itemView.findViewById(R.id.text_view_alarm);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
