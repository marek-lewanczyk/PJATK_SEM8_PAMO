package com.s29420.zad01;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BmiActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<BmiActivity> activityRule =
            new ActivityScenarioRule<>(BmiActivity.class);

    @Test
    public void inputsAndButton_areDisplayed() {
        onView(withId(R.id.etWeightBmi)).check(matches(isDisplayed()));
        onView(withId(R.id.etHeightBmi)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCalculateBmi)).check(matches(isDisplayed()));
    }

    @Test
    public void calculateBmi_normalWeight_showsCorrectResult() {
        // 70 kg, 175 cm → BMI ≈ 22.86, kategoria Norma
        onView(withId(R.id.etWeightBmi)).perform(typeText("70"), closeSoftKeyboard());
        onView(withId(R.id.etHeightBmi)).perform(typeText("175"), closeSoftKeyboard());
        onView(withId(R.id.btnCalculateBmi)).perform(click());

        onView(withId(R.id.tvResultBmi)).check(matches(withText(containsString("22.86"))));
        onView(withId(R.id.tvResultBmi)).check(matches(withText(containsString("Norma"))));
    }

    @Test
    public void calculateBmi_overweight_showsOverweightCategory() {
        // 100 kg, 175 cm → BMI ≈ 32.65, kategoria Otyłość
        onView(withId(R.id.etWeightBmi)).perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.etHeightBmi)).perform(typeText("175"), closeSoftKeyboard());
        onView(withId(R.id.btnCalculateBmi)).perform(click());

        onView(withId(R.id.tvResultBmi)).check(matches(withText(containsString("Otyłość"))));
    }

    @Test
    public void calculateBmi_emptyFields_doesNotShowResult() {
        // Click without entering any data – result should remain empty
        onView(withId(R.id.btnCalculateBmi)).perform(click());
        onView(withId(R.id.tvResultBmi)).check(matches(withText("")));
    }
}
