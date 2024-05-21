//package com.example.todo_list.StopWatch;
//
//import androidx.fragment.app.testing.FragmentScenario;
//import androidx.lifecycle.Lifecycle;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import com.example.todo_list.R;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
//import static androidx.test.espresso.matcher.ViewMatchers.Visibility;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class StopwatchFragmentTest {
//
//    @Test
//    public void testStopwatchInitialState() {
//        FragmentScenario<StopwatchFragment> scenario = FragmentScenario.launchInContainer(
//                StopwatchFragment.class, null, R.style.AppTheme, new EmptyFragmentActivity());
//
//        scenario.onFragment(fragment -> {
//            assertThat(fragment.stopWatchState, is(StopwatchFragment.STATE_INITIAL));
//        });
//
//        onView(withId(R.id.button_start_stop_watch)).check(matches(isDisplayed()));
//        onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_reset_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.textView_stopwatch_h)).check(matches(withText("00:")));
//        onView(withId(R.id.textView_stopwatch_m)).check(matches(withText("00:")));
//        onView(withId(R.id.textView_stopwatch_s)).check(matches(withText("00:")));
//        onView(withId(R.id.textView_stopwatch_10ms)).check(matches(withText("00")));
//    }
//
//    @Test
//    public void testStartStopWatch() {
//        FragmentScenario<StopwatchFragment> scenario = FragmentScenario.launchInContainer(
//                StopwatchFragment.class, null, R.style.AppTheme, new EmptyFragmentActivity());
//
//        onView(withId(R.id.button_start_stop_watch)).perform(click());
//
//        scenario.onFragment(fragment -> {
//            assertThat(fragment.stopWatchState, is(StopwatchFragment.STATE_START));
//        });
//
//        onView(withId(R.id.button_start_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_stop_stop_watch)).check(matches(isDisplayed()));
//        onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testStopStopWatch() {
//        FragmentScenario<StopwatchFragment> scenario = FragmentScenario.launchInContainer(
//                StopwatchFragment.class, null, R.style.AppTheme, new EmptyFragmentActivity());
//
//        onView(withId(R.id.button_start_stop_watch)).perform(click());
//        onView(withId(R.id.button_stop_stop_watch)).perform(click());
//
//        scenario.onFragment(fragment -> {
//            assertThat(fragment.stopWatchState, is(StopwatchFragment.STATE_STOP));
//        });
//
//        onView(withId(R.id.button_start_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_resume_stop_watch)).check(matches(isDisplayed()));
//        onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testResetStopWatch() {
//        FragmentScenario<StopwatchFragment> scenario = FragmentScenario.launchInContainer(
//                StopwatchFragment.class, null, R.style.AppTheme, new EmptyFragmentActivity());
//
//        onView(withId(R.id.button_start_stop_watch)).perform(click());
//        onView(withId(R.id.button_stop_stop_watch)).perform(click());
//        onView(withId(R.id.button_reset_stop_watch)).perform(click());
//
//        scenario.onFragment(fragment -> {
//            assertThat(fragment.stopWatchState, is(StopwatchFragment.STATE_INITIAL));
//        });
//
//        onView(withId(R.id.button_start_stop_watch)).check(matches(isDisplayed()));
//        onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_reset_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.textView_stopwatch_h)).check(matches(withText("00:")));
//        onView(withId(R.id.textView_stopwatch_m)).check(matches(withText("00:")));
//        onView(withId(R.id.textView_stopwatch_s)).check(matches(withText("00:")));
//        onView(withId(R.id.textView_stopwatch_10ms)).check(matches(withText("00")));
//    }
//
//    @Test
//    public void testResumeStopWatch() {
//        FragmentScenario<StopwatchFragment> scenario = FragmentScenario.launchInContainer(
//                StopwatchFragment.class, null, R.style.AppTheme, new EmptyFragmentActivity());
//
//        onView(withId(R.id.button_start_stop_watch)).perform(click());
//        onView(withId(R.id.button_stop_stop_watch)).perform(click());
//        onView(withId(R.id.button_resume_stop_watch)).perform(click());
//
//        scenario.onFragment(fragment -> {
//            assertThat(fragment.stopWatchState, is(StopwatchFragment.STATE_START));
//        });
//
//        onView(withId(R.id.button_start_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_stop_stop_watch)).check(matches(isDisplayed()));
//        onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(Visibility.GONE)));
//        onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testFragmentLifecycle() {
//        FragmentScenario<StopwatchFragment> scenario = FragmentScenario.launchInContainer(
//                StopwatchFragment.class, null, R.style.AppTheme, new EmptyFragmentActivity());
//        scenario.moveToState(Lifecycle.State.RESUMED);
//        scenario.recreate();
//
//        scenario.onFragment(fragment -> {
//            assertThat(fragment.stopWatchState, is(StopwatchFragment.STATE_INITIAL));
//        });
//    }
//}
