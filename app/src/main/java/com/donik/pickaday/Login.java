package com.donik.pickaday;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.donik.pickaday.R;

public class Login extends AppCompatActivity {

    LinearLayout ll;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin,buttonRegister;
    ProgressBar pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize EditText and Button views
        editTextEmail = findViewById(R.id.editTextEmail);
        ll = findViewById(R.id.mainll);
        pg = findViewById(R.id.pg);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(Login.this, Register.class);
             startActivity(intent);
        }
        });
        // Login button click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
        ll.setEnabled(false);
                // Perform validation
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.db.collection("Users")
                            .whereEqualTo("user_email", email)
                            .whereEqualTo("user_password", password)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().isEmpty()) {
                                            // No matching user in Firestore users collection
                                            // Display an error message or handle the case accordingly
                                            Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // User exists in Firestore users collection
                                            QuerySnapshot querySnapshot = task.getResult();
                                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                            String documentId = documentSnapshot.getId();
                                            MainActivity.UserID = documentId;
                                            finish();
                                        }
                                    } else {
                                        ll.setEnabled(true);
                                        pg.setVisibility(View.GONE);

                                        // Error accessing Firestore
                                        Toast.makeText(Login.this, "Firestore error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        // Disable back arrow function by leaving this method empty
        // This will prevent the activity from navigating back
    }
}