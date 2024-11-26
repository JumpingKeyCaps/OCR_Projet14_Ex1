package com.kirabium.relayance

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.ui.activity.DetailActivity
import com.kirabium.relayance.ui.activity.MainActivity
import com.kirabium.relayance.util.RecyclerViewItemCountAssertion
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        // Initialize Intents before each test
        Intents.init()
    }

    @After
    fun tearDown() {
        // Release resources after each test
        Intents.release()
    }

    /**
     * Test si la recyclerView est bien affichée avec le bon nombre d'items
     */
    @Test
    fun testClientListHasFiveItems() {
        // Launch MainActivity
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // Check that the RecyclerView contains 5 items using the RecyclerViewItemCountAssertion
        onView(withId(R.id.customerRecyclerView))
            .check(RecyclerViewItemCountAssertion.withItemCount(5))

        scenario.close()
    }

    /**
     * Test si DetailActivity est bien lancé au click sur un des items client de la recyclerView
     */
    @Test
    fun testClientItemLaunchesDetailActivity() {
        // Launch MainActivity
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // Simulate a click on the first item in the RecyclerView
        onView(withId(R.id.customerRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Verify that the correct Intent is launched with the correct ID
        Intents.intended(allOf(
            hasComponent(DetailActivity::class.java.name),
            hasExtra(DetailActivity.EXTRA_CUSTOMER_ID, 1)
        ))

        scenario.close()
    }

    /**
     * Test d'intégration pour le lancement de la DetailActivity avec un client spécifique
     */
    @Test
    fun testLaunchDetailActivityWithAliceWonderland() {
        // Create an Intent for launching DetailActivity with Alice's ID
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_CUSTOMER_ID, 1) // Alice Wonderland's ID
        }

        // Launch the DetailActivity with the pre-configured Intent
        val scenario = ActivityScenario.launch<DetailActivity>(intent)

        // Use ComposeTestRule to interact with the Compose UI
        composeTestRule.onNodeWithText("Alice Wonderland")
            .assertExists() // Verify that the name exists

        // Check that the name is displayed
        composeTestRule.onNodeWithText("Alice Wonderland")
            .assertIsDisplayed()  // Vérifie que le nom est affiché

        // Verify the email address is also displayed
        composeTestRule.onNodeWithText("alice@example.com")
            .assertExists()  // Vérifie que l'email existe
            .assertIsDisplayed()  // Vérifie que l'email est affiché

        scenario.close()
    }

}