package com.example.todo_list.LoginSignup;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.rule.GrantPermissionRule;
import android.os.IBinder;
import android.view.WindowManager;
import androidx.test.espresso.Root;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import com.example.todo_list.DashBoard_Option.DashboardActivity;
import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;


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
//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.Intents.intending;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.AllOf.allOf;

//import androidx.test.espresso.intent.Intents;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {


    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Rule
//    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_NETWORK_STATE);

    private FirebaseAuth mAuth;

    @Before
    public void setUp() {
//        Intents.init();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
        }
    }

    @After
    public void tearDown() {
//        Intents.release();
    }
    @Test
    public void testEmptyEmail() {
        onView(withId(R.id.editTextTextPassword)).perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.imageView5)).perform(click());
        onView(withId(R.id.editTextTextEmail)).check(matches(hasErrorText("Email is required")));
    }

    @Test
    public void testEmptyPassword() {

        onView(withId(R.id.editTextTextEmail)).perform(typeText("test@example.com"), closeSoftKeyboard());

        onView(withId(R.id.imageView5)).perform(click());
        onView(withId(R.id.editTextTextPassword)).check(matches(hasErrorText("Password is required")));
    }

    @Test
    public void testNoInformation() {

        onView(withId(R.id.imageView5)).perform(click());
        onView(withId(R.id.editTextTextEmail)).check(matches(hasErrorText("Please fill in information to continue")));
    }

    @Test
    public void testForgotPasswordClick() {
        onView(withId(R.id.editTextTextEmail)).perform(replaceText("razon@gmail.com"));
        onView(withId(R.id.textView23)).perform(click());

        // Verifying password reset interaction (this would ideally check if the password reset email is sent)
        onView(withId(R.id.editTextTextEmail)).check(matches(withText("razon@gmail.com")));
    }


    @Test
    public void testRegisterButtonClick() {
        onView(withId(R.id.textView5)).perform(click());

        // Check if the SignupActivity is started
//        intended(hasComponent(SignupActivity.class.getName()));
    }
}
