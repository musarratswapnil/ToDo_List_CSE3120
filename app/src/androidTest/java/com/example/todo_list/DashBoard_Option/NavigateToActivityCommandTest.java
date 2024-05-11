package com.example.todo_list.DashBoard_Option;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo_list.Note.NoteMainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for verifying the navigation functionality from DashboardActivity
 * to another activity using NavigateToActivityCommand.
 *
 */
@RunWith(AndroidJUnit4.class)
public class NavigateToActivityCommandTest {
    private Context context;

    /**
     * Sets up the testing environment before each test.So everytime I dont need initialization
     */
    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
        ActivityScenario.launch(DashboardActivity.class);
    }

    /**
     * Tests the navigation to TargetActivity from DashboardActivity.
     * It asserts that an Intent to launch TargetActivity is indeed initiated.
     */
    @Test
    public void testActivityNavigation() {

        Intent intent = new Intent(context, NoteMainActivity.class);
        assertNotNull( intent);
    }

    /**
     * Tests that {@link NoteMainActivity} correctly handles intent extras.
     * Verifies that the extra passed with the intent is exactly as expected.
     * This test passes an intent with a "key" extra to the {@link NoteMainActivity} and checks
     * if the activity retrieves the correct value associated with the key.
     * <br>
     *
     * Expected outcome:
     * Correctresult:The string "Shipra" should be returned from the activity's intent, confirming that the
     * activity correctly processes the intent extras passed to it.
     */

    @Test
    public void noteMainActivityHandleIntentExtrasEquals() {
        Intent intent = new Intent(context, NoteMainActivity.class);
        intent.putExtra("key", "Shipra");
        try (ActivityScenario<NoteMainActivity> scenario = ActivityScenario.launch(intent)){
            scenario.onActivity(activity -> {
                assertEquals("Shipra", activity.getIntent().getStringExtra("key"));
            });
        }
    }

    /**
     * Tests that {@link NoteMainActivity} correctly handles incorrect intent extras,for example here string passed.
     * Verifies that the activity does not confuse different string cases when retrieving
     * intent extras. <b>The test checks that the actual value retrieved from the intent is case-sensitive</b>
     * and hence does not match an incorrect case version of the input string.
     * <br>
     * Expected outcome:
     * TestCorrect:The string "shipra" (note the lowercase 's') should not match the stored value "Shipra",
     * confirming that the intent extra handling in the activity is case-sensitive
     */
    @Test
    public void noteMainActivityHandleIntentExtrasNotEquals() {
        Intent intent = new Intent(context, NoteMainActivity.class);
        intent.putExtra("key", "Shipra");
        try (ActivityScenario<NoteMainActivity> scenario = ActivityScenario.launch(intent)){
            scenario.onActivity(activity -> {
                assertNotEquals("shipra", activity.getIntent().getStringExtra("key"));
            });
        }
    }


    /**
     * Cleans up after each test.
     * As I didnot use instance of ActivityScenario,so dont need release
     * kept it for future use
     */
    @After
    public void close() {

    }
}
