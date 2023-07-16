package com.donik.pickaday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donik.pickaday.R;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServiceViewHolder> {
    private List<Services> serviceList;
    private int selectedItem = RecyclerView.NO_POSITION;
    private List<Services> dataList;


    public void setSelectedItem(int position) {
        int previousSelected = selectedItem;
        selectedItem = position;
        notifyItemChanged(previousSelected);
        notifyItemChanged(selectedItem);
    }
    public ServicesAdapter(List<Services> serviceList) {
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Services service = serviceList.get(position);
        holder.bindData(service, position, selectedItem);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }
}
