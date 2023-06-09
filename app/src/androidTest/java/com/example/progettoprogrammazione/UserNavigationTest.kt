package com.example.progettoprogrammazione

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.progettoprogrammazione.activity.UserActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UserNavigationTest {

    @Test
    fun test_isUserActivityInView(){
        //Setup activity scenario
        val activityScenario= ActivityScenario.launch(UserActivity::class.java)
        //Controllo activity in mostra
        Espresso.onView(ViewMatchers.withId(R.id.user_nav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    /*
    @Test
    fun test_Navigation_to_Fragment_Ristoranti(){
        //Setup activity scenario
        val activityScenario=ActivityScenario.launch(UserActivity::class.java)
        //Controllo activity in mostra
        Espresso.onView(ViewMatchers.withId(R.id.fragmentRistoranti))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

     */
}