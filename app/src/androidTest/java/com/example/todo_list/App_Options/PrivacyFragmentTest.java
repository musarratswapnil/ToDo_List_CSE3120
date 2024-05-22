package com.example.todo_list.App_Options;

//import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;

import com.example.todo_list.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class PrivacyFragmentTest {

    @Test
    public void testExpandCollapseFunctionality() {
        // Launching the fragment
//        FragmentScenario.launchInContainer(PrivacyFragment.class);

        // Initially, the answer should be GONE
        onView(withId(R.id.answer1))
                .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.GONE)));

        // Click the expand button to expand the section
        onView(withId(R.id.expandButton1)).perform(click());

        // Now the answer should be VISIBLE
        onView(withId(R.id.answer1))
                .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)));

        // Click the expand button again to collapse the section
        onView(withId(R.id.expandButton1)).perform(click());

        // The answer should be GONE again
        onView(withId(R.id.answer1))
                .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.GONE)));
    }
}
