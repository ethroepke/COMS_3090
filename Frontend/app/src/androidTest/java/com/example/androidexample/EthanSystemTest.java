package com.example.androidexample;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
public class EthanSystemTest {

    @Rule
    public ActivityTestRule<loginActivity> activityRule = new ActivityTestRule<>(loginActivity.class);

    @Test
    public void testLoginSuccess() {
        onView(withId(R.id.usernameInput)).perform(typeText("testUser"));
        onView(withId(R.id.passwordInput)).perform(typeText("testPass"));
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.mainMessage)).check(matches(withText("Login successful")));
    }

    @Test
    public void testLoginFailure() {
        onView(withId(R.id.usernameInput)).perform(typeText("wrongUser"));
        onView(withId(R.id.passwordInput)).perform(typeText("wrongPass"));
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.mainMessage)).check(matches(withText("Login failed. Please try again.")));
    }

    @Test
    public void testEmptyFields() {
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.mainMessage)).check(matches(withText("Please enter both username and password.")));
    }
}
