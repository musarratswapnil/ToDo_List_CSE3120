package com.example.todo_list.App_Options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo_list.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * This activity provides a contact form for users to send messages.
 * It also displays a map with a marker at the location of Khulna University of Engineering & Technology.
 */

public class ContactActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap mMap;
    DatabaseReference usersRef;

    private TextView msg;
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the most recent data supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Initialize the MapView
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        msg=findViewById(R.id.textView8);

        // Initialize the Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        // Retrieve references to the views
        final EditText messageEditText = findViewById(R.id.message);
        final EditText emailAddressEditText = findViewById(R.id.editTextTextEmailAddress);
        Button sendMessageButton = findViewById(R.id.sendMessage);

        // Set click listener for the send message button
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the values entered by the user
                String message = messageEditText.getText().toString().trim();
                String emailAddress = emailAddressEditText.getText().toString().trim();

                // Check if any of the EditText fields is empty
                if (message.isEmpty() || emailAddress.isEmpty()) {
                    showToast("Please fill in all fields");
                    if (message.isEmpty()) {
                        messageEditText.setError("This field is required");
                    }
                    if (emailAddress.isEmpty()) {
                        emailAddressEditText.setError("This field is required");
                    }
                    return;
                }
                if (!isValidEmail(emailAddress)) {
                    showToast("Invalid email address");
                    emailAddressEditText.setError("Invalid email address");
                    return;
                }
                // Generate the current datetime
                String datetime = getCurrentDateTime();

                // Assuming you have implemented Firebase Authentication and the user is authenticated
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference userRef = usersRef.child(userId).child("feedback");
                DatabaseReference feedbackRef = userRef.child(datetime);
                feedbackRef.child("message").setValue(message, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            // Message saved successfully
                            showToast("Message sent successfully");
                            // Clear the EditText views
                            messageEditText.setText("");
                            emailAddressEditText.setText("");

                        } else {
                            // Error occurred while saving data
                            showToast("Error: " + error.getMessage());
                        }
                    }
                });
                feedbackRef.child("emailAddress").setValue(emailAddress);
            }
        });

        // Set focus change listener for the email address field
        emailAddressEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Check if the email address is valid
                    String emailAddress = emailAddressEditText.getText().toString().trim();
                    if (!isValidEmail(emailAddress)) {
                        emailAddressEditText.setError("Invalid email address");
                    } else {
                        emailAddressEditText.setError(null);
                    }
                }
            }
        });
    }
    /**
     * Gets the current date and time in the format "yyyy-MM-dd HH:mm:ss".
     * @return A string representing the current date and time.
     */
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    /**
     * Checks if the provided email address is valid.
     * @param target The email address to validate.
     * @return true if the email address is valid, false otherwise.
     */
    private boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    /**
     * Displays a toast message.
     * @param message The message to display in the toast.
     */
    private void showToast(String message) {
        Toast.makeText(ContactActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    /**
     * Called when the map is ready to be used.
     * @param googleMap The GoogleMap object representing the map.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // New coordinates for Khulna University Of Engineering and Technology
        LatLng location = new LatLng(22.8997, 89.5023); // Coordinates of Khulna University
        float zoomLevel = 15.0f; // Adjust zoom level as needed for best view

        mMap.addMarker(new MarkerOptions().position(location).title("Khulna University of Engineering & Technology"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));

        // Set the marker click listener
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Zoom in further when marker is clicked
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17.0f));
                return true;
            }
        });
    }
}
