package com.example.townbus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    EditText journeyFrom;
    Transport bus, flight;

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        Handler handler = new Handler();

        journeyFrom = findViewById(R.id.from);
        Button getLocation = findViewById(R.id.get_location_home);
        Spinner journeyTo = findViewById(R.id.to_spinner);
        Button searchTickets = findViewById(R.id.search_tickets);
        searchTickets.setEnabled(true);

        CardView busCardView = findViewById(R.id.select_bus_card);
        busCardView.setEnabled(false);
        CardView flightCardView = findViewById(R.id.select_flight_card);
        flightCardView.setEnabled(false);
        Button logoutHome = findViewById(R.id.logout_home);
        TextView userGreetingTextView = findViewById(R.id.user_greeting_home);

        assert currentUser != null;
        String userGreetingFormatted = "Hi, " + currentUser.getDisplayName() + "!";
        userGreetingTextView.setText(userGreetingFormatted);

        searchTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = journeyFrom.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(HomeActivity.this, "Give your journey details", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(HomeActivity.this, "Select the Bus or Flight", Toast.LENGTH_SHORT).show();
                busCardView.setEnabled(true);
                flightCardView.setEnabled(true);
            }
        });


        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        logoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        busCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!busCardView.isEnabled()) {
                    Toast.makeText(HomeActivity.this, "Give Journey Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), SelectBusActivity.class);
                intent.putExtra("JOURNEY_FROM", journeyFrom.getText().toString());
                intent.putExtra("JOURNEY_TO", journeyTo.getSelectedItem().toString());
                startActivity(intent);
                finish();
            }
        });

        flightCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flightCardView.isEnabled()) {
                    Toast.makeText(HomeActivity.this, "Give Journey Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), SelectFlightActivity.class);
                intent.putExtra("JOURNEY_FROM", journeyFrom.getText().toString());
                intent.putExtra("JOURNEY_TO", journeyTo.getSelectedItem().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                List<Address> addresses;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    journeyFrom.setText(addresses.get(0).getAddressLine(0));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                askPermission();
                            }
                        }
                    });
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Location Access Required", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}