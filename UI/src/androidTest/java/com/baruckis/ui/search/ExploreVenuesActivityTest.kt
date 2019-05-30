/*
 * Copyright 2019 Andrius Baruckis www.baruckis.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baruckis.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.baruckis.domain.model.Venue
import com.baruckis.ui.R
import com.baruckis.ui.TestAndroidDataFactory
import com.baruckis.ui.TestVenuesNearbyApp
import io.reactivex.Single
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@LargeTest
class ExploreVenuesActivityTest {

    @Rule
    @JvmField
    // Third parameter is set to false which means the activity is not started automatically.
    val mActivityTestRule = ActivityTestRule<ExploreVenuesActivity>(ExploreVenuesActivity::class.java, false, false)

    private val venues = TestAndroidDataFactory.createVenuesList()


    @Test
    fun activityLaunch() {
        mActivityTestRule.launchActivity(null)
    }


    @Test
    fun exploreVenuesActivityShowDataOnSearch() {

        stubGetVenuesNearby(Single.just(venues))

        mActivityTestRule.launchActivity(null)

        val actionMenuItemView = onView(
            Matchers.allOf(
                withId(R.id.search), withContentDescription("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(ViewActions.click())

        val searchAutoComplete = onView(
            Matchers.allOf(
                withId(R.id.search_src_text),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(
            ViewActions.replaceText(TestAndroidDataFactory.createSearchPlaceName()),
            ViewActions.closeSoftKeyboard()
        )

        val searchAutoComplete2 = onView(
            Matchers.allOf(
                withId(R.id.search_src_text), withText(TestAndroidDataFactory.createSearchPlaceName()),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete2.perform(ViewActions.pressImeActionButton())

        venues.forEachIndexed { index, venue ->
            onView(withId(R.id.recyclerview)).perform(
                RecyclerViewActions.scrollToPosition<ExploreVenuesRecyclerViewAdapter.BindingViewHolder>(
                    index
                )
            )

            onView(withId(R.id.recyclerview)).check(ViewAssertions.matches(hasDescendant(withText(venue.name))))
        }
    }

    private fun stubGetVenuesNearby(single: Single<List<Venue>>) {
        Mockito.`when`(
            TestVenuesNearbyApp.testAppComponent().getVenuesDomainRepository()
                .getVenuesNearby(TestAndroidDataFactory.createSearchPlaceName())
        )
            .thenReturn(single)
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}