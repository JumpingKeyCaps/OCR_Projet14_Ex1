package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Calendar

/**
 * Test unitaire pour la classe Customer
 */
class CustomerTest {

    private lateinit var calendar: Calendar

    @Before
    fun setup() {
        calendar = Calendar.getInstance()
    }

    @After
    fun teardown() {
        // Libérer les ressources si nécessaire
    }

    /**
     * Test pour verifier que le client est nouveau
     * - Vérifie qu'un client créé il y a moins de 3 mois est bien considéré comme "nouveau".
     */
    @Test
    fun isNewCustomer_returnsTrueForNewCustomer() {
        // Arrange
        calendar.add(Calendar.MONTH, -2)  // Date il y a 2 mois
        val newCustomer = Customer(
            id = 1,
            name = "John Doe",
            email = "john@example.com",
            createdAt = calendar.time
        )

        // Act
        val result = newCustomer.isNewCustomer()

        // Assert
        assertTrue(result)
    }

    /**
     * Test pour verifier qu'un ancien client n'est pas considéré comme nouveau
     * - Vérifie qu'un client créé il y a plus de 3 mois n'est pas considéré comme "nouveau".
     */
    @Test
    fun isNewCustomer_returnsFalseForOldCustomer() {
        // Arrange
        calendar.add(Calendar.MONTH, -6)  // Date il y a 6 mois
        val oldCustomer = Customer(
            id = 2,
            name = "Jane Doe",
            email = "jane@example.com",
            createdAt = calendar.time
        )

        // Act
        val result = oldCustomer.isNewCustomer()

        // Assert
        assertFalse(result)
    }


    /**
     * Test pour verifier qu'un client d'exactment 3 mois est considéré comme nouveau
     * - Vérifie qu'un client créé exactement il y a 3 mois n'est pas considéré comme "nouveau".
     */
    @Test
    fun isNewCustomer_returnsFalseForCustomerExactlyThreeMonthsOld() {
        // Arrange
        val currentDate = Calendar.getInstance()
        currentDate.set(Calendar.HOUR_OF_DAY, 0)  // Réinitialiser à minuit
        currentDate.set(Calendar.MINUTE, 0)
        currentDate.set(Calendar.SECOND, 0)
        currentDate.set(Calendar.MILLISECOND, 0)

        // Créer une date exactement il y a 3 mois
        val exactlyThreeMonthsAgo = Calendar.getInstance().apply {
            add(Calendar.MONTH, -3)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val exactlyOldCustomer = Customer(
            id = 3,
            name = "Sam Smith",
            email = "sam@example.com",
            createdAt = exactlyThreeMonthsAgo
        )

        // Act
        val result = exactlyOldCustomer.isNewCustomer()

        // Assert
        assertFalse(result)
    }

    /**
     * Test pour verifier qu'un client créé aujourd'hui est considéré comme nouveau
     */
    @Test
    fun isNewCustomer_returnsTrueForCustomerCreatedToday() {
        // Arrange
        calendar.set(Calendar.HOUR_OF_DAY, 0)  // Réinitialise l'heure à minuit pour aujourd'hui
        val todayCustomer = Customer(
            id = 4,
            name = "Lisa Johnson",
            email = "lisa@example.com",
            createdAt = calendar.time
        )

        // Act
        val result = todayCustomer.isNewCustomer()

        // Assert
        assertTrue(result)
    }
}