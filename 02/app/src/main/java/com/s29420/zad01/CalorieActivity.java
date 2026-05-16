package com.s29420.zad01;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class CalorieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calorie);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etAge = findViewById(R.id.etAge);
        EditText etWeight = findViewById(R.id.etWeight);
        EditText etHeight = findViewById(R.id.etHeight);
        RadioGroup rgGender = findViewById(R.id.rgGender);
        RadioButton rbMale = findViewById(R.id.rbMale);
        Spinner spinnerActivity = findViewById(R.id.spinnerActivity);
        Button btnCalculate = findViewById(R.id.btnCalculateCalorie);
        TextView tvResult = findViewById(R.id.tvResultCalorie);

        String[] activities = {"Brak", "Niska", "Średnia", "Wysoka"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, activities);
        spinnerActivity.setAdapter(adapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ageStr = etAge.getText().toString();
                String weightStr = etWeight.getText().toString();
                String heightStr = etHeight.getText().toString();

                if (TextUtils.isEmpty(ageStr) || TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(heightStr)) {
                    Toast.makeText(CalorieActivity.this, "Proszę uzupełnić wszystkie pola", Toast.LENGTH_SHORT).show();
                    return;
                }

                double age = Double.parseDouble(ageStr);
                double weight = Double.parseDouble(weightStr);
                double height = Double.parseDouble(heightStr);

                boolean isMale = rbMale.isChecked();

                double bmr;
                if (isMale) {
                    bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
                } else {
                    bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
                }

                double pal = 1.2;
                int selectedPosition = spinnerActivity.getSelectedItemPosition();
                if (selectedPosition == 1) {
                    pal = 1.375;
                } else if (selectedPosition == 2) {
                    pal = 1.55;
                } else if (selectedPosition == 3) {
                    pal = 1.725;
                }

                double tdee = bmr * pal;
                int tdeeRounded = (int) Math.round(tdee);

                tvResult.setText(String.format(Locale.getDefault(), "Twoje zapotrzebowanie to:\n%d kcal", tdeeRounded));
            }
        });
    }
}

