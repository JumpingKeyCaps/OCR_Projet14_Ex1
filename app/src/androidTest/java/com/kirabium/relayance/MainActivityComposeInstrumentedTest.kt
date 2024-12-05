package com.kirabium.relayance

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.espresso.intent.Intents
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.ui.activity.DetailActivity
import com.kirabium.relayance.ui.activity.MainActivity
import com.kirabium.relayance.util.RecyclerViewItemCountAssertion
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test d'intégration de l'activité principale avec ComposeTestRule
 */
@RunWith(AndroidJUnit4::class)
class MainActivityComposeInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

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
     * Test si la RecyclerView est bien affichée avec le bon nombre d'items.
     */
    @Test
    fun testClientListHasFiveItems() {
        // L'activité est automatiquement lancée par composeTestRule
        onView(withId(R.id.customerRecyclerView))
            .check(RecyclerViewItemCountAssertion.withItemCount(5))
    }

    /**
     * Test si DetailActivity est bien lancé au clic sur un des items client de la RecyclerView.
     */
    @Test
    fun testClientItemLaunchesDetailActivity() {
        // Lancer l'activité via la règle
        onView(withId(R.id.customerRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Vérifier que l'intention correcte est envoyée
        Intents.intended(hasComponent(DetailActivity::class.java.name)) // Vérifie que l'intention contient le composant correct
        Intents.intended(hasExtra(DetailActivity.EXTRA_CUSTOMER_ID, 1)) // Vérifie que l'intention contient l'extra correct
    }

    /**
     * Test d'intégration pour le lancement de la DetailActivity avec un client spécifique.
     */
    @Test
    fun testLaunchDetailActivityWithAliceWonderland() {
        // Créer un Intent pour lancer DetailActivity avec l'ID d'Alice
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_CUSTOMER_ID, 1) // ID d'Alice Wonderland
        }

        // Lancer l'activité en utilisant composeTestRule, qui initialise Compose pour l'activité
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.startActivity(intent)  // Démarrer l'activité avec l'intent
        }

        // Attendez que Compose soit prêt et que l'UI se soit rendue
        composeTestRule.waitForIdle()  // S'assurer que Compose est prêt

        // Utilisez ComposeTestRule pour interagir avec l'UI de DetailActivity
        composeTestRule.onNodeWithText("Alice Wonderland")
            .assertExists() // Vérifie que le nom existe

        // Vérifiez que le nom est bien affiché
        composeTestRule.onNodeWithText("Alice Wonderland")
            .assertIsDisplayed()

        // Vérifiez que l'email est également affiché
        composeTestRule.onNodeWithText("alice@example.com")
            .assertExists()
            .assertIsDisplayed()
    }
}