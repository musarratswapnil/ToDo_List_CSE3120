package com.example.todo_list.DashBoard_Option;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.todo_list.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test class for testing UI interactions in the OptionFragment.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class OptionFragmentUITest {

    @Rule
    public ActivityScenarioRule<DashboardActivity> activityScenarioRule =
            new ActivityScenarioRule<>(DashboardActivity.class);

    /**
     * Test to check if the OptionFragment is displayed.
     */
    @Test
    public void testOptionFragmentDisplayed() {
        // Check if the OptionFragment is displayed
        Espresso.onView(ViewMatchers.withId(R.id.fragment_container))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Test to check navigation to the ReminderFragment by clicking on the reminder card.
     */
    @Test
    public void testNavigationToReminderFragment() {
        // Click on the reminder card in the OptionFragment
        Espresso.onView(ViewMatchers.withId(R.id.Reminder)).perform(ViewActions.click());

        // Check if the ReminderFragment is opened
        Espresso.onView(ViewMatchers.withId(R.id.fragment_container))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
