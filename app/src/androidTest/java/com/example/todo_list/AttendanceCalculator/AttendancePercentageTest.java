package com.example.todo_list.AttendanceCalculator;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import com.example.todo_list.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AttendancePercentageTest {

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testCalculateAttendancePercentage() {
        try (ActivityScenario<AttendancePercentage> scenario = ActivityScenario.launch(AttendancePercentage.class)) {
            // Input values
            onView(withId(R.id.editTextTotalCredit)).perform(clearText(), typeText("3"));
            onView(withId(R.id.editTextTotalWeeks)).perform(clearText(), typeText("15"));
            onView(withId(R.id.editTextClassesAttended)).perform(clearText(), typeText("30"));

            // Close the keyboard
            closeSoftKeyboard();

            // Click the calculate button
            onView(withId(R.id.buttonCalculateAttendance)).perform(click());

            // Check the result
            onView(withId(R.id.textViewAttendanceResult)).check(matches(withText("Your attendance is 66.67%")));
        }
    }
}
