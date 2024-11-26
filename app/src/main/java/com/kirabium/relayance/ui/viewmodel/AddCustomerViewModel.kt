package com.kirabium.relayance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

/**
 * ViewModel pour l'ajout d'un nouveau client.
 */
@HiltViewModel
class AddCustomerViewModel @Inject constructor(private val customerRepository: CustomerRepository) : ViewModel() {

    private val _addCustomerResult = MutableStateFlow<Result<Customer>?>(null)
    val addCustomerResult: StateFlow<Result<Customer>?> get() = _addCustomerResult

    /**
     * Ajoute un nouveau client.
     * @param name Le nom du client.
     * @param email L'adresse email du client.
     */
    fun addCustomer(name: String, email: String) {
        viewModelScope.launch {
            try {
                // Exemple d'ajout de client avec un repository
                val customer = Customer(id = 0, name = name, email = email, createdAt = Date())
                customerRepository.addCustomer(customer)
                _addCustomerResult.value = Result.success(customer)
            } catch (e: Exception) {
                _addCustomerResult.value = Result.failure(e)
            }
        }
    }
}