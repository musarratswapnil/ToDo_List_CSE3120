package com.example.todo_list.App_Options; // Adjust the package name based on where you place this file

import androidx.fragment.app.Fragment;

//import com.example.todo_list.App_Options.AccountFragment;
import com.example.todo_list.App_Options.PrivacyFragment;
import com.example.todo_list.App_Options.HelpFragment;
import com.example.todo_list.App_Options.AboutFragment;
//import com.example.todo_list.StopWatch.StopwatchFragment;
//import com.example.todo_list.StopWatch.TimerFragment;

public class FragmentFactory {

    public static Fragment getFragment(String type) {
        if ("account".equals(type)) {
//            return new AccountFragment();
            return null;
        } else if ("privacy".equals(type)) {
            return new PrivacyFragment();
        } else if ("help".equals(type)) {
            return new HelpFragment();
        } else if ("about".equals(type)) {
            return new AboutFragment();
        } else {
            return null;
        }
    }


//    public static final byte STATUS_STOP_WATCH = 1;
//    public static final byte STATUS_TIMER = 2;
//
//    public static Fragment createFragment(byte status) {
//        switch (status) {
//            case STATUS_STOP_WATCH:
//                return new StopwatchFragment();
//            case STATUS_TIMER:
//                return new TimerFragment();
//            default:
//                throw new IllegalArgumentException("Invalid status");
//        }
//    }
}
