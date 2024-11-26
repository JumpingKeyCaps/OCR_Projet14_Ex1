package com.kirabium.relayance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel pour la liste des clients de l'activité principale.
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val customerRepository: CustomerRepository) : ViewModel() {


    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers: StateFlow<List<Customer>> = _customers.asStateFlow()

    init {
       getCustomers()
    }

    /**
     * Récupère la liste de tous les clients.
     */
    private fun getCustomers() {
        viewModelScope.launch {
            customerRepository.getCustomers().collect { customerList ->
                _customers.value = customerList
            }
        }
    }


}