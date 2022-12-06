package com.example.medrem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class TherapyFragment extends Fragment {

    public TherapyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void openAddNotificationActivity() {
        Intent intent = new Intent(getActivity(), AddNotificationActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_therapy, container, false);
        Button add_button = view.findViewById(R.id.button);
        add_button.setOnClickListener(v -> openAddNotificationActivity());
        return view;
    }
}