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

import com.example.todo_list.Reminder.HomeFragment;
import com.example.todo_list.Note.HomeScreen;
import com.example.todo_list.R;
import com.google.android.material.card.MaterialCardView;

/**
 * Defines the command interface for executing navigation actions
 * It is implemented using Behavioural design pattern name 'Command'
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

    /**
     * Constructs a new command for activity navigation.
     * @param context The context from which the navigation is initiated.
     */
    NavigateToFragmentCommand(Context context) {
        this.context = context;
    }

    /**
     * Executes the command to launch the Activity we want
     */
    @Override
    public void execute() {
        Fragment fragment = new HomeFragment();
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
     * Executes the command to launch the Activity we want
     */
    @Override
    public void execute() {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
}

/**
 * Invoker class that executes commands on basis of setting specific command
 * Invoker is a basic part of command behavioural Design Pattern
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
     * Executes the currently set dynamic command.
     */
    public void executeCommand() {
        if (command != null) {
            command.execute();
        }
    }
}
    public class OptionFragment extends Fragment {

        /**
         *
         * @param inflater The LayoutInflater object that can be used to inflate
         * any views in the fragment,
         * @param container If non-null, this is the parent view that the fragment's
         * UI should be attached to.  The fragment should not add the view itself,
         * but this can be used to generate the LayoutParams of the view.
         * @param savedInstanceState If non-null, this fragment is being re-constructed
         * from a previous saved state as given here.
         *
         * @return Return the View for the fragment's UI
         */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_option, container, false);

        /**
         *  Find the card views
         */
        MaterialCardView remainderCard = view.findViewById(R.id.RemainderCard);
        MaterialCardView noteCard = view.findViewById(R.id.NoteCard);
        /**
         * Create command invoker
         */
        CommandInvoker invoker = new CommandInvoker();

        /**
         * Add 1st option
         *Set click listener for the RemainderCard
         */
        remainderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set fragment command and go to next fragment
                invoker.setCommand(new NavigateToFragmentCommand(getActivity()));
                invoker.executeCommand();
            }
        });

        /**
         * Add 2nd option
         *Set click listener for the RemainderCard
         */
        noteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Set fragment command and execute
                 */
                invoker.setCommand(new NavigateToActivityCommand(getActivity(), HomeScreen.class));
                invoker.executeCommand();
            }
        });

        return view;
    }
}
