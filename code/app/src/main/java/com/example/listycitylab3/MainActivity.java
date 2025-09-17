package com.example.listycitylab3;

import static java.lang.Math.min;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener{

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Moscow",
                "Sydney", "Berlin", "Vienna",
                "Tokyo", "Beijing", "Osaka", "New Delhi"
        };

        String[] province = {
                "AB", "AB", "HU",
                "TR", "RE", "AL",
                "WA", "NT", "PB"
        };

        dataList = new ArrayList<>();
        for (int i = 0; i < min(province.length, cities.length); ++i) {
            dataList.add(new City(cities[i], province[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);
        // Edit
        cityList.setOnItemClickListener((adapterView, view, i, l) -> {
            AddCityFragment.newInstance(cityAdapter.getItem(i)).show(getSupportFragmentManager(), "Edit City");
        });

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            AddCityFragment.newInstance(null).show(getSupportFragmentManager(), "Add City");
        });
    }

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCity() {
        cityAdapter.notifyDataSetChanged();
    }
}