package com.s29420.zad01;

public class CalorieCalculator {

    public enum ActivityLevel {
        NONE(1.2),
        LOW(1.375),
        MEDIUM(1.55),
        HIGH(1.725);

        final double pal;
        ActivityLevel(double pal) { this.pal = pal; }
    }

    /**
     * Returns BMR using the Harris-Benedict (revised Mifflin–St Jeor) formula.
     * All inputs must be positive.
     */
    public static double calculateBmr(double weightKg, double heightCm, double age, boolean male) {
        if (male) {
            return 88.362 + (13.397 * weightKg) + (4.799 * heightCm) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weightKg) + (3.098 * heightCm) - (4.330 * age);
        }
    }

    public static double calculateTdee(double bmr, ActivityLevel level) {
        return bmr * level.pal;
    }
}
