package com.abu_siddiq.groceryapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.abu_siddiq.groceryapp", appContext.packageName)
    }

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addItems() {

        Espresso.onView(withId(R.id.fabAdd)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.add_item))
            .perform(ViewActions.typeText("Tomato"))

        Espresso.onView(withId(R.id.add_item_quantity))
            .perform(ViewActions.typeText("100"))

        Espresso.onView(withId(R.id.add_item_price))
            .perform(ViewActions.typeText("30"))

        Espresso.onView(withId(R.id.add_item)).perform(ViewActions.click())

    }

    @Test
    fun cancelButton() {
        Espresso.onView(withId(R.id.fabAdd)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.item_cancel)).perform(ViewActions.click())
    }
}