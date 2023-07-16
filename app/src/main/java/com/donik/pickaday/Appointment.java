package com.donik.pickaday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
/*import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;*//**/
import com.google.api.services.calendar.CalendarScopes;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.Manifest;

import androidx.annotation.NonNull;

import com.donik.pickaday.R;


public class Appointment extends AppCompatActivity {
    TextView date_time;
    ImageView imageView;
    Button book;
    ProgressBar pg;
    // Inside your activity or fragment
    private static final int RC_SIGN_IN = 123;
    private static final int RC_ADD_EVENT = 456;
    Date d1, d2;
    int year = 0, m = 0, d = 0, start_h = 0, start_m = 0, end_h, end_m;
    DatePicker dp;
    TimePicker tp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int PERMISSION_REQUEST_CODE = 123;

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    String Hairdresser, servicebooked;
    EditText message;
    String id;
    long barberID, serviceID;
    long eventID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        Bundle bundle = getIntent().getExtras();

// getting the string back
        id = bundle.getString("id", "");
        String dt = bundle.getString("dt", "");
        String msg = bundle.getString("msg", "");
          eventID = bundle.getLong("eventID", 0);


        date_time = findViewById(R.id.date_time);
        message = findViewById(R.id.message_edittext);
        imageView = findViewById(R.id.imageView);
        book = findViewById(R.id.proceed_button);
        pg = findViewById(R.id.pg);

        Hairdresser = getIntent().getStringExtra("Hairdresser");
        servicebooked = getIntent().getStringExtra("servicebooked");
        servicebooked = getIntent().getStringExtra("servicebooked");
        barberID = getIntent().getLongExtra("barberID", 0);
        serviceID = getIntent().getLongExtra("serviceID", 0);
        if (dt.equals(""))
            date_time.setText("Tap here ");
        else
            date_time.setText(dt);
        message.setText(msg);
        // Check if the required permissions are granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED) {
            // Permissions are not granted, request them
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_CALENDAR,
                            Manifest.permission.WRITE_CALENDAR
                    },
                    PERMISSION_REQUEST_CODE);
        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dp != null && tp != null) {
                    book.setEnabled(false);
                    pg.setVisibility(View.VISIBLE);
                    if (id.equals(""))
                        insertCalendarEntry(dp, tp);
                    else {
                        deleteEvent(dp, tp);
                    }
                }
                    else
                        Toast.makeText(getApplicationContext(), "Choose the Date and Time for booking", Toast.LENGTH_LONG).show();
            }
        });
        date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


    }// Method to show the Date Picker Dialog

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Appointment.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the selected date
                        // Update the date and time TextView with the selected date
                        year = year;
                        m = month;
                        d = dayOfMonth;
                        dp = view;
                        date_time.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        showTimePickerDialog(); // Open Time Picker dialog after selecting the date
                    }
                },
                year,
                month,
                day
        );

        // Show the Date Picker dialog
        datePickerDialog.show();
    }

    // Method to show the Time Picker Dialog
    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                Appointment.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Handle the selected time
                        // Update the date and time TextView with the selected time
                        start_h = hourOfDay;
                        start_m = minute;
                        tp = view;
                        date_time.append(" " + hourOfDay + ":" + minute);
                    }
                },
                hour,
                minute,
                false
        );

        // Show the Time Picker dialog
        timePickerDialog.show();
    }

    // Request code for Sign-In
    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(CalendarScopes.CALENDAR))
                .build();

        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);
        startActivityForResult(signInClient.getSignInIntent(), RC_SIGN_IN);
    }
    public void deleteEvent(DatePicker datePicker, TimePicker timePicker) {
        ContentResolver contentResolver = getContentResolver();

        // Define the Uri for the event
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);

        // Delete the event
        int rows = contentResolver.delete(deleteUri, null, null);

        if (rows > 0) {
            updateCalendarEntry(datePicker, timePicker);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to edit calender event", Toast.LENGTH_SHORT).show();

            pg.setVisibility(View.GONE);

        }
    }

    private void updateCalendarEntry(DatePicker datePicker, TimePicker timePicker) {
        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                timePicker.getCurrentHour(),
                timePicker.getCurrentMinute());
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, calendar.getTimeInMillis());

        values.put(CalendarContract.Events.TITLE, "Hairdresser booking");
        values.put(CalendarContract.Events.DESCRIPTION, "Hairdresser booking with " + Hairdresser + " for " + servicebooked);
        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

        // default calendar
        values.put(CalendarContract.Events.CALENDAR_ID, 1);

        //for one hour
        values.put(CalendarContract.Events.DURATION, "+P1H");

        values.put(CalendarContract.Events.HAS_ALARM, 1);

        // insert event to calendar
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        long eventID = Long.parseLong(uri.getLastPathSegment());
        ContentValues reminderContentvalues = new ContentValues();
        reminderContentvalues.put(CalendarContract.Reminders.MINUTES, 30 * 24 * 60);
        reminderContentvalues.put(CalendarContract.Reminders.EVENT_ID, eventID);
        reminderContentvalues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderContentvalues);
        String mess = message.getText().toString();
        String date_timetxt = date_time.getText().toString();
        // Create a new user with a first and last name
        Map<String, Object> appointment = new HashMap<>();
        appointment.put("appointmentDateTime", date_timetxt);
        appointment.put("appointmentMessage", mess);
        appointment.put("eventID", eventID);


        // Perform the update operation
        MainActivity.db.collection("Appointments").document(id).update(appointment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {pg.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Booking updated", Toast.LENGTH_LONG).show();
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {pg.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error updating document > " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }
    private void insertCalendarEntry(DatePicker datePicker, TimePicker timePicker) {
        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                timePicker.getCurrentHour(),
                timePicker.getCurrentMinute());
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, calendar.getTimeInMillis());

        values.put(CalendarContract.Events.TITLE, "Hairdresser booking");
        values.put(CalendarContract.Events.DESCRIPTION, "Hairdresser booking with " + Hairdresser + " for " + servicebooked);
        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

        // default calendar
        values.put(CalendarContract.Events.CALENDAR_ID, 1);

        //for one hour
        values.put(CalendarContract.Events.DURATION, "+P1H");

        values.put(CalendarContract.Events.HAS_ALARM, 1);

        // insert event to calendar
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        long eventID = Long.parseLong(uri.getLastPathSegment());
        ContentValues reminderContentvalues = new ContentValues();
        reminderContentvalues.put(CalendarContract.Reminders.MINUTES, 30 * 24 * 60);
        reminderContentvalues.put(CalendarContract.Reminders.EVENT_ID, eventID);
        reminderContentvalues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderContentvalues);
        String mess = message.getText().toString();
        String date_timetxt = date_time.getText().toString();
        // Create a new user with a first and last name
        Map<String, Object> appointment = new HashMap<>();
        appointment.put("appointmentDateTime", date_timetxt);
        appointment.put("appointmentMessage", mess);
        appointment.put("barberName", Hairdresser);
        appointment.put("serviceName", servicebooked);
        appointment.put("barberID", barberID);
        appointment.put("serviceID", serviceID);
        appointment.put("bookedByID", MainActivity.UserID);
        appointment.put("eventID", eventID);

// Add a new document with a generated ID
        db.collection("Appointments")
                .add(appointment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {pg.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Booking Done", Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {pg.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error adding documen > " + e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }
}