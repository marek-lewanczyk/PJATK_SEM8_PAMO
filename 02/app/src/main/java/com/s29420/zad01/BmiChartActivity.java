package com.s29420.zad01;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class BmiChartActivity extends AppCompatActivity {

    // Mocked BMI data: 12 months, showing gradual improvement from Nadwaga to Norma
    private static final String[] MONTHS = {
            "Sty", "Lut", "Mar", "Kwi", "Maj", "Cze",
            "Lip", "Sie", "Wrz", "Paź", "Lis", "Gru"
    };
    private static final float[] BMI_VALUES = {
            27.5f, 27.2f, 26.8f, 26.5f, 26.1f, 25.8f,
            25.4f, 25.1f, 24.8f, 24.6f, 24.3f, 24.1f
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi_chart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LineChart chart = findViewById(R.id.lineChart);
        setupChart(chart);
    }

    private void setupChart(LineChart chart) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < BMI_VALUES.length; i++) {
            entries.add(new Entry(i, BMI_VALUES[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "BMI");
        dataSet.setColor(Color.parseColor("#2196F3"));
        dataSet.setCircleColor(Color.parseColor("#2196F3"));
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawCircleHole(true);
        dataSet.setCircleHoleRadius(3f);
        dataSet.setCircleHoleColor(Color.WHITE);
        dataSet.setValueTextSize(9f);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#BBDEFB"));
        dataSet.setFillAlpha(80);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        chart.setData(new LineData(dataSet));

        // X axis – month labels at bottom
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(MONTHS));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(MONTHS.length);
        xAxis.setDrawGridLines(false);

        // Y axis – range 18–30 with limit lines for BMI categories
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(18f);
        leftAxis.setAxisMaximum(30f);
        leftAxis.setGranularity(1f);

        LimitLine overweightLine = new LimitLine(25f, "Nadwaga");
        overweightLine.setLineColor(Color.parseColor("#F44336"));
        overweightLine.setLineWidth(1.5f);
        overweightLine.setTextColor(Color.parseColor("#F44336"));
        overweightLine.setTextSize(10f);
        leftAxis.addLimitLine(overweightLine);

        LimitLine underweightLine = new LimitLine(18.5f, "Niedowaga");
        underweightLine.setLineColor(Color.parseColor("#FF9800"));
        underweightLine.setLineWidth(1.5f);
        underweightLine.setTextColor(Color.parseColor("#FF9800"));
        underweightLine.setTextSize(10f);
        leftAxis.addLimitLine(underweightLine);

        chart.getAxisRight().setEnabled(false);

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        chart.getLegend().setEnabled(true);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.animateX(1500);
        chart.invalidate();
    }
}
