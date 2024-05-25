package com.example.todo_list.App_Options;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo_list.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class PrivacyActivityTest {

    @Rule
    public ActivityScenarioRule<PrivacyActivity> activityScenarioRule =
            new ActivityScenarioRule<>(PrivacyActivity.class);

    @Test
    public void testSectionVisibilityToggle() {
        // Use the ActivityScenarioRule to automatically handle the activity lifecycle

        // Initially, the answer should be hidden
        onView(withId(R.id.answer1)).check(matches(withEffectiveVisibility(Visibility.GONE)));

        // Perform click to expand
        onView(withId(R.id.expandButton1)).perform(click());

        // The answer should now be visible
        onView(withId(R.id.answer1)).check(matches(isDisplayed()));

        // Perform click to collapse
        onView(withId(R.id.expandButton1)).perform(click());

        // The answer should now be hidden again
        onView(withId(R.id.answer1)).check(matches(withEffectiveVisibility(Visibility.GONE)));
    }
}
