package com.s29420.zad01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class BmiCalculatorTest {

    private static final double DELTA = 0.01;

    // --- calculate() ---

    @Test
    public void calculate_normalWeight_returnsCorrectBmi() {
        // 70 kg, 175 cm → BMI = 70 / (1.75²) = 22.86
        double bmi = BmiCalculator.calculate(70, 175);
        assertEquals(22.86, bmi, DELTA);
    }

    @Test
    public void calculate_overweight_returnsCorrectBmi() {
        // 90 kg, 175 cm → BMI = 90 / (1.75²) = 29.39
        double bmi = BmiCalculator.calculate(90, 175);
        assertEquals(29.39, bmi, DELTA);
    }

    @Test
    public void calculate_zeroHeight_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> BmiCalculator.calculate(70, 0));
    }

    @Test
    public void calculate_negativeHeight_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> BmiCalculator.calculate(70, -5));
    }

    // --- categorize() ---

    @Test
    public void categorize_bmiBelow18_5_returnsUnderweight() {
        assertEquals(BmiCalculator.Category.UNDERWEIGHT, BmiCalculator.categorize(17.0));
    }

    @Test
    public void categorize_bmiAt18_5_returnsNormal() {
        assertEquals(BmiCalculator.Category.NORMAL, BmiCalculator.categorize(18.5));
    }

    @Test
    public void categorize_bmiAt22_returnsNormal() {
        assertEquals(BmiCalculator.Category.NORMAL, BmiCalculator.categorize(22.0));
    }

    @Test
    public void categorize_bmiAt24_9_returnsNormal() {
        assertEquals(BmiCalculator.Category.NORMAL, BmiCalculator.categorize(24.9));
    }

    @Test
    public void categorize_bmiAt25_returnsOverweight() {
        assertEquals(BmiCalculator.Category.OVERWEIGHT, BmiCalculator.categorize(25.0));
    }

    @Test
    public void categorize_bmiAt29_9_returnsOverweight() {
        assertEquals(BmiCalculator.Category.OVERWEIGHT, BmiCalculator.categorize(29.9));
    }

    @Test
    public void categorize_bmiAt30_returnsObese() {
        assertEquals(BmiCalculator.Category.OBESE, BmiCalculator.categorize(30.0));
    }

    // --- categoryLabel() ---

    @Test
    public void categoryLabel_underweight_returnsPolishLabel() {
        assertEquals("Niedowaga", BmiCalculator.categoryLabel(BmiCalculator.Category.UNDERWEIGHT));
    }

    @Test
    public void categoryLabel_normal_returnsPolishLabel() {
        assertEquals("Norma", BmiCalculator.categoryLabel(BmiCalculator.Category.NORMAL));
    }

    @Test
    public void categoryLabel_overweight_returnsPolishLabel() {
        assertEquals("Nadwaga", BmiCalculator.categoryLabel(BmiCalculator.Category.OVERWEIGHT));
    }

    @Test
    public void categoryLabel_obese_returnsPolishLabel() {
        assertEquals("Otyłość", BmiCalculator.categoryLabel(BmiCalculator.Category.OBESE));
    }
}
