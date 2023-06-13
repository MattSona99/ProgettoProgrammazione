package com.example.progettoprogrammazione

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.activity.UserActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UserNavigationTesting {

    @Test
    fun test_Navigate_to_UserActivity(){
        //Setup activity scenario
        val activityScenario= ActivityScenario.launch(IntroActivity::class.java)
        //Perform click action per andare a LogIn
        Espresso.onView(ViewMatchers.withId(R.id.ConstraintLogin))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.ConstraintLogin)).perform(ViewActions.click())
        //controllo che si veda
        Espresso.onView(ViewMatchers.withId(R.id.fragment_login_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.fragment_login_test))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
    /*
    @Test
    fun test_PageLogin_to_Login(){
        //Setup activity scenario
        val activityScenario= ActivityScenario.launch(IntroActivity::class.java)
        //Perform click action per andare a LogIn
        Espresso.onView(ViewMatchers.withId(R.id.ConstraintLogin))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.ConstraintLogin)).perform(ViewActions.click())

        //login action user livello 1
        val email:String="useruser@gmail.com"
        val pwd:String="useruser"

        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText(email))
        closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText(pwd))
        closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.ConstraintLogin)).perform(ViewActions.click())

        //logout e controllo che si veda login
        Espresso.onView(ViewMatchers.withId(R.id.ic_logoutU)).perform(ViewActions.click())
        //Perform click action per andare a LogIn
        Espresso.onView(ViewMatchers.withId(R.id.ConstraintLogin))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

     */


}