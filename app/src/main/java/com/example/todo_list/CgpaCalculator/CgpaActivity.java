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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.LoginSignup.LoginActivity;
import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * This activity handles the user interface for calculating CGPA.
 */
public class CgpaActivity extends AppCompatActivity {
    protected Spinner semesterSpinner;
    protected Spinner gradeSpinner;
    protected EditText courseNameEditText, courseCreditsEditText;
    protected Button addCourseButton, calculateGpaButton, saveSemesterButton, showSemestersButton;
    protected TextView gpaResultTextView;
    protected ListView courseListView;

    protected ArrayList<String> courses = new ArrayList<>();
    protected ArrayList<Double> credits = new ArrayList<>();
    protected ArrayList<Double> grades = new ArrayList<>();
    protected ArrayAdapter<String> adapter;
    protected FirebaseDatabase database;
    protected DatabaseReference semestersRef;

    protected GpaCalculator semesterGpaCalculator;

    protected FirebaseAuth firebaseAuth;
    protected String userId;
    private Cgpa cgpa=new Cgpa();
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the most recent data supplied in onSaveInstanceState(Bundle).
     */

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
            Intent intent=new Intent(CgpaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
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
                if (courses.isEmpty() || credits.isEmpty() || grades.isEmpty()) {
                    courseNameEditText.setError("Please provide all the information before saving the semester.");
                    Toast.makeText(CgpaActivity.this, "Please provide all the information before saving the semester.", Toast.LENGTH_SHORT).show();
                } else {
                    saveSemester();
                }
            }
        });

        showSemestersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CgpaActivity.this, CgpaActivity2.class));
            }
        });
    }
    /**
     * Adds a course to the list of courses for the current semester.
     */
    protected void addCourse() {
        String courseName = courseNameEditText.getText().toString();
        String courseCreditsStr = courseCreditsEditText.getText().toString();

        if (courseName.isEmpty() || courseCreditsStr.isEmpty()) {
            Toast.makeText(CgpaActivity.this, "Give Necessary Information", Toast.LENGTH_SHORT).show();
            courseNameEditText.setError("Name is required");
            courseCreditsEditText.setError("Credits are reqired");
            return;
        }

        double courseCredits;
        try {
            courseCredits = Double.parseDouble(courseCreditsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid credits.", Toast.LENGTH_SHORT).show();
            courseCreditsEditText.setError("Please enter valid credits.");

            return;
        }

        String grade = gradeSpinner.getSelectedItem().toString();
        double gradeValue = cgpa.getGradeValue(grade);

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


    /**
     * Calculates the GPA for the current list of courses.
     */
    protected void calculateGPA() {
        double gpa = semesterGpaCalculator.calculate(grades, credits);
        gpaResultTextView.setText("GPA: " + String.format("%.2f", gpa));
    }
    /**
     * Saves the current semester's courses, credits, and grades to Firebase.
     */
    protected void saveSemester() {
        final String semesterName = semesterSpinner.getSelectedItem().toString();
        if (semesterName.isEmpty()) {
            Toast.makeText(this, "Please select a semester.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Load existing semester data
        semestersRef.child(semesterName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Semester existingSemester = dataSnapshot.getValue(Semester.class);

                if (existingSemester == null) {
                    // No existing semester, create a new one
                    existingSemester = new Semester(semesterName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "");
                }

                // Add new courses to the existing semester data
                existingSemester.courses.addAll(courses);
                existingSemester.credits.addAll(credits);
                existingSemester.grades.addAll(grades);

                // Recalculate GPA with the combined courses and credits
                double combinedGpa = semesterGpaCalculator.calculate(existingSemester.grades, existingSemester.credits);
                existingSemester.gpa = String.format("%.2f", combinedGpa);

                // Save updated semester data to Firebase
                semestersRef.child(semesterName).setValue(existingSemester);

                // Clear the data for the next semester
                courses.clear();
                credits.clear();
                grades.clear();
                adapter.clear();
                adapter.notifyDataSetChanged();
                gpaResultTextView.setText("GPA: ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Toast.makeText(CgpaActivity.this, "Failed to save semester. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * Represents a semester with its courses, credits, grades, and GPA.
     */
    public static class Semester {
        public String name;
        public ArrayList<String> courses;
        public ArrayList<Double> credits;
        public ArrayList<Double> grades;
        public String gpa;
        /**
         * Default constructor required for calls to DataSnapshot.getValue(Semester.class).
         */
        public Semester() {
            // Default constructor required for calls to DataSnapshot.getValue(Semester.class)
        }
        /**
         * Constructor to create a new Semester instance.
         * @param name The name of the semester.
         * @param courses A list of course names.
         * @param credits A list of course credits.
         * @param grades A list of course grades.
         * @param gpa The GPA of the semester.
         */
        public Semester(String name, ArrayList<String> courses, ArrayList<Double> credits, ArrayList<Double> grades, String gpa) {
            this.name = name;
            this.courses = courses;
            this.credits = credits;
            this.grades = grades;
            this.gpa = gpa;
        }
    }
}
