package com.example.todo_list.CgpaCalculator;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.example.todo_list.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CgpaActivity2UITest {

    @Rule
    public ActivityTestRule<CgpaActivity2> activityRule =
            new ActivityTestRule<>(CgpaActivity2.class, true, true);

    @Test
    public void testCalculateRequiredGpaWithEmptyExpectedCgpa() {

        onView(withId(R.id.calculate_required_gpa_button)).perform(click());

        onView(withId(R.id.required_gpa_result))
                .check(matches(withText("Please enter an expected CGPA.")));
    }
    @Test
    public void testCalculateRequiredGpa() {
        onView(withId(R.id.expected_cgpa))
                .perform(typeText("4"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.calculate_required_gpa_button)).perform(click());

        // Check if the required GPA is calculated and displayed correctly
        onView(withId(R.id.required_gpa_result))
                .check(matches(withText("Required GPA: 4.00")));
    }
    @Test
    public void testCalculateRequiredGpa2() {
        onView(withId(R.id.expected_cgpa))
                .perform(typeText("3.5"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.calculate_required_gpa_button)).perform(click());

        // Check if the required GPA is calculated and displayed correctly
        onView(withId(R.id.required_gpa_result))
                .check(matches(withText("Required GPA: 3.50")));
    }


}
