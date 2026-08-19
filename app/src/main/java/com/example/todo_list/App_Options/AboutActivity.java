/**
 * The AboutActivity class represents the activity displaying information about the application.
 */
package com.example.todo_list.App_Options;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todo_list.R;

public class AboutActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. Initializes the activity and sets its content view.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this activity
        setContentView(R.layout.activity_about);
    }
}
