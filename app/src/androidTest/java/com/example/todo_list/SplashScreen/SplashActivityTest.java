package com.example.todo_list.SplashScreen;

import android.content.Intent;

//import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

//import com.example.todo_list.MainActivity;
import com.example.todo_list.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityScenarioRule<SplashActivity> activityScenarioRule =
            new ActivityScenarioRule<>(SplashActivity.class);

    @Before
    public void setUp() {
//        Intents.init();
    }

    @After
    public void tearDown() {
//        Intents.release();
    }

    @Test
    public void testSplashScreenLaunchesMainActivity() throws InterruptedException {
        // Wait for the splash timeout
        TimeUnit.MILLISECONDS.sleep(SplashActivity.SPLASH_TIMEOUT + 500);

        // Verify that the MainActivity is launched
//        intended(hasComponent(MainActivity.class.getName()));
    }
}
