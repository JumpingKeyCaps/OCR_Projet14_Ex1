package com.kirabium.relayance.viewmodelUnitTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.viewmodel.AddCustomerViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
 * Test unitaire pour la classe AddCustomerViewModel.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AddCustomerViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddCustomerViewModel
    private val customerRepository: CustomerRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = AddCustomerViewModel(customerRepository)
    }

    /**
     * Test pour vérifier que le résultat de l'ajout d'un client est correctement mis à jour.
     */
    @Test
    fun addCustomer_should_return_success_when_customer_is_added() = runTest {
        // Simuler l'ajout d'un client avec succès
        val customer = Customer(id = 0, name = "John Doe", email = "john.doe@example.com", createdAt = Date())
        coEvery { customerRepository.addCustomer(any()) } returns Result.success(customer)

        // Lancer l'ajout du client
        viewModel.addCustomer("John Doe", "john.doe@example.com")

        // Avancer jusqu'à ce que la coroutine soit terminée
        advanceUntilIdle()

        // Vérifier que le résultat est un succès
        assertTrue(viewModel.addCustomerResult.value?.isSuccess == true)
        assertEquals("John Doe", viewModel.addCustomerResult.value?.getOrNull()?.name)
        assertEquals("john.doe@example.com", viewModel.addCustomerResult.value?.getOrNull()?.email)

        // Vérifier que la méthode addCustomer a bien été appelée sur le repository
        coVerify { customerRepository.addCustomer(any()) }
    }

    /**
     * Test si un email valid est bien détecté.
     */
    @Test
    fun isValidEmail_should_return_true_for_valid_email() {
        val validEmail = "john.doe@example.com"
        val result = viewModel.isValidEmail(validEmail)
        assertTrue(result)
    }

    /**
     * Test si un email non valid est bien détecté.
     */
    @Test
    fun isValidEmail_should_return_false_for_invalid_email() {
        val invalidEmail = "john doe.com"
        val result = viewModel.isValidEmail(invalidEmail)
        assertFalse(result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}