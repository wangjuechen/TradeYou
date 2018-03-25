package com.jc.android.tradeyou.ui.activity;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.jc.android.tradeyou.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListingCategoryTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void listingCategoryTest() {
        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.text_marketPlace), withText("Marketplace"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        2),
                                0)));
        appCompatTextView.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.text_category_name), withText("Baby gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_categoryList),
                                        2),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Baby gear")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_categoryList),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.text_listing_category_first_condition), withText("Baby gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView_category_condition),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Baby gear")));

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.text_listing_category_first_condition), withText("Baby gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView_category_condition),
                                        0),
                                0)));
        appCompatTextView2.perform(scrollTo(), click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("Baby room & furniture"),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        childAtPosition(
                                                withId(R.id.contentPanel),
                                                0)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Baby room & furniture")));

        DataInteraction appCompatTextView3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(0);
        appCompatTextView3.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
