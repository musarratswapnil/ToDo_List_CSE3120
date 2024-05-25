package com.example.todo_list.App_Options;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.todo_list.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

    @Rule
    public IntentsTestRule<SettingsActivity> intentsTestRule =
            new IntentsTestRule<>(SettingsActivity.class);

    @Test
    public void testAccountButtonIsDisplayed() {
        // Check if the account button is displayed
        onView(withId(R.id.accountButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testPrivacyButtonIsDisplayed() {
        // Check if the privacy button is displayed
        onView(withId(R.id.privacyButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testHelpButtonIsDisplayed() {
        // Check if the help button is displayed
        onView(withId(R.id.helpButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testAboutButtonIsDisplayed() {
        // Check if the about button is displayed
        onView(withId(R.id.aboutButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testAccountButtonLaunchesAccountActivity() {
        // Perform click on the account button and check if AccountActivity is launched
        onView(withId(R.id.accountButton)).perform(click());
        intended(hasComponent(AccountActivity.class.getName()));
    }

    @Test
    public void testPrivacyButtonLaunchesPrivacyActivity() {
        // Perform click on the privacy button and check if PrivacyActivity is launched
        onView(withId(R.id.privacyButton)).perform(click());
        intended(hasComponent(PrivacyActivity.class.getName()));
    }

    @Test
    public void testHelpButtonLaunchesHelpActivity() {
        // Perform click on the help button and check if HelpActivity is launched
        onView(withId(R.id.helpButton)).perform(click());
        intended(hasComponent(HelpActivity.class.getName()));
    }

    @Test
    public void testAboutButtonLaunchesAboutActivity() {
        // Perform click on the about button and check if AboutActivity is launched
        onView(withId(R.id.aboutButton)).perform(click());
        intended(hasComponent(AboutActivity.class.getName()));
    }
}
