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
                    Toast.makeText(CalorieActivity.this, getString(R.string.error_fill_all_fields), Toast.LENGTH_SHORT).show();
                    return;
                }

                double age = Double.parseDouble(ageStr);
                double weight = Double.parseDouble(weightStr);
                double height = Double.parseDouble(heightStr);

                boolean isMale = rbMale.isChecked();

                double bmr = CalorieCalculator.calculateBmr(weight, height, age, isMale);

                CalorieCalculator.ActivityLevel[] levels = CalorieCalculator.ActivityLevel.values();
                int selectedPosition = spinnerActivity.getSelectedItemPosition();
                CalorieCalculator.ActivityLevel level = levels[Math.min(selectedPosition, levels.length - 1)];

                double tdee = CalorieCalculator.calculateTdee(bmr, level);
                int tdeeRounded = (int) Math.round(tdee);

                tvResult.setText(String.format(Locale.getDefault(), "Twoje zapotrzebowanie to:\n%d kcal", tdeeRounded));
            }
        });
    }
}

