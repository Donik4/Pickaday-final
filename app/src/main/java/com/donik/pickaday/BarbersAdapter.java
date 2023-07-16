package com.donik.pickaday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donik.pickaday.R;

import java.util.List;

public class BarbersAdapter extends RecyclerView.Adapter<BarberViewHolder> {
    private List<Barber> barberList;

    public BarbersAdapter(List<Barber> barberList) {
        this.barberList = barberList;
    }
    public void setSelectedItem(int position) {
        int previousSelected = selectedItem;
        selectedItem = position;
        notifyItemChanged(previousSelected);
        notifyItemChanged(selectedItem);
    }
    @NonNull
    @Override
    public BarberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.barber_item, parent, false);
        return new BarberViewHolder(itemView, this);
    }
    private int selectedItem = RecyclerView.NO_POSITION;
    @Override
    public void onBindViewHolder(@NonNull BarberViewHolder holder, int position) {
        Barber barber = barberList.get(position);
        holder.bindData(barber, position, selectedItem);
    }

    @Override
    public int getItemCount() {
        return barberList.size();
    }
}
