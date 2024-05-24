package com.example.todo_list.AttendanceCalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

public class AttendanceCalculator extends AppCompatActivity {

    private EditText editTextTotalCredit, editTextTotalWeeks, editTextClassesAttended, editTextDesiredPercentage, editTextRemainingWeeks;
    private Button buttonCalculate, buttonReset, buttonNavigate;
    private TextView textViewClassesLeft, textViewClassesNeedToAttend, textViewResult;
    private AttendanceCalculatorLogic calculatorLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_calculator);

        calculatorLogic = new AttendanceCalculatorLogic();

        editTextTotalCredit = findViewById(R.id.editTextTotalCredit);
        editTextTotalWeeks = findViewById(R.id.editTextTotalWeeks);
        editTextClassesAttended = findViewById(R.id.editTextClassesAttended);
        editTextDesiredPercentage = findViewById(R.id.editTextDesiredPercentage);
        editTextRemainingWeeks = findViewById(R.id.editTextRemainingWeeks);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonReset);
        buttonNavigate = findViewById(R.id.buttonNavigate);
        textViewClassesLeft = findViewById(R.id.textViewClassesLeft);
        textViewClassesNeedToAttend = findViewById(R.id.textViewClassesNeedToAttend);
        textViewResult = findViewById(R.id.textViewResult);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateNeededClasses();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceCalculator.this, AttendancePercentage.class);
                startActivity(intent);
            }
        });
    }

    private void calculateNeededClasses() {
        String totalCreditStr = editTextTotalCredit.getText().toString();
        String totalWeeksStr = editTextTotalWeeks.getText().toString();
        String classesAttendedStr = editTextClassesAttended.getText().toString();
        String desiredPercentageStr = editTextDesiredPercentage.getText().toString();
        String remainingWeeksStr = editTextRemainingWeeks.getText().toString();

        if (!totalCreditStr.isEmpty() && !totalWeeksStr.isEmpty() && !classesAttendedStr.isEmpty() && !desiredPercentageStr.isEmpty() && !remainingWeeksStr.isEmpty()) {
            int totalCredit = Integer.parseInt(totalCreditStr);
            int totalWeeks = Integer.parseInt(totalWeeksStr);
            int classesAttended = Integer.parseInt(classesAttendedStr);
            double desiredPercentage = Double.parseDouble(desiredPercentageStr);
            int remainingWeeks = Integer.parseInt(remainingWeeksStr);

            AttendanceCalculatorLogic.CalculationResult result = calculatorLogic.calculateNeededClasses(
                    totalCredit, totalWeeks, classesAttended, desiredPercentage, remainingWeeks
            );

            textViewClassesLeft.setText(String.valueOf(result.getTotalRemainingClasses()));
            textViewClassesNeedToAttend.setText(result.getRemainingClassesToAttend());
            textViewResult.setText(result.getMessage());
        } else {
            textViewResult.setText("Please enter all fields");
        }
    }

    private void resetFields() {
        editTextTotalCredit.setText("");
        editTextTotalWeeks.setText("");
        editTextClassesAttended.setText("");
        editTextDesiredPercentage.setText("");
        editTextRemainingWeeks.setText("");
        textViewClassesLeft.setText("");
        textViewClassesNeedToAttend.setText("");
        textViewResult.setText("");
    }
}
