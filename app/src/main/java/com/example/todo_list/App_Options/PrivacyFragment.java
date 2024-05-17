package com.example.todo_list.App_Options;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.todo_list.R;
//import com.example.yourapp.R;

public class PrivacyFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy, container, false);

        setupSection(view, R.id.section1, R.id.expandButton1, R.id.answer1);

        // Repeat the setupSection call for additional sections here
        // setupSection(view, R.id.section2, R.id.expandButton2, R.id.answer2);

        return view;
    }

    private void setupSection(View view, int sectionId, int buttonId, int answerId) {
        LinearLayout section = view.findViewById(sectionId);
        final ImageView expandButton = view.findViewById(buttonId);
        final TextView answer = view.findViewById(answerId);

        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getVisibility() == View.GONE) {
                    answer.setVisibility(View.VISIBLE);
                    expandButton.setImageResource(R.drawable.privacy_minus); // Assuming you have a 'minus' drawable
                } else {
                    answer.setVisibility(View.GONE);
                    expandButton.setImageResource(R.drawable.privacy_add);
                }
            }
        });
    }
}
