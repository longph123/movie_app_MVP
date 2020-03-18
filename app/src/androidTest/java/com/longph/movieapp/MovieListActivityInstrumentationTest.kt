package com.longph.movieapp

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.longph.movieapp.domains.movie_list.MovieListActivity
import com.longph.movieapp.domains.movie_list.adapters.MovieListAdapter
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.app.Activity
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.InstrumentationRegistry
import android.support.test.runner.lifecycle.Stage
import android.support.v7.app.AppCompatActivity
import com.longph.movieapp.domains.movie_detail.MovieDetailActivity


@RunWith(AndroidJUnit4::class)
class MovieListActivityInstrumentationTest {

    @Rule
    @JvmField
    public val rule = ActivityTestRule(MovieListActivity::class.java)

    @Test
    fun checkStartNewActivity() {
        Espresso.onView(withId(R.id.activity_movie_list_rcv_movie_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<MovieListAdapter.MovieItemViewHolder>(0, click()))

        Assert.assertEquals(getActivityInstance().javaClass, MovieDetailActivity::class.java)
    }

    private fun getActivityInstance(): Activity {
        var currentActivities = ArrayList<Activity>()
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            currentActivities = ArrayList(resumedActivities)
        }

        if (currentActivities.size > 0) {
            return currentActivities[0]
        }

        return Activity()
    }
}