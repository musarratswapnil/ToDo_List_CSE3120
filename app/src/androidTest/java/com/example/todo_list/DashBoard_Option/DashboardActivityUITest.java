package com.example.todo_list.DashBoard_Option;

import androidx.test.core.app.ActivityScenario;

import org.junit.Before;

public class DashboardActivityUITest {
    private ActivityScenario<DashboardActivity> scenario;

    @Before
    public void setUp() {
        // Launch DashboardActivity before each test
        scenario = ActivityScenario.launch(DashboardActivity.class);
    }

//    @Test
//    public void testNavigationDrawerOpenAndClose() {
//        // Click on the navigation drawer icon
//        onView(withContentDescription(R.string.open_nav)).perform(click());
//
//        // Verify that the navigation drawer is displayed
//        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
//
//        // Click on the navigation drawer close icon
//        onView(withContentDescription(R.string.close_nav)).perform(click());
//
//        // Verify that the navigation drawer is closed
//        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
//    }

//    @Test
//    public void testNavigationToSettingsActivity() {
//        // Click on the navigation drawer icon
//        onView(withContentDescription(R.string.open_nav)).perform(click());
//
//        // Click on the "Settings" option
//        onView(withText("Settings")).perform(click());
//
//        // Verify that the SettingsActivity is displayed
//        onView(withId(R.id.nav_settings)).check(matches(isDisplayed()));
//    }
}
