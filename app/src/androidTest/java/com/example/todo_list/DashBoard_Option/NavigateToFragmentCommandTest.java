package com.example.todo_list.DashBoard_Option;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo_list.R;
import com.example.todo_list.Reminder.HomeFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;
import androidx.test.core.app.ActivityScenario;

/**
 * Tests for verifying the navigation functionality within the DashboardActivity.
 * This class focuses on fragment transactions, specifically testing the
 * replacement of a fragment container with HomeFragment.
 *
 */
@RunWith(AndroidJUnit4.class)
public class NavigateToFragmentCommandTest {
    private ActivityScenario<DashboardActivity> scenario;

    /**
     * Sets up the testing environment before each test. This method launches the activity which contains fragments,here
     * DashboardActivity which will be used to host the fragments during the tests.
     */
    @Before
    public void setup() {
        scenario = ActivityScenario.launch(new Intent(ApplicationProvider.getApplicationContext(), DashboardActivity.class));
    }

    /**
     * Tests the navigation to HomeFragment by replacing the content of the fragment_container.
     * It asserts that HomeFragment is successfully added to the fragment_container.
     *After executing the command,the test needs to verify that the HomeFragment is indeed loaded into the specified container
     */
    @Test
    public void testFragmentNavigation() {
        scenario.onActivity(activity -> {
            HomeFragment homeFragment = new HomeFragment();
            new NavigateToFragmentCommand(activity,homeFragment).execute();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commitNow();


            assertTrue(activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HomeFragment);
        });
    }

    /**
     * Cleans up after each test.This method ensures that the ActivityScenario is properly closed,
     * releasing any resources it has acquired during the test to prevent memory leaks.
     * Though it is done implicitly
     */
    @After
    public void close() {
        if (scenario != null) {
            scenario.close();
        }
    }
}
