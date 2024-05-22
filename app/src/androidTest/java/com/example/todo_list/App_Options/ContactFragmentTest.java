package com.example.todo_list.App_Options;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo_list.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ContactFragmentTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(TestActivity.class);
    }

    @Test
    public void testSendMessage_emptyFields() {
        onView(withId(R.id.message)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.sendMessage)).perform(click());

        // Check if the error message is set on the email field
        onView(withId(R.id.editTextTextEmailAddress)).check(matches(hasErrorText("This field is required")));
    }

    @Test
    public void testSendMessage_invalidEmail() {
        onView(withId(R.id.message)).perform(typeText("Test message"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("invalidemail"), closeSoftKeyboard());
        onView(withId(R.id.sendMessage)).perform(click());

        // Check if the error message is set on the email field
        onView(withId(R.id.editTextTextEmailAddress)).check(matches(hasErrorText("Invalid email address")));
    }

    @Test
    public void testSendMessage_success() {
        onView(withId(R.id.message)).perform(typeText("Test message"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("test@example.com"), closeSoftKeyboard());
        onView(withId(R.id.sendMessage)).perform(click());

        // Check if the fields are cleared on success
        onView(withId(R.id.message)).check(matches(withText("")));
        onView(withId(R.id.editTextTextEmailAddress)).check(matches(withText("")));
    }

    @Test
    public void testEmailFieldFocusChange_invalidEmail() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("invalidemail"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmailAddress)).perform(click()).perform(closeSoftKeyboard());

        // Check if the error message is set on the email field
        onView(withId(R.id.editTextTextEmailAddress)).check(matches(hasErrorText("Invalid email address")));
    }

}
