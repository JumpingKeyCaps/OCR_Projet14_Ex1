package com.kirabium.relayance.stepdefs

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class StepDefinitions {


    // Etape : Given I am on the Add Customer screen
    @Given("I am on the Add Customer screen")
    fun givenIAmOnAddCustomerScreen() {
        // Lancer l'activité AddCustomerActivity
        ActivityScenario.launch(AddCustomerActivity::class.java)
    }

    // Etape : When I enter "John Doe" as the name and "john.doe@example.com" as the email
    @When("I enter {string} as the name and {string} as the email")
    fun whenIEnterCustomerDetails(name: String, email: String) {
        // Saisir le nom et l'email dans les champs respectifs
        onView(withId(R.id.nameEditText)).perform(typeText(name))
        onView(withId(R.id.emailEditText)).perform(typeText(email))
    }

    // Etape : And I press the "Save" button
    @When("I press the save button")
    fun whenIPressSaveButton() {
        // Fermer le clavier avant d'appuyer sur le bouton (important dans Compose ou RecyclerView)
        closeSoftKeyboard()
        // Cliquer sur le bouton "Save"
        onView(withId(R.id.saveFab)).perform(click())
    }

    // Etape : Then I should see a confirmation message "Customer added successfully"
    @Then("I should see a confirmation message {string}")
    fun thenIShouldSeeConfirmationMessage(expectedMessage: String) {
        // Vérifier que le message de confirmation est affiché
        onView(withText(expectedMessage)).check(matches(isDisplayed()))
    }

    @Then("I should see an error message {string}")
    fun thenIShouldSeeErrorMessage(expectedMessage: String) {
        // Vérifier que le message d'erreur attendu est affiché dans un Snackbar
        onView(withText(expectedMessage))
            .check(matches(isDisplayed()))
    }
}