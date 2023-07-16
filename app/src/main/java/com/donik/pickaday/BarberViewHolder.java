package com.donik.pickaday;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.donik.pickaday.R;

import java.util.Objects;

public class BarberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView barberPhotoImageView;
    private TextView barberNameTextView;
    private TextView barberRateTextView;
    private TextView barberRatingTextView;
    private BarbersAdapter adapter;
    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();

        // Update the selected item position via the adapter
        if (adapter != null) {
            adapter.setSelectedItem(position);
        }

    }
    public BarberViewHolder(View itemView, BarbersAdapter adapter) {
        super(itemView);
        barberPhotoImageView = itemView.findViewById(R.id.barberIconImageView);
        barberNameTextView = itemView.findViewById(R.id.barberNameTextView);
        barberRateTextView = itemView.findViewById(R.id.barberRateTextView);

        this.adapter = adapter;

        itemView.setOnClickListener(this);
    }

    public void bindData(Barber barber, int position, int selectedItem) {
        loadImageFromUrl(barber.getBarberPhoto(), barberPhotoImageView);

        barberNameTextView.setText(barber.getBarberName());
        barberRateTextView.setText("USD " +String.valueOf(barber.getBarberRate()));
        if(Objects.equals(barber.getBarberPhoto(), "1"))
            barberPhotoImageView.setImageResource(R.drawable.m1);
        else if(Objects.equals(barber.getBarberPhoto(), "2"))
            barberPhotoImageView.setImageResource(R.drawable.m2);
        else if(Objects.equals(barber.getBarberPhoto(), "3"))
            barberPhotoImageView.setImageResource(R.drawable.m3);
        else if(Objects.equals(barber.getBarberPhoto(), "4"))
            barberPhotoImageView.setImageResource(R.drawable.m4);
// Update the view based on the selected state
        if (position == selectedItem) {
            // Apply selected item style
            itemView.setBackgroundResource(R.drawable.selected_item_background);
            MainActivity.barber_selected = barber.getBarberID();
            MainActivity.Hairdresser=barber.getBarberName();
        } else {
            itemView.setBackgroundResource(R.drawable.service_item_background);
        }

    }

    private void loadImageFromUrl(String imageUrl, ImageView imageView) {
    }
}
