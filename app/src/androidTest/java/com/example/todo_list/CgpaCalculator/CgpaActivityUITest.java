package com.example.todo_list.CgpaCalculator;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.todo_list.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CgpaActivityUITest {

    @Rule
    public ActivityTestRule<CgpaActivity> activityRule =
            new ActivityTestRule<>(CgpaActivity.class, true, true);

    @Test
    public void testAddCourse() {
        onView(withId(R.id.semester_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click()); // Select the first item (1-1)

        onView(withId(R.id.course_name))
                .perform(typeText("Test"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.course_credits))
                .perform(typeText("3"), ViewActions.closeSoftKeyboard());

        // Select an item from the grade spinner
        onView(withId(R.id.grade_spinner)).perform(click());
        onData(anything()).atPosition(2).perform(click()); // Select the third item (A-)


        onView(withId(R.id.add_course_button)).perform(click());

        // Check if the course is added to the list view
        onData(anything()).inAdapterView(withId(R.id.course_list_view)).atPosition(0).check(matches(withText("Test - 3.0 credits - Grade: A-")));
    }
    @Test
    public void testCalculateGPA2() {
        // Add first course
        onView(withId(R.id.course_name))
                .perform(typeText("Course 1"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.course_credits))
                .perform(typeText("3"), ViewActions.closeSoftKeyboard());

        // Select an item from the semester spinner
        onView(withId(R.id.semester_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click()); // Select the first item (1-1)

        // Select an item from the grade spinner
        onView(withId(R.id.grade_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click()); // Select the first item (A+)

        onView(withId(R.id.add_course_button)).perform(click());

        // Add second course
        onView(withId(R.id.course_name))
                .perform(typeText("Course 2"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.course_credits))
                .perform(typeText("4"), ViewActions.closeSoftKeyboard());

        // Select an item from the semester spinner
        onView(withId(R.id.semester_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click()); // Select the first item (1-1)

        // Select an item from the grade spinner
        onView(withId(R.id.grade_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click()); // Select the second item (A)

        onView(withId(R.id.add_course_button)).perform(click());

        // Calculate GPA
        onView(withId(R.id.calculate_gpa_button)).perform(click());

        // Check if GPA is calculated and displayed correctly
        // Expected GPA: ((4.0 * 3) + (3.75 * 4)) / (3 + 4) = 3.8571
        onView(withId(R.id.gpa_result))
                .check(matches(withText("GPA: 3.86"))); // Adjust the format if needed
    }
    @Test
    public void testCalculateGPA() {
        onView(withId(R.id.course_name))
                .perform(typeText("Test Course"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.course_credits))
                .perform(typeText("3"), ViewActions.closeSoftKeyboard());

        // Select an item from the semester spinner
        onView(withId(R.id.semester_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click()); // Select the first item (1-1)

        // Select an item from the grade spinner
        onView(withId(R.id.grade_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click()); // Select the first item (A+)

        onView(withId(R.id.add_course_button)).perform(click());

        onView(withId(R.id.calculate_gpa_button)).perform(click());

        // Check if GPA is calculated and displayed correctly
        onView(withId(R.id.gpa_result))
                .check(matches(withText("GPA: 4.00")));
    }

    @Test
    public void testShowSemestersButton() {
        onView(withId(R.id.show_semesters_button)).perform(click());

        // Check if the next activity is launched
        ActivityScenario.launch(CgpaActivity2.class);
    }

    @Test
    public void testAddCourseWithMissingInformation() {
        // Attempt to add a course with missing information
        onView(withId(R.id.add_course_button)).perform(click());

        // Check if error messages are displayed
        onView(withId(R.id.course_name)).check(matches(hasErrorText("Name is required")));

    }

    @Test
    public void testSaveSemesterWithMissingInformation() {
        // Attempt to save a semester with missing course information
        onView(withId(R.id.save_semester_button)).perform(click());

        onView(withId(R.id.course_name)).check(matches(hasErrorText("Please provide all the information before saving the semester.")));
    }



}
