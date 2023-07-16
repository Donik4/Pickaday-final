package com.donik.pickaday;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import com.donik.pickaday.R;

public class bookedViewHolder extends RecyclerView.ViewHolder   {
    private TextView tv2;
    private TextView tvName,tv4,tvmsg;
    private ImageView deleteButton,editButton;
    long eventID;
    private bookedAdapter adapter;
    private DocumentReference documentReference;
String id;

    public interface ViewHolderCallback {
        void onButtonClicked();
    }
    private bookedAdapter callback;
    // Setter for the callback
    public void setViewHolderCallback(bookedAdapter callback) {
        this.callback = callback;
    }

    public bookedViewHolder(View itemView, bookedAdapter adapter) {
        super(itemView);
        tv2 = itemView.findViewById(R.id.tv2);
        tvName = itemView.findViewById(R.id.tvName);
        tv4 = itemView.findViewById(R.id.serv);
        tvmsg = itemView.findViewById(R.id.tvmsg);
        deleteButton = itemView.findViewById(R.id.delbut);
        editButton = itemView.findViewById(R.id.editbut);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                documentReference = MainActivity.db.collection("Appointments").document(id);

                showDeleteConfirmationDialog();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editButton.getContext(), Appointment.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("dt", tv2.getText().toString());
                bundle.putString("msg", tvmsg.getText().toString());
                bundle.putLong("eventID", eventID);
                intent.putExtras(bundle);
                editButton.getContext().startActivity(intent);

            }
        });
        this.adapter = adapter;

    }
    private void showDeleteConfirmationDialog() {
        // Show a confirmation dialog asking the user if they want to delete
        AlertDialog.Builder builder = new AlertDialog.Builder(deleteButton.getContext());
        builder.setTitle("Delete Booking")
                .setMessage("Are you sure you want to delete this booking?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecord();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void deleteRecord() {
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(deleteButton.getContext(), "Record deleted successfully", Toast.LENGTH_SHORT).show();
                    deleteEvent();
                } else {
                    Toast.makeText(deleteButton.getContext(), "Failed to delete record", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteEvent() {
        ContentResolver contentResolver = deleteButton.getContext().getContentResolver();

        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);

        int rows = contentResolver.delete(deleteUri, null, null);

        if (rows > 0) {
            Toast.makeText(deleteButton.getContext(), "Calender event deleted successfully", Toast.LENGTH_SHORT).show();
            if (callback != null) {
                callback.onButtonClicked();
            }
          } else {
            Toast.makeText(deleteButton.getContext(), "Failed to delete calender event", Toast.LENGTH_SHORT).show();

        }
    }
    public void bindData(Booked item, int position, int selectedItem) {
        // Bind data to the ViewHolder
        tv2.setText(item.getAppointmentDateTime());
        tvmsg.setText(item.getAppointmentMessage());
        tv4.setText(item.getServiceName());
        tvName.setText(item.getBarberName());
        id=item.getDocID();
        eventID= item.getEventID();
    }
}