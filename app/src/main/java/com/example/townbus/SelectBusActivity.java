package com.example.townbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SelectBusActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Transport> list;
    MyAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bus);

        Intent intent = getIntent();
        String journeyFrom = intent.getStringExtra("JOURNEY_FROM");
        String journeyTo = intent.getStringExtra("JOURNEY_TO");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.bus_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Transport bus = new Transport();

        list = new ArrayList<>();

        // Set data - pending - RELL



        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
    }
}