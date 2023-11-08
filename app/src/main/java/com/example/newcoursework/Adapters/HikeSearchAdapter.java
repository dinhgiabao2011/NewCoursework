package com.example.newcoursework.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newcoursework.Models.Hike;
import com.example.newcoursework.R;

import java.util.List;

public class HikeSearchAdapter extends RecyclerView.Adapter<HikeSearchAdapter.ContactViewHolder> {
    private List<Hike> hikes;

    public HikeSearchAdapter(List<Hike> hikes) {
        this.hikes = hikes;
    }

    @NonNull
    @Override
    public HikeSearchAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.hike_search_item, parent, false);
        return new HikeSearchAdapter.ContactViewHolder(itemView);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView searchName;
        TextView searchLocation;
        TextView searchDate;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            searchName = itemView.findViewById(R.id.hike_search_name);
            searchDate = itemView.findViewById(R.id.hike_search_location);
            searchLocation = itemView.findViewById(R.id.hike_search_date);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HikeSearchAdapter.ContactViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.searchName.setText(hike.hikeName);
        holder.searchLocation.setText(hike.hikeLocation);
        holder.searchDate.setText(hike.hikeDate);
    }

    @Override
    public int getItemCount() {
        return hikes.size();
    }
}
