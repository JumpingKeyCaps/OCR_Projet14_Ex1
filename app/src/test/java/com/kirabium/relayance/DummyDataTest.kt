package com.kirabium.relayance

import com.kirabium.relayance.data.DummyData
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Calendar


/**
 * Test unitaire pour la classe DummyData
 */
class DummyDataTest {

    private lateinit var calendar: Calendar

    @Before
    fun setup() {
        // Initialiser le calendrier pour les tests
        calendar = Calendar.getInstance()
    }

    @After
    fun teardown() {
        // Libérer les ressources si nécessaire (non utilisé ici)
    }


    /**
     * Test pour verifier que la date générée est correcte en fonction du nombre de mois passés
     */
    @Test
    fun generateDate_returnsCorrectDateBasedOnMonthsBack() {
        // Arrange
        val monthsBack = 6
        val expectedCalendar = calendar.apply {
            add(Calendar.MONTH, -monthsBack)
        }

        // Act
        val resultDate = DummyData.generateDate(monthsBack)
        val resultCalendar = Calendar.getInstance().apply {
            time = resultDate
        }

        // Assert
        assertEquals(expectedCalendar.get(Calendar.YEAR), resultCalendar.get(Calendar.YEAR))
        assertEquals(expectedCalendar.get(Calendar.MONTH), resultCalendar.get(Calendar.MONTH))
    }


    /**
     * Test pour verifier que la date générée est correcte si le nombre de mois passés est zero
     */
    @Test
    fun generateDate_withZeroMonthsBack_returnsCurrentDate() {
        // Arrange
        val expectedCalendar = Calendar.getInstance()
        val expectedYear = expectedCalendar.get(Calendar.YEAR)
        val expectedMonth = expectedCalendar.get(Calendar.MONTH)
        val expectedDay = expectedCalendar.get(Calendar.DAY_OF_MONTH)

        // Act
        val resultDate = DummyData.generateDate(0)
        val resultCalendar = Calendar.getInstance().apply {
            time = resultDate
        }

        // Assert
        assertEquals(expectedYear, resultCalendar.get(Calendar.YEAR))
        assertEquals(expectedMonth, resultCalendar.get(Calendar.MONTH))
        assertEquals(expectedDay, resultCalendar.get(Calendar.DAY_OF_MONTH))
    }

    /**
     * Test pour verifier que la date générée dans le futur est correcte si le nombre de mois passés est négatif
     */
    @Test
    fun generateDate_withNegativeMonthsBack_returnsFutureDate() {
        // Arrange
        val monthsBack = -3
        val expectedCalendar = calendar.apply {
            add(Calendar.MONTH, -monthsBack) // Ajoute 3 mois dans le futur
        }

        // Act
        val resultDate = DummyData.generateDate(monthsBack)
        val resultCalendar = Calendar.getInstance().apply {
            time = resultDate
        }

        // Assert
        assertEquals(expectedCalendar.get(Calendar.YEAR), resultCalendar.get(Calendar.YEAR))
        assertEquals(expectedCalendar.get(Calendar.MONTH), resultCalendar.get(Calendar.MONTH))
    }



}