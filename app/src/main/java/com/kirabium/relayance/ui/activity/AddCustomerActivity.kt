package com.kirabium.relayance.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kirabium.relayance.databinding.ActivityAddCustomerBinding
import com.kirabium.relayance.ui.viewmodel.AddCustomerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Activité pour ajouter un nouveau client.
 */
@AndroidEntryPoint
class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding
    private val viewModel: AddCustomerViewModel by viewModels()

    /**
     * Initialisation de l'activité.
     * @param savedInstanceState L'état sauvegardé de l'activité.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupToolbar()

        // Observer le résultat de l'ajout du client dans le ViewModel
        observeAddCustomerResult()
    }

    /**
     * Initialise la toolbar de l'activité.
     */
    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Initialise le content binding de l'activité. (UI)
     */
    private fun setupBinding() {
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Envoi des données lorsque l'utilisateur appuie sur un bouton pour ajouter un client
        binding.saveFab.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                if (!viewModel.isValidEmail(email)) {
                    Snackbar.make(binding.root, "Invalid email format", Snackbar.LENGTH_LONG).show()
                } else {
                    viewModel.addCustomer(name, email)
                }
            } else {
                Snackbar.make(binding.root, "Please fill in all fields", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Observe le résultat de l'ajout du client dans le ViewModel.
     */
    private fun observeAddCustomerResult() {
        lifecycleScope.launch {
            viewModel.addCustomerResult.collect { result ->
                when {
                    result == null -> {
                        // En attente ou initialisation
                    }
                    result.isSuccess -> {
                        // Le client a été ajouté avec succès
                        Snackbar.make(binding.root, "Customer added successfully", Snackbar.LENGTH_LONG)
                            .setAction("OK") {
                                finish() // Fermer l'activité après confirmation
                            }
                            .show()
                    }
                    result.isFailure -> {
                        // Une erreur est survenue
                        val error = result.exceptionOrNull()
                        val message = error?.message ?: "Failed to add customer"
                        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}