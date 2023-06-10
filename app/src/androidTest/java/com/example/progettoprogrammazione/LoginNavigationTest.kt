package com.example.progettoprogrammazione

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.progettoprogrammazione.activity.IntroActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginNavigationTest {

    @Test
    fun test_Navigation_Intro_to_Login(){
        //Setup activity scenario
        val activityScenario= ActivityScenario.launch(IntroActivity::class.java)
        //Perform click action per andare a LogIn
        onView(withId(R.id.ConstraintLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.ConstraintLogin)).perform(click())

        onView(withId(R.id.fragment_login_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_login_test)).check(matches(isDisplayed()))

    }
    fun test_Navigation_Login_to_Register(){
        //Setup activity scenario
        val activityScenario= ActivityScenario.launch(IntroActivity::class.java)
        //premi per andare a fragmentLogin
        onView(withId(R.id.ConstraintLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.ConstraintLogin)).perform(click())
        //simula azione click su scritta no registrato
        onView(withId(R.id.noaccount)).perform(click())
        //controlla se è displayata il fragment registrati
        onView(withId(R.id.fragment_register_test)).check(matches(isDisplayed()))

        //onView(withId(R.id.fragmentRegister)).check(matches(isDisplayed()))
    }


}