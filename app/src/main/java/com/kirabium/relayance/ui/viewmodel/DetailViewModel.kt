package com.kirabium.relayance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour les détails du client.
 */
@HiltViewModel
class DetailViewModel @Inject constructor(private val customerRepository: CustomerRepository) : ViewModel() {

    private val _customer = MutableStateFlow<Customer?>(null)
    val customer: StateFlow<Customer?> get() = _customer

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Récupère le client par son ID.
     * @param customerId L'ID du client à récupérer.
     */
    fun getCustomerById(customerId: Int) {
        viewModelScope.launch {
            try {
                // Si le client existe, nous collectons les données
                customerRepository.getCustomerById(customerId).collect { customer ->
                    _customer.value = customer
                }
            } catch (e: Exception) {
                // Si une erreur survient, nous assignons un message d'erreur
                _error.value = e.message
            }
        }
    }


}