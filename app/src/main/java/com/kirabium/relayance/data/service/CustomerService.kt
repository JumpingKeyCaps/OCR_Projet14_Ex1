package com.kirabium.relayance.data.service

import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow

/**
 * Interface du service pour les clients.
 */
interface CustomerService {
    /**
     * Récupère la liste de tous les clients.
     * @return un flux contenant la liste de tous les clients.
     */
    fun getAllCustomers(): Flow<List<Customer>>

    /**
     * Ajoute un nouveau client.
     * @param customer Le client à ajouter.
     * @return un Resulat contenant le client ajouté ou une exception.
     */
    suspend fun addCustomer(customer: Customer): Result<Customer>

    /**
     * Récupère un client par son ID.
     * @param customerId L'ID du client à récupérer.
     * @return un flux contenant le client.
     */
    fun getCustomerById(customerId: Int): Flow<Customer>
}