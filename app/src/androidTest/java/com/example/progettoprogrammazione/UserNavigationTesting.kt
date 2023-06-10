package com.example.progettoprogrammazione

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.progettoprogrammazione.activity.IntroActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UserNavigationTesting {

    @Test
    fun test_Navigate_to_UserActivity(){
        //Setup activity scenario
        val activityScenario= ActivityScenario.launch(IntroActivity::class.java)
        //Perform click action per andare a LogIn
        Espresso.onView(ViewMatchers.withId(R.id.intro_activity)).perform(ViewActions.click())
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