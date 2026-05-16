package com.s29420.zad01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalorieCalculatorTest {

    private static final double DELTA = 1.0;

    // --- calculateBmr() ---

    @Test
    public void calculateBmr_male_returnsExpectedValue() {
        // Male, 80 kg, 180 cm, 30 years
        // BMR = 88.362 + (13.397*80) + (4.799*180) - (5.677*30)
        //     = 88.362 + 1071.76 + 863.82 - 170.31 = 1853.63
        double bmr = CalorieCalculator.calculateBmr(80, 180, 30, true);
        assertEquals(1853.6, bmr, DELTA);
    }

    @Test
    public void calculateBmr_female_returnsExpectedValue() {
        // Female, 60 kg, 165 cm, 25 years
        // BMR = 447.593 + (9.247*60) + (3.098*165) - (4.330*25)
        //     = 447.593 + 554.82 + 511.17 - 108.25 = 1405.33
        double bmr = CalorieCalculator.calculateBmr(60, 165, 25, false);
        assertEquals(1405.3, bmr, DELTA);
    }

    @Test
    public void calculateBmr_maleBmrHigherThanFemale_sameParams() {
        double maleBmr = CalorieCalculator.calculateBmr(70, 170, 30, true);
        double femaleBmr = CalorieCalculator.calculateBmr(70, 170, 30, false);
        assertTrue("Male BMR should exceed female BMR for same params", maleBmr > femaleBmr);
    }

    // --- calculateTdee() ---

    @Test
    public void calculateTdee_sedentary_appliesMultiplier1_2() {
        double bmr = 1800;
        double tdee = CalorieCalculator.calculateTdee(bmr, CalorieCalculator.ActivityLevel.NONE);
        assertEquals(2160.0, tdee, DELTA);
    }

    @Test
    public void calculateTdee_highActivity_appliesMultiplier1_725() {
        double bmr = 2000;
        double tdee = CalorieCalculator.calculateTdee(bmr, CalorieCalculator.ActivityLevel.HIGH);
        assertEquals(3450.0, tdee, DELTA);
    }

    @Test
    public void calculateTdee_higherActivityYieldsHigherCalories() {
        double bmr = 1500;
        double tdeeLow = CalorieCalculator.calculateTdee(bmr, CalorieCalculator.ActivityLevel.LOW);
        double tdeeHigh = CalorieCalculator.calculateTdee(bmr, CalorieCalculator.ActivityLevel.HIGH);
        assertTrue(tdeeHigh > tdeeLow);
    }
}
