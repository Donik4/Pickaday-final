package com.donik.pickaday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.FirebaseApp;

import com.donik.pickaday.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView servicesRecyclerView;
    private RecyclerView barbersRecyclerView;
    public static     String Hairdresser="-", servicebooked="-";
    int serviceID=0, barberID=0;
    Button newbook, viewbook;
    public static FirebaseFirestore db;
    public  static String UserID="";
    public static  long service_selected=-1, barber_selected=-1;
    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newbook = findViewById(R.id.book);
        newbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(service_selected!=-1 && barber_selected!=-1)
                 appointment();
                else      Toast.makeText(getApplicationContext(), "Choose service and barber"  , Toast.LENGTH_LONG).show();

            }
        });
        viewbook = findViewById(R.id.viewButton);
        viewbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewBooked.class);

                startActivity(intent);
            }
        });
        FirebaseApp.initializeApp(this);
// Set up the RecyclerView for Services
        RecyclerView servicesRecyclerView = findViewById(R.id.servicesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        servicesRecyclerView.setLayoutManager(layoutManager);


// Set up the RecyclerView for Barbers
        RecyclerView barbersRecyclerView = findViewById(R.id.barbersRecyclerView);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        barbersRecyclerView.setLayoutManager(layoutManager2);



// Retrieving data from Firestore for Services collection
          db = FirebaseFirestore.getInstance();
        CollectionReference servicesCollectionRef = db.collection("Services");

        servicesCollectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
           try{     List<Services> serviceList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Services service = document.toObject(Services.class);
                    serviceList.add(service);
                }
                ServicesAdapter serviceAdapter = new ServicesAdapter(serviceList);
                servicesRecyclerView.setAdapter(serviceAdapter);
            }
                catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error adding documen > "+e.getMessage() , Toast.LENGTH_LONG).show();

            }
            } else {
                // Handle the error
                Exception exception = task.getException();
                if (exception != null) {
                    Log.e("Firebase", "Error retrieving data: " + exception.getMessage());
                }
            }
        });

// Retrieving data from Firestore for Barbers collection
        CollectionReference barbersCollectionRef = db.collection("Barbers");

        barbersCollectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                try {
                    List<Barber> barberList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Barber barber = document.toObject(Barber.class);
                        barberList.add(barber);
                    }
                    BarbersAdapter barberAdapter = new BarbersAdapter(barberList);
                    barbersRecyclerView.setAdapter(barberAdapter);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error adding documen > "+e.getMessage() , Toast.LENGTH_LONG).show();

                }
            } else {
                // Handle the error
                Exception exception = task.getException();
                if (exception != null) {
                    Log.e("Firebase", "Error retrieving data: " + exception.getMessage());
                }
            }
        });

        if(UserID.equals("")){
            Intent intent = new Intent(MainActivity.this, Login.class);

            startActivity(intent);
        }


    }
void appointment(){
    Intent intent = new Intent(MainActivity.this, Appointment.class);
    intent.putExtra("Hairdresser", Hairdresser);
    intent.putExtra("servicebooked", servicebooked);
    intent.putExtra("serviceID", service_selected);
    intent.putExtra("barberID", barber_selected);


    startActivity(intent);
}
}