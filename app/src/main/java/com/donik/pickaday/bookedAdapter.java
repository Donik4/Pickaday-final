package com.donik.pickaday;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donik.pickaday.R;

import java.util.List;

public class bookedAdapter extends RecyclerView.Adapter<bookedViewHolder> implements bookedViewHolder.ViewHolderCallback {
    private List<Booked> bookedList;
    private int selectedItem = RecyclerView.NO_POSITION;
    private List<Booked> dataList;

    public void setSelectedItem(int position) {
        int previousSelected = selectedItem;
        selectedItem = position;
        notifyItemChanged(previousSelected);
        notifyItemChanged(selectedItem);
    }
    ViewBooked viewBooked;
    public bookedAdapter(List<Booked> bookedList, ViewBooked viewBooked) {
        this.bookedList = bookedList;
        this.viewBooked=viewBooked;
    }
    @Override
    public void onButtonClicked() {
        viewBooked.finish();

    }
    @NonNull
    @Override
    public bookedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_item, parent, false);
        bookedViewHolder viewHolder = new bookedViewHolder(itemView,this);
        viewHolder.setViewHolderCallback(this);

        return new bookedViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull bookedViewHolder holder, int position) {
        Booked booked = bookedList.get(position);
        holder.bindData(booked, position, selectedItem);
    }
    private List<ClipData.Item> itemList;

    public void updateItem(int position, ClipData.Item updatedItem) {
        itemList.set(position, updatedItem);
        notifyItemChanged(position);
    }
    @Override
    public int getItemCount() {
        return bookedList.size();
    }
}
