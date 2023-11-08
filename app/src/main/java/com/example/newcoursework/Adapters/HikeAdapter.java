package com.example.newcoursework.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newcoursework.Models.Hike;
import com.example.newcoursework.R;

import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.ContactViewHolder> {
    Button deleteBtn;
    private List<Hike> hikes;
    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onDeleteClick(Hike hike);
        void onEditClick(Hike hike);
    }

    public HikeAdapter(List<Hike> hikes, OnClickListener onClickListener) {
        this.hikes = hikes;
        this.onClickListener = onClickListener;
    }
    @Override
    public int getItemCount() {
        return hikes.size();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hike_name);
            deleteBtn = itemView.findViewById(R.id.deleteButton);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.name.setText(hike.hikeName);
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onEditClick(hikes.get(position));
            }
        });
        deleteBtn.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onDeleteClick(hikes.get(position));
            }
        });
    }
}
