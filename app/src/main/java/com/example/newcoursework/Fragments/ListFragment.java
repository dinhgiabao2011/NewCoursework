package com.example.newcoursework.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.newcoursework.Activities.UpdateActivity;
import com.example.newcoursework.Adapters.HikeAdapter;
import com.example.newcoursework.Database.AppDatabase;
import com.example.newcoursework.Models.Hike;
import com.example.newcoursework.R;

import java.util.List;

public class ListFragment extends Fragment implements HikeAdapter.OnClickListener {

    AppDatabase appDatabase;
    RecyclerView recyclerView;
    HikeAdapter hikeAdapter;
    List<Hike> hikes;
    Button deleteAllBtn;

    public ListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "hikes")
                      .allowMainThreadQueries()
                      .fallbackToDestructiveMigration()
                      .build();
        deleteAllBtn = view.findViewById(R.id.deleteAllButton);
        recyclerView = view.findViewById(R.id.hike_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hikes = appDatabase.hikeDao().getAllHike();
        hikeAdapter = new HikeAdapter(hikes,this);
        recyclerView.setAdapter(hikeAdapter);
        deleteAllBtn.setOnClickListener(v -> onDeleteAllClick());
        return view;
    }
    @Override
    public void onDeleteClick(Hike hike) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete Hike")
                .setMessage("Are you sure you want to delete this hike?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    appDatabase.hikeDao().deleteHike(hike);
                    hikes.remove(hike);
                    hikeAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void onDeleteAllClick(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete All Hikes")
                .setMessage("Are you sure you want to delete all hikes?")
                .setPositiveButton("Delete All", (dialog, which) -> {
                    appDatabase.hikeDao().deleteAllHike();
                    hikes.clear();
                    hikeAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    @Override
    public void onEditClick(Hike hike){
        Intent intent = new Intent(getActivity(), UpdateActivity.class);
        intent.putExtra("Hike", hike);
        startActivity(intent);
    }
}