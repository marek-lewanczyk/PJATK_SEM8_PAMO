package com.s29420.zad01;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void allMenuButtons_areDisplayed() {
        onView(withId(R.id.btnBmi)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCalorie)).check(matches(isDisplayed()));
        onView(withId(R.id.btnBmiChart)).check(matches(isDisplayed()));
        onView(withId(R.id.btnShoppingList)).check(matches(isDisplayed()));
    }

    @Test
    public void clickBmiButton_opensBmiActivity() {
        onView(withId(R.id.btnBmi)).perform(click());
        onView(withId(R.id.btnCalculateBmi)).check(matches(isDisplayed()));
    }

    @Test
    public void clickCalorieButton_opensCalorieActivity() {
        onView(withId(R.id.btnCalorie)).perform(click());
        onView(withId(R.id.btnCalculateCalorie)).check(matches(isDisplayed()));
    }

    @Test
    public void clickBmiChartButton_opensBmiChartActivity() {
        onView(withId(R.id.btnBmiChart)).perform(click());
        onView(withId(R.id.lineChart)).check(matches(isDisplayed()));
    }

    @Test
    public void clickShoppingListButton_opensShoppingListActivity() {
        onView(withId(R.id.btnShoppingList)).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }
}
