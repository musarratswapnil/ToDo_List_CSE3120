package com.example.todo_list.DashBoard_Option;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todo_list.AttendanceCalculator.AttendanceCalculator;
import com.example.todo_list.Reminder.HomeFragment;
import com.example.todo_list.Note.HomeScreen;
import com.example.todo_list.R;
import com.example.todo_list.StopWatch.StopWatchHomeActivity;
import com.google.android.material.card.MaterialCardView;

/**
 * Defines the command interface for executing navigation actions.
 * It is implemented using the Behavioral design pattern name 'Command'.
 */
interface Command {
    /**
     * Executes the navigation action encapsulated by this command.
     */
    void execute();
}

/**
 * Command for navigating to a specific Fragment.
 */
class NavigateToFragmentCommand implements Command {
    private Context context;
    private Fragment fragment;

    /**
     * Constructs a new command for fragment navigation.
     * @param context The context from which the navigation is initiated.
     * @param fragment The fragment to navigate to.
     */
    NavigateToFragmentCommand(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    /**
     * Executes the command to replace the current fragment with the specified fragment.
     */
    @Override
    public void execute() {
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}

/**
 * Command for launching a new Activity.
 */
class NavigateToActivityCommand implements Command {
    private Context context;
    private Class<? extends Activity> activityClass;

    /**
     * Constructs a new command for activity navigation.
     * @param context The context from which the navigation is initiated.
     * @param activityClass The class of the Activity to be launched.
     */
    public NavigateToActivityCommand(Context context, Class<? extends Activity> activityClass) {
        this.context = context;
        this.activityClass = activityClass;
    }

    /**
     * Executes the command to launch the specified activity.
     */
    @Override
    public void execute() {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
}

/**
 * Invoker class that executes commands based on the command pattern.
 */
class CommandInvoker {
    private Command command;

    /**
     * Sets the command to be executed.
     * @param command The command to execute.
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Executes the currently set command.
     */
    public void executeCommand() {
        if (command != null) {
            command.execute();
        }
    }
}

/**
 * OptionFragment class to handle the dashboard options.
 */
public class OptionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_option, container, false);

        // Find the card views
        MaterialCardView remainderCard = view.findViewById(R.id.RemainderCard);
        MaterialCardView noteCard = view.findViewById(R.id.NoteCard);
        MaterialCardView stopwatchCard = view.findViewById(R.id.StopWatchCard);
        MaterialCardView attendanceCalculator = view.findViewById(R.id.AttendanceCalculatorCard);

        // Create command invoker
        CommandInvoker invoker = new CommandInvoker();

        // Set click listener for the RemainderCard
        remainderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoker.setCommand(new NavigateToFragmentCommand(getActivity(), new HomeFragment()));
                invoker.executeCommand();
            }
        });

        // Set click listener for the NoteCard
        noteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoker.setCommand(new NavigateToActivityCommand(getActivity(), HomeScreen.class));
                invoker.executeCommand();
            }
        });

        // Set click listener for the StopWatchCard
        stopwatchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoker.setCommand(new NavigateToActivityCommand(getActivity(), StopWatchHomeActivity.class));
                invoker.executeCommand();
            }
        });

        // Set click listener for the AttendanceCalculatorCard
        attendanceCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoker.setCommand(new NavigateToActivityCommand(getActivity(), AttendanceCalculator.class));
                invoker.executeCommand();
            }
        });

        return view;
    }
}
