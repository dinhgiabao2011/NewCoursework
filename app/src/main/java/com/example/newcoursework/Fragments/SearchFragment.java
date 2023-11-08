package com.example.newcoursework.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.newcoursework.Adapters.HikeSearchAdapter;
import com.example.newcoursework.Database.AppDatabase;
import com.example.newcoursework.Models.Hike;
import com.example.newcoursework.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    AppDatabase appDatabase;
    HikeSearchAdapter hikeSearchAdapter;
    List<Hike> hikes = new ArrayList<Hike>();
    RecyclerView recyclerView;
    EditText searchText;
    Button searchBtn;

    public SearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "hikes")
                      .allowMainThreadQueries()
                      .fallbackToDestructiveMigration()
                      .build();

        searchText = v.findViewById(R.id.searchTxt);
        searchBtn = v.findViewById(R.id.searchButton);
        recyclerView = v.findViewById(R.id.hike_search_list);
        searchBtn.setOnClickListener(view -> searchHike());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hikeSearchAdapter = new HikeSearchAdapter(hikes);
        recyclerView.setAdapter(hikeSearchAdapter);
        return v;
    }
    public void searchHike(){
        String search = searchText.getText().toString();
        hikes = appDatabase.hikeDao().findByName(search);
        hikeSearchAdapter = new HikeSearchAdapter(hikes);
        recyclerView.setAdapter(hikeSearchAdapter);
    }
}