package com.s29420.zad01;

public class BmiCalculator {

    public enum Category {
        UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESE
    }

    /** @throws IllegalArgumentException when height is zero or negative */
    public static double calculate(double weightKg, double heightCm) {
        if (heightCm <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }
        double heightM = heightCm / 100.0;
        return weightKg / (heightM * heightM);
    }

    public static Category categorize(double bmi) {
        if (bmi < 18.5) return Category.UNDERWEIGHT;
        if (bmi <= 24.9) return Category.NORMAL;
        if (bmi <= 29.9) return Category.OVERWEIGHT;
        return Category.OBESE;
    }

    public static String categoryLabel(Category category) {
        switch (category) {
            case UNDERWEIGHT: return "Niedowaga";
            case NORMAL:      return "Norma";
            case OVERWEIGHT:  return "Nadwaga";
            default:          return "Otyłość";
        }
    }
}
