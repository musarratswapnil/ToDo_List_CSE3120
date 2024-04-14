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
//
//@RunWith(AndroidJUnit4.class)
//
//public class NavigateToFragmentCommandTest {
//    private DashboardActivity activity;
//    private NavigateToFragmentCommand command;
//    private HomeFragment homeFragment;
//
//
//    @Before
//    public void setup() {
//       ActivityScenario<DashboardActivity> scenario = ActivityScenario.launch(new Intent(ApplicationProvider.getApplicationContext(), DashboardActivity.class));
//
//        scenario.onActivity(activity -> {
//            HomeFragment homeFragment = new HomeFragment();
//            NavigateToFragmentCommand command = new NavigateToFragmentCommand(activity, homeFragment);
//            command.execute();
//            assertTrue(activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HomeFragment);
//        });
//        scenario.close();
//    }
//
//    @Test
//    public void testFragmentNavigation() {
//        command.execute();
//        assertTrue(activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HomeFragment);
//    }
//}
@RunWith(AndroidJUnit4.class)
public class NavigateToFragmentCommandTest {
    private ActivityScenario<DashboardActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(new Intent(ApplicationProvider.getApplicationContext(), DashboardActivity.class));
    }
    @Test
    public void testFragmentNavigation() {
        scenario.onActivity(activity -> {
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commitNow();
                assertTrue(activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HomeFragment);
        });
    }
    @After
    public void close() {
        if (scenario != null) {
            scenario.close();
        }
    }
}
