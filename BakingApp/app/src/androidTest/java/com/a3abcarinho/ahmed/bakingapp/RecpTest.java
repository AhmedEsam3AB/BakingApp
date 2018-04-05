package com.a3abcarinho.ahmed.bakingapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ahmed on 17/01/18.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecpTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);



    @Test
    public void RecpTest (){
        String firstItem = " - 2 CUP - Graham Cracker crumbs";

        ViewInteraction recyclerView = onView(allOf(withId(R.id.mainRV),withParent(withId(R.id.fragmentMain)),isDisplayed()));
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        recyclerView.perform(actionOnItemAtPosition(0,click()));
        ViewInteraction viewInteraction = onView(allOf(withId(R.id.ingredient_TV),withText(firstItem),childAtPosition(childAtPosition(withId(R.id.ingredientsRV),0),0),isDisplayed()));
        viewInteraction.check(matches(withText(firstItem)));




    }

    private Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {


            @Override
            public void describeTo(Description description) {
                description.appendText("Child at postition" + position + " in parent ");
                parentMatcher.describeTo(description);
            }


            @Override
            protected boolean matchesSafely(View item) {
                ViewParent viewParent = item.getParent();
                return viewParent instanceof ViewGroup && parentMatcher.matches(viewParent) && item.equals(((ViewGroup) viewParent).getChildAt(position));

            }
        };
    }

}
