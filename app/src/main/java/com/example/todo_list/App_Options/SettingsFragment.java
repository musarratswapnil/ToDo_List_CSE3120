package com.example.todo_list.App_Options;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.todo_list.R;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        LinearLayout accountButton = rootView.findViewById(R.id.accountButton);
        LinearLayout privacyButton = rootView.findViewById(R.id.privacyButton);
        LinearLayout helpButton = rootView.findViewById(R.id.helpButton);
        LinearLayout aboutButton = rootView.findViewById(R.id.aboutButton);

//        SwitchCompat darkModeSwitch = rootView.findViewById(R.id.switch1);
//        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // Toggle dark mode
////                toggleDarkMode(rootView, isChecked);
//            }
//        });

        setupButtonListeners(accountButton, "account");
        setupButtonListeners(privacyButton, "privacy");
        setupButtonListeners(helpButton, "help");
        setupButtonListeners(aboutButton, "about");

        return rootView;
    }

//    private void toggleDarkMode(View rootView, boolean isChecked) {
//        int nightModeFlags = rootView.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//        if (isChecked && nightModeFlags != Configuration.UI_MODE_NIGHT_YES) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else if (!isChecked && nightModeFlags != Configuration.UI_MODE_NIGHT_NO) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//    }

    private void setupButtonListeners(LinearLayout button, String fragmentType) {
        button.setOnClickListener(v -> {
            Fragment fragment = FragmentFactory.getFragment(fragmentType);
            if (fragment != null) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
