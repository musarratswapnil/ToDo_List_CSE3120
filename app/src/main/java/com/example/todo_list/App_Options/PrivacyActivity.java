package com.example.todo_list.App_Options;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        // Setup all sections
        setupSection(R.id.section1, R.id.expandButton1, R.id.answer1);
        setupSection(R.id.section2, R.id.expandButton2, R.id.answer2);
        setupSection(R.id.section3, R.id.expandButton3, R.id.answer3);
        setupSection(R.id.section4, R.id.expandButton4, R.id.answer4);
        setupSection(R.id.section5, R.id.expandButton5, R.id.answer5);
    }

    private void setupSection(int sectionId, int buttonId, int answerId) {
        LinearLayout section = findViewById(sectionId);
        final ImageView expandButton = findViewById(buttonId);
        final TextView answer = findViewById(answerId);

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
