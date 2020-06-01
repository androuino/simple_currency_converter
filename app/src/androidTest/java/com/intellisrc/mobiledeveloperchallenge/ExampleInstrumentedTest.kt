package com.intellisrc.mobiledeveloperchallenge

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.intellisrc.mobiledeveloperchallenge.ui.main.MainActivity
import com.intellisrc.mobiledeveloperchallenge.ui.main.MainFragmentViewModel
import com.intellisrc.mobiledeveloperchallenge.ui.main.repo.CurrencyLayerService
import com.zhuinden.simplestack.Backstack
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleInstrumentedTest {
    @JvmField
    @Rule
    var rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    @Mock
    val backstack: Backstack? = null
    @Mock
    var service: CurrencyLayerService? = null

    private var viewModel: MainFragmentViewModel? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainFragmentViewModel(backstack!!)
    }

    @Test
    fun getHistoricalData() {
        viewModel?.getHistoricalData()
    }

    @Test
    fun inputCurrency() {
        onView(withId(R.id.autoCompleteConvert)).perform(typeText("JPY"))
        onView(withId(R.id.etAmount)).perform(typeText("5000"))
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.intellisrc.mobiledeveloperchallenge", appContext.packageName)
    }
}
