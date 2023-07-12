package com.example.madcampweek2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class WriteJournalFragment extends Fragment {

    private EditText journalView;
    private Button saveButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_journal, container, false);
        journalView = view.findViewById(R.id.journalView);
        saveButton = view.findViewById(R.id.save_btn);

        Bundle bundle = getArguments();
        String uid = bundle.getString("uid");
        String category = bundle.getString("category");
        String title = bundle.getString("title");
        String count = bundle.getString("count");
        String time = bundle.getString("time");
        final String[] journal = {bundle.getString("journal")};



        journalView.setText(journal[0]);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                journal[0] = journalView.getText().toString();
                Intent intent = new Intent(requireContext(), PracticeActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("journal", journal[0]);
                intent.putExtra("category", category);
                intent.putExtra("title", title);
                intent.putExtra("count", count);
                intent.putExtra("time", time);
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(WriteJournalFragment.this);
                fragmentTransaction.commit();
                startActivity(intent);


            }
        });

        return view;
    }
}