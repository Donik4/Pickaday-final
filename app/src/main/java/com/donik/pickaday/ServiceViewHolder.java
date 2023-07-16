package com.donik.pickaday;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.donik.pickaday.R;

import java.util.Objects;

public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView serviceNameTextView;
    private TextView serviceRateTextView;
    private ImageView serviceIcon;
    private ServicesAdapter adapter;

    public ServiceViewHolder(View itemView, ServicesAdapter adapter) {
        super(itemView);
        serviceNameTextView = itemView.findViewById(R.id.serviceTitleTextView);
        serviceRateTextView = itemView.findViewById(R.id.serviceRateTextView);
        serviceIcon = itemView.findViewById(R.id.serviceIconImageView);
        this.adapter = adapter;

        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();

        if (adapter != null) {
            adapter.setSelectedItem(position);
        }
    }
    public void bindData(Services item, int position, int selectedItem) {
        // Bind data to the ViewHolder
        serviceNameTextView.setText(item.getServiceName());
        serviceRateTextView.setText("USD " + String.valueOf(item.getServiceRate()));

        if(Objects.equals(item.getServiceIcon(), "1"))
            serviceIcon.setImageResource(R.drawable.razor);
        else if(Objects.equals(item.getServiceIcon(), "2"))
            serviceIcon.setImageResource(R.drawable.spa_facial);
        else if(Objects.equals(item.getServiceIcon(), "3"))
            serviceIcon.setImageResource(R.drawable.hair_style);
        else if(Objects.equals(item.getServiceIcon(), "4"))
            serviceIcon.setImageResource(R.drawable.scissors);


        // Update the view based on the selected state
        if (position == selectedItem) {
            // Apply selected item style
            itemView.setBackgroundResource(R.drawable.selected_item_background);
            MainActivity.service_selected = item.getServiceID();
            MainActivity.servicebooked =item.getServiceName();
        } else {
            itemView.setBackgroundResource(R.drawable.service_item_background);
        }
    }

}
