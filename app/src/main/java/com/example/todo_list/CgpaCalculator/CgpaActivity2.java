package com.example.todo_list.CgpaCalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Activity to display and manage semesters for CGPA calculation.
 * Allows users to view, edit, delete semesters and calculate required GPA for desired CGPA.
 */
public class CgpaActivity2 extends AppCompatActivity {
    private RecyclerView semestersRecyclerView;
    private TextView cgpaResultTextView, requiredGpaResultTextView;
    private EditText expectedCgpaEditText;
    private Button calculateRequiredGpaButton;
    private SemesterAdapter adapter;
    protected ArrayList<CgpaActivity.Semester> semestersList = new ArrayList<>();

    private GpaCalculator cgpaCalculator;

    private FirebaseDatabase database;
    private DatabaseReference semestersRef;
    private FirebaseAuth firebaseAuth;
    private String userId;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the most recent data supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa2);

        semestersRecyclerView = findViewById(R.id.semesters_recycler_view);
        cgpaResultTextView = findViewById(R.id.cgpa_result);
        expectedCgpaEditText = findViewById(R.id.expected_cgpa);
        calculateRequiredGpaButton = findViewById(R.id.calculate_required_gpa_button);
        requiredGpaResultTextView = findViewById(R.id.required_gpa_result);

        cgpaCalculator = GpaCalculatorFactory.createCalculator("cgpa");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
            database = FirebaseDatabase.getInstance();
            semestersRef = database.getReference("users").child(userId).child("semesters");

            adapter = new SemesterAdapter(semestersList, semestersRef);
            semestersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            semestersRecyclerView.setAdapter(adapter);

            loadSemesters();

            calculateRequiredGpaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculateRequiredGpa();
                }
            });
        } else {
            Toast.makeText(this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Loads the semesters from the Firebase database.
     * Listens for changes and updates the RecyclerView and CGPA display accordingly.
     */
    private void loadSemesters() {
        semestersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semestersList.clear();
                double totalGpa = 0;
                int totalSemesters = 0;

                for (DataSnapshot semesterSnapshot : dataSnapshot.getChildren()) {
                    CgpaActivity.Semester semester = semesterSnapshot.getValue(CgpaActivity.Semester.class);
                    if (semester != null && semester.gpa != null && !semester.gpa.isEmpty()) {
                        try {
                            totalGpa += Double.parseDouble(semester.gpa);
                            totalSemesters++;
                            semestersList.add(semester);
                        } catch (NumberFormatException e) {
                            Toast.makeText(CgpaActivity2.this, "Error parsing GPA for " + semester.name, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                sortSemesters();
                adapter.notifyDataSetChanged();
                if (totalSemesters > 0) {
                    double cgpa = totalGpa / totalSemesters;
                    cgpaResultTextView.setText("CGPA: " + String.format("%.2f", cgpa));
                } else {
                    cgpaResultTextView.setText("CGPA: N/A");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CgpaActivity2.this, "Failed to load semesters.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sorts the list of semesters in the predefined order.
     */
    protected void sortSemesters() {
        HashMap<String, Integer> semesterOrder = new HashMap<>();
        semesterOrder.put("1-1", 1);
        semesterOrder.put("1-2", 2);
        semesterOrder.put("2-1", 3);
        semesterOrder.put("2-2", 4);
        semesterOrder.put("3-1", 5);
        semesterOrder.put("3-2", 6);
        semesterOrder.put("4-1", 7);
        semesterOrder.put("4-2", 8);

        Collections.sort(semestersList, new Comparator<CgpaActivity.Semester>() {
            @Override
            public int compare(CgpaActivity.Semester s1, CgpaActivity.Semester s2) {
                return semesterOrder.get(s1.name) - semesterOrder.get(s2.name);
            }
        });
    }

    /**
     * Calculates the required GPA to achieve the expected CGPA for the remaining semesters.
     */
    private void calculateRequiredGpa() {
        String expectedCgpaStr = expectedCgpaEditText.getText().toString();
        if (expectedCgpaStr.isEmpty()) {
            requiredGpaResultTextView.setText("Please enter an expected CGPA.");
            return;
        }

        double expectedCgpa = Double.parseDouble(expectedCgpaStr);
        int totalSemestersCompleted = semestersList.size();
        int totalSemesters = 8;
        int remainingSemesters = totalSemesters - totalSemestersCompleted;

        if (remainingSemesters <= 0) {
            requiredGpaResultTextView.setText("All semesters are already completed.");
            return;
        }

        double currentTotalGpa = 0;
        for (CgpaActivity.Semester semester : semestersList) {
            try {
                currentTotalGpa += Double.parseDouble(semester.gpa);
            } catch (NumberFormatException e) {
                Toast.makeText(CgpaActivity2.this, "Error parsing GPA for " + semester.name, Toast.LENGTH_SHORT).show();
            }
        }

        double requiredTotalGpa = expectedCgpa * totalSemesters;
        double remainingGpa = requiredTotalGpa - currentTotalGpa;
        double requiredAverageGpa = remainingGpa / remainingSemesters;

        requiredGpaResultTextView.setText("Required GPA: " + String.format("%.2f", requiredAverageGpa));
    }
}
