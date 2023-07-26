package com.example.townbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SelectBusActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Transport> list;
    MyAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bus);
        String journeyFrom = "";
        String journeyTo = "";
        String dateOfJourney = "";
//        Intent intent = getIntent();
//        String journeyFrom = intent.getStringExtra("JOURNEY_FROM");
//        String journeyTo = intent.getStringExtra("JOURNEY_TO");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            journeyFrom = bundle.getString("JOURNEY_FROM");
            journeyTo = bundle.getString("JOURNEY_TO");
            dateOfJourney = bundle.getString("DATE_OF_JOURNEY");
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.bus_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        list = new ArrayList<>();

        // Set data - pending - RELL
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String finalJourneyFrom = "FROM: " + journeyFrom;
        String finalJourneyTo = "TO: " + journeyTo;
        String finalDateOfJourney = "Date of Journey: " + dateOfJourney;
        db.collection("busData/")
                .get()
                .addOnSuccessListener(task -> {
                    for (DocumentSnapshot ds : task.getDocuments()) {
                        Transport bus = new Transport();
                        bus.setTransportOperator(String.valueOf(ds.get("busOperator")));
                        bus.setTransportFrom(finalJourneyFrom.toUpperCase());
                        bus.setTransportTo(finalJourneyTo);
                        bus.setTransportDepartureDate(finalDateOfJourney);
                        list.add(bus);
                    }
                    adapter = new MyAdapter(this, list);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SelectBusActivity.this, "Error! Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}