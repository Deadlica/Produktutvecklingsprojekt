package com.firstapp.vitals.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.vitals.R;

public class ContactAdapter extends ListAdapter<Contact, ContactAdapter.ContactHolder> {
    private OnItemClickListener listener;

    public ContactAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(Contact oldItem, Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Contact oldItem, Contact newItem) {
            return oldItem.getFirstName().equals(newItem.getFirstName()) &&
                   oldItem.getLastName().equals(newItem.getLastName()) &&
                   oldItem.getAge().equals(newItem.getAge()) &&
                   oldItem.getGender().equals(newItem.getGender()) &&
                    oldItem.getAlarm().equals(newItem.getAlarm()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currentContact = getItem(position);
        String name = currentContact.getFirstName();
        name += " ";
        name += currentContact.getLastName();
        holder.textViewName.setText(name);
        holder.textViewAge.setText(currentContact.getAge());
        holder.textViewGender.setText(currentContact.getGender());
        holder.textViewAlarm.setText(currentContact.getAlarm());
        holder.textViewPriority.setText(String.valueOf(currentContact.getPriority()));
    }

    public Contact getContactAt(int position) {
        return getItem(position);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
