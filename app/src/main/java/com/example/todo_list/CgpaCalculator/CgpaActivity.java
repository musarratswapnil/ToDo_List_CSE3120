package com.example.todo_list.CgpaCalculator;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CgpaActivity extends AppCompatActivity {
    private Spinner semesterSpinner, gradeSpinner;
    private EditText courseNameEditText, courseCreditsEditText;
    private Button addCourseButton, calculateGpaButton, saveSemesterButton, showSemestersButton;
    private TextView gpaResultTextView;
    private ListView courseListView;

    private ArrayList<String> courses = new ArrayList<>();
    private ArrayList<Double> credits = new ArrayList<>();
    private ArrayList<Double> grades = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private FirebaseDatabase database;
    private DatabaseReference semestersRef;

    private GpaCalculator semesterGpaCalculator;

    private FirebaseAuth firebaseAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa);

        semesterSpinner = findViewById(R.id.semester_spinner);
        courseNameEditText = findViewById(R.id.course_name);
        courseCreditsEditText = findViewById(R.id.course_credits);
        gradeSpinner = findViewById(R.id.grade_spinner);
        addCourseButton = findViewById(R.id.add_course_button);
        calculateGpaButton = findViewById(R.id.calculate_gpa_button);
        saveSemesterButton = findViewById(R.id.save_semester_button);
        showSemestersButton = findViewById(R.id.show_semesters_button);
        gpaResultTextView = findViewById(R.id.gpa_result);
        courseListView = findViewById(R.id.course_list_view);
        database = FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
            semestersRef = database.getReference("users").child(userId).child("semesters");
        } else {
            // Handle case where user is not authenticated
            // Redirect to login screen or show an error message
        }


        semesterGpaCalculator = GpaCalculatorFactory.createCalculator("semester");

        // Set up the grade spinner
        ArrayAdapter<CharSequence> gradeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.grades_array, android.R.layout.simple_spinner_item);
        gradeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeSpinnerAdapter);

        // Set up the semester spinner
        ArrayAdapter<CharSequence> semesterSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.semester_array, android.R.layout.simple_spinner_item);
        semesterSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterSpinnerAdapter);

        // Set up the list adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        courseListView.setAdapter(adapter);

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
            }
        });

        calculateGpaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGPA();
            }
        });

        saveSemesterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSemester();
            }
        });

        showSemestersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CgpaActivity.this, CgpaActivity2.class));
            }
        });
    }

    private void addCourse() {
        String courseName = courseNameEditText.getText().toString();
        String courseCreditsStr = courseCreditsEditText.getText().toString();

        if (courseName.isEmpty() || courseCreditsStr.isEmpty()) {
            // Handle empty fields, maybe show a Toast or a Snackbar
            return;
        }

        double courseCredits;
        try {
            courseCredits = Double.parseDouble(courseCreditsStr);
        } catch (NumberFormatException e) {
            // Handle the exception, maybe show a Toast or a Snackbar
            return;
        }

        String grade = gradeSpinner.getSelectedItem().toString();
        double gradeValue = getGradeValue(grade);

        courses.add(courseName);
        credits.add(courseCredits);
        grades.add(gradeValue);

        // Update the list view
        adapter.add(courseName + " - " + courseCredits + " credits - Grade: " + grade);
        adapter.notifyDataSetChanged();

        // Clear the input fields
        courseNameEditText.setText("");
        courseCreditsEditText.setText("");
        gradeSpinner.setSelection(0);
    }

    private double getGradeValue(String grade) {
        switch (grade) {
            case "A+":
                return 4.0;
            case "A":
                return 3.75;
            case "A-":
                return 3.5;
            case "B+":
                return 3.25;
            case "B":
                return 3.0;
            case "B-":
                return 2.75;
            case "C":
                return 2.5;
            case "D":
                return 2.25;
            default:
                return 0.0;
        }
    }

    private void calculateGPA() {
        double gpa = semesterGpaCalculator.calculate(grades, credits);
        gpaResultTextView.setText("GPA: " + String.format("%.2f", gpa));
    }

    private void saveSemester() {
        String semesterName = semesterSpinner.getSelectedItem().toString();
        if (semesterName.isEmpty()) {
            // Handle empty semester name
            return;
        }

        // Create a new semester object
        Semester semester = new Semester(semesterName, courses, credits, grades, gpaResultTextView.getText().toString());

        // Save to Firebase
        semestersRef.child(semesterName).setValue(semester);

        // Clear the data for the next semester
        courses.clear();
        credits.clear();
        grades.clear();
        adapter.clear();
        adapter.notifyDataSetChanged();
        gpaResultTextView.setText("GPA: ");
    }

    public static class Semester {
        public String name;
        public ArrayList<String> courses;
        public ArrayList<Double> credits;
        public ArrayList<Double> grades;
        public String gpa;

        public Semester() {
            // Default constructor required for calls to DataSnapshot.getValue(Semester.class)
        }

        public Semester(String name, ArrayList<String> courses, ArrayList<Double> credits, ArrayList<Double> grades, String gpa) {
            this.name = name;
            this.courses = courses;
            this.credits = credits;
            this.grades = grades;
            this.gpa = gpa;
        }
    }
}
