package com.example.todo_list.AttendanceCalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todo_list.R;

public class AttendancePercentage extends AppCompatActivity {

    private EditText editTextTotalCredit, editTextTotalWeeks, editTextClassesAttended;
    private Button buttonCalculateAttendance;
    private TextView textViewAttendanceResult;
    private AttendancePercentageLogic percentageLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_percentage);

        AttendanceAdapter attendanceAdapter = new AttendanceAdapter();
        percentageLogic = new AttendancePercentageLogic(attendanceAdapter);

        editTextTotalCredit = findViewById(R.id.editTextTotalCredit);
        editTextTotalWeeks = findViewById(R.id.editTextTotalWeeks);
        editTextClassesAttended = findViewById(R.id.editTextClassesAttended);
        buttonCalculateAttendance = findViewById(R.id.buttonCalculateAttendance);
        textViewAttendanceResult = findViewById(R.id.textViewAttendanceResult);

        buttonCalculateAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAttendance();
            }
        });
    }

    private void calculateAttendance() {
        String totalCreditStr = editTextTotalCredit.getText().toString();
        String totalWeeksStr = editTextTotalWeeks.getText().toString();
        String classesAttendedStr = editTextClassesAttended.getText().toString();

        if (!totalCreditStr.isEmpty() && !totalWeeksStr.isEmpty() && !classesAttendedStr.isEmpty()) {
            int totalCredit = Integer.parseInt(totalCreditStr);
            int totalWeeks = Integer.parseInt(totalWeeksStr);
            int classesAttended = Integer.parseInt(classesAttendedStr);

            double attendancePercentage = percentageLogic.calculatePercentage(totalCredit, totalWeeks, classesAttended);

            textViewAttendanceResult.setText(String.format("Your attendance is %.2f%%", attendancePercentage));
        } else {
            textViewAttendanceResult.setText("Please enter all fields");
        }
    }
}
