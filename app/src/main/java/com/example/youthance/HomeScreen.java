package com.example.youthance;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeScreen extends AppCompatActivity {

    Spinner citySpinner;
    LinearLayout dobpickerLinearLayout, cityLinearLayout;
    TextView dobTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        dobpickerLinearLayout = findViewById(R.id.dobpickerLinearLayout);
        cityLinearLayout = findViewById(R.id.cityLinearLayout);
        citySpinner = findViewById(R.id.citySpinner);
        dobTextView = findViewById(R.id.dobTextView);

        ArrayList<String> cityArrayList = new ArrayList<>();
        cityArrayList.add("Select City");
        cityArrayList.add("Delhi");
        cityArrayList.add("Bombay");
        cityArrayList.add("Banglore");

        ArrayAdapter<String> cityArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cityArrayList);
        citySpinner.setAdapter(cityArrayAdapter);

        dobpickerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeScreen.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dobTextView.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

    }
}