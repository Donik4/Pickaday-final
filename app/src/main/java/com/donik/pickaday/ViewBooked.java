package com.donik.pickaday;

import static com.donik.pickaday.MainActivity.UserID;
import static com.donik.pickaday.MainActivity.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.donik.pickaday.R;

import java.util.ArrayList;
import java.util.List;

public class ViewBooked extends AppCompatActivity   {

    Button home; public static bookedAdapter bA;
    ProgressBar pg;
ViewBooked viewBooked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booked);
viewBooked = this;
        RecyclerView bookedRecyclerView = findViewById(R.id.bookedRecyclerView);

        bookedRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider_line));
pg= findViewById(R.id.pg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        db.collection("Appointments")
                .whereEqualTo("bookedByID", UserID)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pg.setVisibility(View.INVISIBLE);
                            if (task.getResult().isEmpty()) {
                                // No matching user in Firestore users collection
                                Toast.makeText(ViewBooked.this, "No booking found", Toast.LENGTH_SHORT).show();
                            } else {
                                try{

                                    List<Booked> bookedList = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Booked booked = document.toObject(Booked.class);
                                        QuerySnapshot querySnapshot = task.getResult();

                                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

                                      booked.setDocID(documentSnapshot.getId());
                                        bookedList.add(booked);
                                    }
                                      bA = new bookedAdapter(bookedList,viewBooked);
                                    bookedRecyclerView.setAdapter(bA);
                                    bookedRecyclerView.setLayoutManager(layoutManager);
                                }
                                catch (Exception e){
                                    Toast.makeText(getApplicationContext(), "Error loading > "+e.getMessage() , Toast.LENGTH_LONG).show();

                                }

                            }
                        } else {
                            // Error accessing Firestore
                            Toast.makeText(ViewBooked.this, "Firestore error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        home = findViewById(R.id.home_but);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });
    }
}