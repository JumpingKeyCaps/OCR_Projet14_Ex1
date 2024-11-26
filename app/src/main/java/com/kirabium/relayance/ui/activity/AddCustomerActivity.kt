package com.kirabium.relayance.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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
                viewModel.addCustomer(name, email)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(this@AddCustomerActivity, "Customer added successfully", Toast.LENGTH_SHORT).show()
                        finish()  // Fermer l'activité et revenir à la précédente
                    }
                    result.isFailure -> {
                        // Une erreur est survenue
                        val error = result.exceptionOrNull()
                        Toast.makeText(this@AddCustomerActivity, "Failed to add customer: ${error?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}