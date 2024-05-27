package com.example.todo_list.App_Options;

import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.todo_list.App_Options.Model.Help;
import com.example.todo_list.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelpActivityTest {

    @Rule
    public ActivityScenarioRule<HelpActivity> activityScenarioRule = new ActivityScenarioRule<>(HelpActivity.class);

    @Before
    public void setUp() {
        List<Help> mockHelpList = new ArrayList<>();
        mockHelpList.add(new Help("How to add a task?", "Click the add button to add a new task."));
        mockHelpList.add(new Help("How to delete a task?", "Swipe left to delete a task."));

        // Mock FirebaseDatabaseHelper to return the mockHelpList
        FirebaseDatabaseHelper mockFirebaseDatabaseHelper = Mockito.mock(FirebaseDatabaseHelper.class);
        Mockito.doAnswer(invocation -> {
            FirebaseDatabaseHelper.DataStatus callback = invocation.getArgument(0);
            callback.DataIsLoaded(mockHelpList);
            return null;
        }).when(mockFirebaseDatabaseHelper).fetchHelpItems(Mockito.any());
    }

    @Test
    public void testRecyclerViewIsPopulated() {
        onView(withId(R.id.recyclerviewItem))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recyclerviewItem))
                .perform(scrollToPosition(0))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSearchFunctionality() {
        onView(withId(R.id.searchInput))
                .perform(typeText("add"));
        closeSoftKeyboard();
        onView(withId(R.id.recyclerviewItem))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recyclerviewItem))
                .perform(scrollToPosition(0))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testEmptySearchFunctionality() {
        onView(withId(R.id.searchInput))
                .perform(clearText(), typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.recyclerviewItem))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recyclerviewItem))
                .perform(scrollToPosition(0))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testNonMatchingSearch() {
        onView(withId(R.id.searchInput))
                .perform(typeText("non-existing query"));
        closeSoftKeyboard();
        onView(withId(R.id.recyclerviewItem))
                .check(matches(isDisplayed()));
    }
}
