package com.example.newcoursework.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.newcoursework.Fragments.CreateFragment;
import com.example.newcoursework.Fragments.ListFragment;
import com.example.newcoursework.Fragments.SearchFragment;
import com.example.newcoursework.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    CreateFragment createFragment = new CreateFragment();
    ListFragment listFragment = new ListFragment();
    SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.create);

        Intent intent = getIntent();
        if (intent != null) {
            String selectedFragment = intent.getStringExtra("selectedFragment");
            if (selectedFragment != null)
            {
                navigate(selectedFragment);
            } else
            {
                navigate("create");
                bottomNavigationView.setSelectedItemId(R.id.create);
            }
        }
    }

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.create)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, createFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.list)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, listFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.search)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, searchFragment)
                    .commit();
            return true;
        }
        return false;
    }
    public void navigate(String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentTag.equals("list"))
        {
            fragmentTransaction.replace(R.id.flFragment, listFragment);
            bottomNavigationView.setSelectedItemId(R.id.list);
        } else if (fragmentTag.equals("create"))
        {
            fragmentTransaction.replace(R.id.flFragment, createFragment);
            bottomNavigationView.setSelectedItemId(R.id.create);
        } else if (fragmentTag.equals("search"))
        {
            fragmentTransaction.replace(R.id.flFragment, searchFragment);
            bottomNavigationView.setSelectedItemId(R.id.search);
        }
        fragmentTransaction.commit();
    }
}