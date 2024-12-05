package com.kirabium.relayance.viewmodelUnitTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.service.CustomerService
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

/**
 * Test unitaire pour la classe MainViewModel.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private val customerRepository: CustomerRepository = mockk()
    private val customerService: CustomerService = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        // Initialise le mock pour le repository avec un service mocké
        viewModel = MainViewModel(customerRepository)

        // Configure le comportement du service mocké
        coEvery { customerService.getAllCustomers() } returns flowOf(
            listOf(
                Customer(id = 1, name = "John Doe", email = "john.doe@example.com", createdAt = Date()),
                Customer(id = 2, name = "Jane Smith", email = "jane.smith@example.com", createdAt = Date())
            )
        )
        // Initialise le repository avec le service mocké
        coEvery { customerRepository.getCustomers() } returns customerService.getAllCustomers()
    }



    /**
     * Test pour vérifier que la liste des clients est correctement mise à jour lorsque les données sont récupérées.
     */
    @Test
    fun getCustomers_should_update_customers_list_when_data_is_fetched() = runTest {


        // Lancer la collecte dans une coroutine
        var customersCollected: List<Customer>? = null
        val job = launch {
            viewModel.customers.collect { customers ->
                customersCollected = customers
            }
        }

        // Avancer le dispatcher pour s'assurer que le flow est collecté
        advanceUntilIdle()

        // Vérification que la liste des clients a bien été mise à jour
        assertNotNull(customersCollected)
        assertEquals(2, customersCollected?.size)
        assertEquals("John Doe", customersCollected?.get(0)?.name)
        assertEquals("Jane Smith", customersCollected?.get(1)?.name)

        // Vérification que la méthode getCustomers() a bien été appelée sur le repository
        verify { customerRepository.getCustomers() }

        // Annuler la collecte après le test
        job.cancel()
    }




    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}