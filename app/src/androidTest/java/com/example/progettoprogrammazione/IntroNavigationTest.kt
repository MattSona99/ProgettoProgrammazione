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
class IntroNavigationTest {

    //private lateinit var scenario: FragmentScenario<FragmentIntro>

    /*
    @Before
    fun navIntro() {

        scenario = launchFragmentInContainer<FragmentIntro>()
        scenario.moveToState(Lifecycle.State.STARTED)
    }
     */

    //working
    @Test
    fun test_isIntroActivityInView(){
        //Setup activity scenario
        val activityScenario=ActivityScenario.launch(IntroActivity::class.java)
        //Controllo activity in mostra
        onView(withId(R.id.intro_activity)).check(matches(isDisplayed()))
    }

    //working
    @Test
    fun test_visibility_LoginBtn_and_strings(){
        //Setup activity scenario
        val activityScenario=ActivityScenario.launch(IntroActivity::class.java)
        //Controllo activity in mostra
        onView(withId(R.id.image_intro)).check(matches(isDisplayed()))
        onView(withId(R.id.benvenuto_text)).check(matches(isDisplayed()))
        onView(withId(R.id.login)).check(matches(isDisplayed()))
        onView(withId(R.id.registrati)).check(matches(isDisplayed()))

    }

    @Test
    fun test_Navigation_to_Fragment_Intro(){
        //Setup activity scenario
        val activityScenario=ActivityScenario.launch(IntroActivity::class.java)
        //Controllo activity in mostra
        onView(withId(R.id.fragmentIntro)).check(matches(isDisplayed()))
    }

    @Test
    fun test_Intro_Fragment_Navigation(){
        //Setup activity scenario
        val activityScenario=ActivityScenario.launch(IntroActivity::class.java)
        //NAV TO FRAGMENT-INTRO fragment
        onView(withId(R.id.ConstraintLogin)).perform(click())

    }

}