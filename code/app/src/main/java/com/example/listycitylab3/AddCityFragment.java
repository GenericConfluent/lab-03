package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity();
    }

    private AddCityDialogListener listener;

    public static AddCityFragment newInstance(City city) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("city", city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        Bundle args = getArguments();
        City city = (City)args.getSerializable("city");

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        final String positiveText;
        final String title;

        if (city == null) {
            positiveText = "Add";
            title = "Add a city";
        } else {
            positiveText = "Edit";
            title = "Edit a city";
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positiveText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (city == null) {
                        listener.addCity(new City(cityName, provinceName));
                    } else {
                        // NOTE: This works since Bundle is actually just a
                        // Map<String, Object> under the hood. This is probably bad
                        // practice though.
                        city.setName(cityName);
                        city.setProvince(provinceName);
                        listener.updateCity();
                    }
                })
                .create();
    }
}
