package com.kirabium.relayance.repositoryUnitTest

import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.service.CustomerService
import com.kirabium.relayance.domain.model.Customer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.util.Date

/**
 * Test unitaire pour la classe CustomerRepository
 */
class CustomerRepositoryTest {


    private lateinit var repository: CustomerRepository
    private val customerService: CustomerService = mockk()

    @Before
    fun setUp() {
        repository = CustomerRepository(customerService)
    }


    /**
     * Test pour verifier qu'une liste de clients est retournée par la méthode
     */
    @Test
    fun getCustomers_should_return_list_of_customers() = runTest {
        // Mocked data
        val mockCustomers = listOf(
            Customer(1, "John Doe", "john.doe@example.com", Date()),
            Customer(2, "Jane Doe", "jane.doe@example.com", Date())
        )

        // Mock behavior
        every { customerService.getAllCustomers() } returns flowOf(mockCustomers)

        // Collect the flow
        val result = repository.getCustomers().toList()

        // Assertions
        assertEquals(1, result.size)
        assertEquals(mockCustomers, result[0])

        // Verify interaction
        verify { customerService.getAllCustomers() }
    }

    /**
     * Test pour verifier qu'un resultat de succès est retourné lorsque le client est ajouté
     */
    @Test
    fun addCustomer_should_return_success_result_when_customer_is_added() = runTest {
        // Mocked data
        val newCustomer = Customer(3, "Alice Smith", "alice.smith@example.com", Date())
        val resultSuccess: Result<Customer> = Result.success(newCustomer)

        // Mock behavior
        coEvery { customerService.addCustomer(newCustomer) } returns resultSuccess

        // Call repository method
        val result = repository.addCustomer(newCustomer)

        // Assertions
        assertTrue(result.isSuccess)
        assertEquals(newCustomer, result.getOrNull())

        // Verify interaction
        coVerify { customerService.addCustomer(newCustomer) }
    }


    /**
     * Test pour verifier qu'un resultat d'echec est retourné lorsque une erreur se produit lors de l'ajout du client
     */
    @Test
    fun addCustomer_should_return_failure_result_when_an_error_occurs() = runTest {
        // Mocked data
        val newCustomer = Customer(4, "Bob White", "bob.white@example.com", Date())
        val exception = Exception("Database error")
        val resultFailure: Result<Customer> = Result.failure(exception)

        // Mock behavior
        coEvery { customerService.addCustomer(newCustomer) } returns resultFailure

        // Call repository method
        val result = repository.addCustomer(newCustomer)

        // Assertions
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())

        // Verify interaction
        coVerify { customerService.addCustomer(newCustomer) }
    }


    /**
     * Test pour verifier q'une exception est lancée lorsque la méthode addCustomer du service échoue
     */
    @Test
    fun addCustomer_should_return_failure_when_customerService_throws_exception() = runTest {
        // Arrange
        val customer = Customer(1, "Jane Doe", "jane.doe@example.com", Date())
        val exception = Exception("Network error")
        coEvery { customerService.addCustomer(customer) } throws exception

        // Act
        val result = repository.addCustomer(customer)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { customerService.addCustomer(customer) }
    }


    /**
     * Test pour verifier qu'un client est retourné par la méthode getCustomerById
     */
    @Test
    fun getCustomerById_should_return_the_correct_customer() = runTest {
        // Mocked data
        val customerId = 1
        val mockCustomer = Customer(customerId, "John Doe", "john.doe@example.com", Date())

        // Mock behavior
        every { customerService.getCustomerById(customerId) } returns flowOf(mockCustomer)

        // Collect the flow
        val result = repository.getCustomerById(customerId).toList()

        // Assertions
        assertEquals(1, result.size)
        assertEquals(mockCustomer, result[0])

        // Verify interaction
        verify { customerService.getCustomerById(customerId) }
    }


    /**
     * Test pour verifier qu'un resultat d'echec est retourné lorsque le service echoue
     */
    @Test
    fun addCustomer_should_return_failure_result_when_customerService_returns_failure() = runTest {
        // Mocked data
        val newCustomer = Customer(4, "Bob White", "bob.white@example.com", Date())
        val exception = Exception("Database error")
        val resultFailure: Result<Customer> = Result.failure(exception)

        // Mock behavior
        coEvery { customerService.addCustomer(newCustomer) } returns resultFailure

        // Call repository method
        val result = repository.addCustomer(newCustomer)

        // Assertions
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())

        // Verify interaction
        coVerify { customerService.addCustomer(newCustomer) }
    }



}