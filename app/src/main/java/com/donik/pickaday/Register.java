package com.donik.pickaday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import com.donik.pickaday.R;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText editTextFullName, editTextEmail, editTextPassword;
    private Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

// Initialize EditText and Button views
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);



// Register button click listener
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText fields
                String fullName = editTextFullName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Perform validation
                if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> appointment = new HashMap<>();
                    appointment.put("user_name", fullName);
                    appointment.put("user_email", email    );
                    appointment.put("user_password", password);

// Add a new document with a generated ID
                    MainActivity.db.collection("Users")
                            .add(appointment)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "Registration Done" , Toast.LENGTH_LONG).show();
                                    // Clear input fields
                                    editTextFullName.setText("");
                                    editTextEmail.setText("");
                                    editTextPassword.setText("");
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error adding documen > "+e.getMessage() , Toast.LENGTH_LONG).show();

                                }
                            });
                }
            }
        });
    }
}