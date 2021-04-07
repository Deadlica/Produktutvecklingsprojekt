package com.firstapp.navigationdrawer.ui.add;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firstapp.navigationdrawer.R;

public class AddFragment extends Fragment {

    private AddViewModel addViewModel;

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        // TODO: Use the ViewModel
    }

}