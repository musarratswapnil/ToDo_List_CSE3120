package com.example.todo_list.App_Options;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo_list.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AboutActivityTest {

    @Rule
    public ActivityScenarioRule<AboutActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AboutActivity.class);

    @Test
    public void testImageViewIsDisplayed() {
        // Check if the ImageView is displayed
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
    }

    @Test
    public void testTextViewTaskProIsDisplayed() {
        // Check if the TextView with "TaskPro" text is displayed
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView)).check(matches(withText("TaskPro")));
    }

    @Test
    public void testTextViewVersionIsDisplayed() {
        // Check if the TextView with "Version 1" text is displayed
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
        onView(withId(R.id.textView2)).check(matches(withText("Version 1")));
    }

    @Test
    public void testTextViewCopyrightIsDisplayed() {
        // Check if the TextView with "© 2023" text is displayed
        onView(withId(R.id.textView12)).check(matches(isDisplayed()));
        onView(withId(R.id.textView12)).check(matches(withText("© 2023")));
    }

    @Test
    public void testTextViewAllRightsReservedIsDisplayed() {
        // Check if the TextView with "All Rights Reserved" text is displayed
        onView(withId(R.id.textView11)).check(matches(isDisplayed()));
        onView(withId(R.id.textView11)).check(matches(withText("All Rights Reserved")));
    }
}
