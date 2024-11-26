package com.kirabium.relayance.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kirabium.relayance.ui.composable.DetailScreen
import com.kirabium.relayance.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Activité des détails du client.
 */
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CUSTOMER_ID = "customer_id"
    }

    private val viewModel: DetailViewModel by viewModels()

    /**
     * Méthode appelée lorsque l'activité est créée.
     * @param savedInstanceState L'état sauvegardé de l'activité.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    /**
     * Méthode pour configurer l'interface utilisateur.
     */
    private fun setupUI() {
        val customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1)

        if (customerId != -1) {// Vérifie que l'ID est valide
            // Charge les données via le ViewModel
            viewModel.getCustomerById(customerId)
            // Observer les données du client
            lifecycleScope.launch {
                viewModel.customer.collect { customer ->
                    customer?.let {
                        // Une fois les données reçues, afficher la vue
                        setContent {
                            DetailScreen(customer = it) {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        }
                    }
                }
            }
        }

        // Observer les erreurs (si présentes)
        lifecycleScope.launch {
            viewModel.error.collect { errorMessage ->
                errorMessage?.let {
                    // Afficher un Toast ou un dialogue d'erreur
                    Toast.makeText(this@DetailActivity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


