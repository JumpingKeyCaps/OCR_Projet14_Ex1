package com.kirabium.relayance.data.repository

import com.kirabium.relayance.data.service.CustomerService
import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository pour les clients.
 */
class CustomerRepository @Inject constructor(private val customerService: CustomerService) {

    /**
     * Récupère la liste de tous les clients.
     * @return un flux contenant la liste de tous les clients.
     */
    fun getCustomers(): Flow<List<Customer>> = customerService.getAllCustomers()

    /**
     * Ajoute un nouveau client.
     * @param customer Le client à ajouter.
     * @return un Resulat contenant le client ajouté ou une exception.
     */
    suspend fun addCustomer(customer: Customer): Result<Customer> {
        return customerService.addCustomer(customer)
    }

    /**
     * Récupère un client par son ID.
     * @param customerId L'ID du client à récupérer.
     * @return un flux contenant le client.
     */
    fun getCustomerById(customerId: Int): Flow<Customer> = customerService.getCustomerById(customerId)



}