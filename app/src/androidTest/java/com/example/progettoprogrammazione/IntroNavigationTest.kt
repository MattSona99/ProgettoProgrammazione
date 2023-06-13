package com.example.progettoprogrammazione

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.progettoprogrammazione.activity.IntroActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class IntroNavigationTest {


    @get:Rule
    var activityRule: ActivityTestRule<IntroActivity>
            = ActivityTestRule(IntroActivity::class.java)

    private var LocatingActivity: IntroActivity? = null
    @Before
    fun setup() {
        LocatingActivity = activityRule.getActivity()
    }
    @Test
    fun test_isIntroActivityInView(){
        //Setup activity scenario
        val activityScenario=ActivityScenario.launch(IntroActivity::class.java)
        //Controllo activity in mostra
        onView(withId(R.id.intro_activity)).check(matches(isDisplayed()))
    }

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

}