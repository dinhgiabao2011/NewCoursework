package com.example.newcoursework.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.newcoursework.Database.AppDatabase;
import com.example.newcoursework.Models.Hike;
import com.example.newcoursework.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;

public class CreateFragment extends Fragment {
    AppDatabase appDatabase;
    EditText nameText, locationText, lengthText, descriptionText;
    TextView dateText;
    RadioGroup parkingGroup;
    Spinner levelSpinner;
    View view;
    public CreateFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create, container, false);
        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "hikes")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        nameText = view.findViewById(R.id.name);
        locationText = view.findViewById(R.id.location);
        dateText = view.findViewById(R.id.date);
        parkingGroup = view.findViewById(R.id.radioGroup);
        lengthText = view.findViewById(R.id.length);
        levelSpinner = view.findViewById(R.id.spinner);
        descriptionText = view.findViewById(R.id.description);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getChildFragmentManager(), "date");
            }
        });

        Button saveDetailsButton = view.findViewById(R.id.createButton);
        saveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHike();
            }
        });

        return view;
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
        {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();
            return new DatePickerDialog(getActivity(), this, year, --month, day);}
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day){
            LocalDate date = LocalDate.of(year, ++month, day);
            ((CreateFragment)getParentFragment()).updateDate(date);
        }
    }

    public void updateDate(LocalDate date){
        dateText.setText(date.toString());
    }
    public void addHike() {
        String nameValue = nameText.getText().toString();
        String locationValue = locationText.getText().toString();
        String dateValue = dateText.getText().toString();
        String lengthValue = lengthText.getText().toString();
        Long levelValue = levelSpinner.getSelectedItemId();
        String descriptionValue = descriptionText.getText().toString();
        int selectedRadioButtonId = parkingGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = view.findViewById(selectedRadioButtonId);

        if(nameValue.trim().isEmpty() || locationValue.trim().isEmpty() ||
           dateValue.trim().isEmpty() || lengthValue.trim().isEmpty() ||
           descriptionValue.trim().isEmpty() || selectedRadioButtonId == -1)
        {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please fill all field before create.")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .show();
            return;
        }
        String parkingTag = (String)selectedRadioButton.getTag();
        new AlertDialog.Builder(getActivity())
                .setTitle("Confirmation")
                .setMessage("Name: " + nameValue + "\nLocation: " + locationValue
                            + "\nHike Date: " + dateValue + "\nParking available: " +
                            (parkingTag.equals("true") ? "Yes" : "No") + "\nHike length: " +
                            lengthValue + "\nDifficulty level: " + levelValue + "\n\n Are you sure?")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Yes", (dialog, which) -> {
                    Hike hike = new Hike();
                    hike.hikeName = nameValue;
                    hike.hikeLocation = locationValue;
                    hike.hikeDate = dateValue;
                    hike.isParking = parkingTag.equals("true") ? true : false;
                    hike.hikeLength = Float.parseFloat(lengthValue);
                    hike.hikeLevel = Math.toIntExact(levelValue);
                    hike.hikeDescription = descriptionValue;
                    appDatabase.hikeDao().insertHike(hike);
                    switchToListFragment();
                })
                .show();
    }

    public void switchToListFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, new ListFragment());
        fragmentTransaction.commit();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem(R.id.list).setChecked(true);
    }
}