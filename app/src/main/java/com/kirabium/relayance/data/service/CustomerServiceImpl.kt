package com.kirabium.relayance.data.service

import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Implémentation du service pour les clients.
 */
class CustomerServiceImpl: CustomerService {

    private val customerList = MutableStateFlow(DummyData.customers.toMutableList())

    /**
     * Récupère la liste de tous les clients.
     * @return un flux contenant la liste de tous les clients.
     */
    override fun getAllCustomers(): Flow<List<Customer>> = customerList

    /**
     * Ajoute un nouveau client.
     * @param customer Le client à ajouter.
     * @return un Resulat contenant le client ajouté ou une exception.
     */
    override suspend fun addCustomer(customer: Customer): Result<Customer> {
        return try {
            // Ajouter le client à la liste avec un nouvel ID
            customer.id = customerList.value.size + 1
            customerList.value = (customerList.value + customer).toMutableList()

            // Retourne le client ajouté avec un succès
            Result.success(customer)
        } catch (e: Exception) {
            // En cas d'erreur, retourne un échec avec l'exception
            Result.failure(e)
        }
    }

    /**
     * Récupère un client par son ID.
     * @param customerId L'ID du client à récupérer.
     * @return un flux contenant le client.
     */
    override fun getCustomerById(customerId: Int): Flow<Customer> {
        return customerList.value.find { it.id == customerId }?.let {
            MutableStateFlow(it)
        } ?: throw Exception("Customer not found")
    }


}