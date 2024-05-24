//package com.example.todo_list.SplashScreen;
//
//import android.content.Intent;
//
//import androidx.test.espresso.intent.Intents;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import com.example.todo_list.DashBoard_Option.DashboardActivity;
//import com.example.todo_list.LoginSignup.LoginActivity;
//import com.example.todo_list.LoginSignup.SignupActivity;
//import com.example.todo_list.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//
//@RunWith(AndroidJUnit4.class)
//public class MainActivityTest {
//
//    @Rule
//    public ActivityScenarioRule<MainActivity> activityScenarioRule =
//            new ActivityScenarioRule<>(MainActivity.class);
//
//    @Before
//    public void setUp() {
//        Intents.init();
//    }
//
//    @After
//    public void tearDown() {
//        Intents.release();
//    }
//
//    @Test
//    public void testLoginButtonClick() {
//        // Check that the login button is displayed
//        onView(withId(R.id.button))
//                .check(matches(isDisplayed()))
//                .check(matches(withText("Login"))); // Assuming the button text is "Login"
//
//        // Perform a click on the login button
//        onView(withId(R.id.button)).perform(click());
//
//        // Verify that the LoginActivity is opened
//        intended(hasComponent(LoginActivity.class.getName()));
//    }
//
//    @Test
//    public void testSignUpButtonClick() {
//        // Check that the sign-up button is displayed
//        onView(withId(R.id.button2))
//                .check(matches(isDisplayed()))
//                .check(matches(withText("SignUp"))); // Assuming the button text is "Sign Up"
//
//        // Perform a click on the sign-up button
//        onView(withId(R.id.button2)).perform(click());
//
//        // Verify that the SignupActivity is opened
//        intended(hasComponent(SignupActivity.class.getName()));
//    }
//
//    @Test
//    public void testUserAuthenticatedRedirectsToDashboard() {
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            // Verify that the DashboardActivity is opened if the user is authenticated
//            intended(hasComponent(DashboardActivity.class.getName()));
//        }
//    }
//}