package com.kirabium.relayance.viewmodelUnitTest

import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.viewmodel.DetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Date

/**
 * Test unitaire pour la classe DetailViewModel.
 */
@OptIn(ExperimentalCoroutinesApi::class) // Pour l'utilisation de `runTest`
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var customerRepository: CustomerRepository

    @Before
    fun setup() {
        // Simuler le repository avec MockK
        customerRepository = mockk()
        viewModel = DetailViewModel(customerRepository)

        // Définir un dispatcher de test pour Dispatchers.Main
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        // Réinitialiser le dispatcher principal après chaque test
        Dispatchers.resetMain()
    }

    /**
     * Test pour vérifier que le client est correctement récupéré.
     */
    @Test
    fun getCustomerById_should_return_customer_when_successful() = runTest {
        // Simuler une réponse réussie du repository
        val customer = Customer(id = 1, name = "John Doe", email = "john.doe@example.com", createdAt = Date())
        coEvery { customerRepository.getCustomerById(1) } returns flowOf(customer)

        // Appeler la méthode pour récupérer le client
        viewModel.getCustomerById(1)

        // Avancer jusqu'à ce que la coroutine soit terminée
        advanceUntilIdle()

        // Vérifier que le client est correctement récupéré
        val result = viewModel.customer.value
        assertNotNull(result)
        assertEquals(customer.id, result?.id)
        assertEquals(customer.name, result?.name)
        assertEquals(customer.email, result?.email)
    }

    /**
     * Test pour vérifier que l'erreur est correctement gérée.
     */
    @Test
    fun getCustomerById_should_return_error_when_exception_is_thrown() = runTest {
        // Simuler une exception lors de la récupération du client
        val exception = Exception("Client not found")
        coEvery { customerRepository.getCustomerById(1) } throws exception

        // Appeler la méthode pour récupérer le client
        viewModel.getCustomerById(1)

        // Avancer jusqu'à ce que la coroutine soit terminée
        advanceUntilIdle()

        // Vérifier que l'erreur est bien mise à jour
        val error = viewModel.error.value
        assertNotNull(error)
        assertEquals("Client not found", error)
    }
}