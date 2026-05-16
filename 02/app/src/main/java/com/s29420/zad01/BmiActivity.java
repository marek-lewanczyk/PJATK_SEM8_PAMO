package com.s29420.zad01;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class BmiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etWeight = findViewById(R.id.etWeightBmi);
        EditText etHeight = findViewById(R.id.etHeightBmi);
        Button btnCalculate = findViewById(R.id.btnCalculateBmi);
        TextView tvResult = findViewById(R.id.tvResultBmi);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightStr = etWeight.getText().toString();
                String heightStr = etHeight.getText().toString();

                if (TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(heightStr)) {
                    Toast.makeText(BmiActivity.this, getString(R.string.error_fill_all_fields), Toast.LENGTH_SHORT).show();
                    return;
                }

                double weight = Double.parseDouble(weightStr);
                double heightCm = Double.parseDouble(heightStr);

                if (heightCm <= 0) {
                    Toast.makeText(BmiActivity.this, getString(R.string.error_height_positive), Toast.LENGTH_SHORT).show();
                    return;
                }

                double bmi = BmiCalculator.calculate(weight, heightCm);
                String category = BmiCalculator.categoryLabel(BmiCalculator.categorize(bmi));

                String result = String.format(Locale.getDefault(), "Twój BMI: %.2f\nKategoria: %s", bmi, category);
                tvResult.setText(result);
            }
        });
    }
}

