package com.example.todo_list.Note;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.todo_list.R;

import org.junit.Rule;
import org.junit.Test;

public class HomeScreenUITest {
    @Rule
    public ActivityScenarioRule<HomeScreen> activityRule =
            new ActivityScenarioRule<>(HomeScreen.class);

    @Test
    public void testInitializeView() {
        onView(withId(R.id.toolbar)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.recyclerview)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.fab)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.sortSpinner)).check(matches(ViewMatchers.isDisplayed()));
    }
//    public void testSetupSortingOptions() {
//        // Wait for 5 seconds before performing the click action
//        onView(ViewMatchers.isRoot()).perform(CustomViewActions.waitFor(5000));
//
//        // Perform click on the sortSpinner
//        onView(withId(R.id.sortSpinner)).perform(click());
//
//        // Select the option "Sort by Name"
//        onView(withText("Sort by Name")).perform(click());
//
//        // Check if the selected text is displayed correctly
//        onView(withId(R.id.sortSpinner)).check(matches(withText("Sort by Name")));
//    }
//
//    @Test
//    public void testSetupSortingOptions() {
//        onView(withId(R.id.sortSpinner)).perform(click());
//        onView(withText("Sort by Name")).perform(click());
//        onView(withId(R.id.sortSpinner)).check(ViewAssertions.matches(ViewMatchers.withText("Sort By Name")));
//
////        onView(withId(R.id.sortSpinner)).perform(click());
////        onView(withText("Sort by Date")).perform(click());
////        onView(withId(R.id.sortSpinner)).check(matches(withText("Sort by Date")));
//    }
//
//    @Test
//    public void testFabButtonOnClick() {
//        onView(withId(R.id.fab)).perform(click());
//        onView(withText(R.string.add_note)).check(matches(ViewMatchers.isDisplayed()));
//    }

//    @Test
//    public void testDatabaseListener() {
//        // This is an integration test that requires a real or test Firebase instance.
//        // You need to set up Firebase with test data or mock data for this test to work.
//
//        // Check if RecyclerView is initially empty
//        onView(withId(R.id.recyclerview))
//                .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
//
//        // Assuming there is a test Firebase instance with preloaded data
//        // You should see the data in RecyclerView after data is loaded
//        onView(withId(R.id.recyclerview))
//                .perform(RecyclerViewActions.scrollToPosition(0))
//                .check(matches(ViewMatchers.hasDescendant(withText("Test Note Title"))));
//    }

}