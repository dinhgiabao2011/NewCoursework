package com.example.newcoursework.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.newcoursework.Database.AppDatabase;
import com.example.newcoursework.Fragments.CreateFragment;
import com.example.newcoursework.Models.Hike;
import com.example.newcoursework.R;

import java.time.LocalDate;

public class UpdateActivity extends AppCompatActivity {
    Hike hike;
    AppDatabase appDatabase;
    EditText nameText, locationText, lengthText, descriptionText;
    Button back, saveBtn;
    TextView dateText;
    RadioGroup parkingGroup;
    Spinner levelSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        appDatabase = Room.databaseBuilder(this.getApplicationContext(), AppDatabase.class,
                        "hikes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        nameText = findViewById(R.id.nameTxt);
        locationText = findViewById(R.id.locationTxt);
        dateText = findViewById(R.id.dateTxt);
        parkingGroup = findViewById(R.id.parkingGroup);
        lengthText = findViewById(R.id.lengthTxt);
        levelSpinner = findViewById(R.id.levelSpinner);
        descriptionText = findViewById(R.id.descriptionTxt);
        back = findViewById(R.id.back);
        saveBtn = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        if (intent != null) {
            hike = intent.getSerializableExtra("Hike", Hike.class);
            if (hike != null)
            {
                nameText.setText(hike.hikeName);
                locationText.setText(hike.hikeLocation);
                dateText.setText(hike.hikeDate);
                int selectedRadioButtonId = hike.isParking ? R.id.yesValue : R.id.noValue;
                parkingGroup.check(selectedRadioButtonId);
                lengthText.setText(hike.hikeLength.toString());
                levelSpinner.setSelection(hike.hikeLevel);
                descriptionText.setText(hike.hikeDescription);
            }
        }
        saveBtn.setOnClickListener(view -> updateHike());
        back.setOnClickListener(view -> moveToListFragment());
    }

    public void updateHike() {
        String nameValue = nameText.getText().toString().trim();
        String locationValue = locationText.getText().toString().trim();
        String dateValue = dateText.getText().toString().trim();
        String lengthValue = lengthText.getText().toString().trim();
        String descriptionValue = descriptionText.getText().toString().trim();
        int selectedRadioButtonId = parkingGroup.getCheckedRadioButtonId();

        if (nameValue.isEmpty() || locationValue.isEmpty() ||
            dateValue.equals("Date") || lengthValue.isEmpty() ||
            selectedRadioButtonId == -1 || descriptionValue.isEmpty())
        {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please fill all field before update.")
                    .setNeutralButton("Ok", (dialogInterface, i) -> {
                    })
                    .show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String parkingTag = (String) selectedRadioButton.getTag();
        long levelValue = levelSpinner.getSelectedItemId();

        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Name: " + nameValue + "\nLocation: " + locationValue
                        + "\nHike Date: " + dateValue + "\nParking available: " +
                        (parkingTag.equals("true") ? "Yes" : "No") + "\nHike length: " +
                        lengthValue + "\nDifficulty level: " + levelValue + "\n\n Are you sure?")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Yes", (dialog, which) -> {
                    hike.hikeName = nameValue;
                    hike.hikeLocation = locationValue;
                    hike.hikeDate = dateValue;
                    hike.isParking = parkingTag.equals("true") ? true : false;
                    hike.hikeLength = Float.parseFloat(lengthValue);
                    hike.hikeLevel = Math.toIntExact(levelValue);
                    hike.hikeDescription = descriptionValue;
                    appDatabase.hikeDao().update(hike);
                    moveToListFragment();
                })
                .show();
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

    public void moveToListFragment(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("selectedFragment", "list");
        startActivity(intent);
    }
}