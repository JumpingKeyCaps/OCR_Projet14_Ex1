package com.kirabium.relayance

import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Test unitaire pour la classe DateExt
 */
class DateExtTest {

    private lateinit var calendar: Calendar
    private lateinit var dateFormat: SimpleDateFormat

    @Before
    fun setup() {
        calendar = Calendar.getInstance()
        dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }

    @After
    fun teardown() {
        // Libérer les ressources si nécessaire
    }

    /**
     * Test pour verifier que la date est formatée correctement
     * - Vérifie qu'une date donnée est correctement formatée en dd/MM/yyyy
     */
    @Test
    fun toHumanDate_formatsDateCorrectly() {
        // Arrange
        calendar.set(2024, Calendar.NOVEMBER, 26)
        val expected = "26/11/2024"
        val testDate = calendar.time

        // Act
        val result = testDate.toHumanDate()

        // Assert
        assertEquals(expected, result)
    }

    /**
     * Test pour verifier que la date est formatée correctement ( cas ou le mois est < 10)
     * - Vérifie que les jours et mois inférieurs à 10 sont précédés d'un zéro.
     */
    @Test
    fun toHumanDate_formatsDateWithLeadingZeros() {
        // Arrange
        calendar.set(2024, Calendar.FEBRUARY, 5) // 5 février 2024
        val expected = "05/02/2024"
        val testDate = calendar.time

        // Act
        val result = testDate.toHumanDate()

        // Assert
        assertEquals(expected, result)
    }

    /**
     * Test pour verifier que la date est formatée correctement ( cas ou l'année est bissextile)
     *  - Vérifie que les dates pour une années bissextiles sont correctement formatées.
     */
    @Test
    fun toHumanDate_formatsDateForLeapYear() {
        // Arrange
        calendar.set(2024, Calendar.FEBRUARY, 29) // 29 février 2024 (année bissextile)
        val expected = "29/02/2024"
        val testDate = calendar.time

        // Act
        val result = testDate.toHumanDate()

        // Assert
        assertEquals(expected, result)
    }

    /**
     * Test pour verifier que la date est formatée correctement ( cas ou l'année est dans le passe)
     * - Vérifie qu'une date dans le passé est correctement formatée.
     */
    @Test
    fun toHumanDate_formatsDateWithPastYear() {
        // Arrange
        calendar.set(1990, Calendar.MARCH, 15) // 15 mars 1990
        val expected = "15/03/1990"
        val testDate = calendar.time

        // Act
        val result = testDate.toHumanDate()

        // Assert
        assertEquals(expected, result)
    }
}