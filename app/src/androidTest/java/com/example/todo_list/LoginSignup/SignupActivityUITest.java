package com.example.todo_list.LoginSignup;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import android.Manifest;
import android.content.Intent;

import androidx.test.rule.GrantPermissionRule;

import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

@RunWith(AndroidJUnit4.class)
public class SignupActivityUITest {

    @Rule
    public ActivityTestRule<SignupActivity> activityRule = new ActivityTestRule<>(SignupActivity.class);

    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_NETWORK_STATE);

    private FirebaseAuth mAuth;

    @Before
    public void setUp() {
        Intents.init();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
        }

    }

    @After
    public void tearDown() {
        Intents.release();
    }


    @Test
    public void testEmptyName() {
        onView(withId(R.id.editTextTextEmail)).perform(typeText("test@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword2)).perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.imageView5)).perform(click());
        onView(withId(R.id.editTextTextName)).check(matches(hasErrorText("Name is required")));

    }

    @Test
    public void testEmptyEmail() {
        onView(withId(R.id.editTextTextName)).perform(typeText("Test User"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword2)).perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.imageView5)).perform(click());
        onView(withId(R.id.editTextTextEmail)).check(matches(hasErrorText("Email is required")));
    }

    @Test
    public void testEmptyPassword() {
        onView(withId(R.id.editTextTextName)).perform(typeText("Test User"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("test@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword2)).perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.imageView5)).perform(click());
        onView(withId(R.id.editTextTextPassword)).check(matches(hasErrorText("Password is required")));
    }

    @Test
    public void testEmptyPasswordConfirmation() {
        onView(withId(R.id.editTextTextName)).perform(typeText("Test User"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("test@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.imageView5)).perform(click());
        onView(withId(R.id.editTextTextPassword2)).check(matches(hasErrorText("Please confirm your password")));
    }

    @Test
    public void testPasswordLessThan6Characters() {
        onView(withId(R.id.editTextTextName)).perform(typeText("Test User"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("test@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword2)).perform(typeText("123"), closeSoftKeyboard());

        onView(withId(R.id.imageView5)).perform(click());
        onView(withId(R.id.editTextTextPassword)).check(matches(hasErrorText("Password must be at least 6 characters")));
    }

    @Test
    public void testPasswordsDoNotMatch() {
        onView(withId(R.id.editTextTextName)).perform(typeText("Test User"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("test@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword2)).perform(typeText("654321"), closeSoftKeyboard());

        onView(withId(R.id.imageView5)).perform(click());
        onView(withId(R.id.editTextTextPassword2)).check(matches(hasErrorText("Passwords do not match")));
    }

    @Test
    public void alreadyLogin() {

        onView(withId(R.id.textView5)).perform(click());
        // Check if the success toast is displayed and the LoginActivity is started
        intended(hasComponent(LoginActivity.class.getName()));
    }
}
