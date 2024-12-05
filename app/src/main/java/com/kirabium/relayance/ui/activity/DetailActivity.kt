package com.kirabium.relayance.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kirabium.relayance.ui.composable.DetailScreen
import com.kirabium.relayance.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        setContent {
            DetailScreen(
                customerId = customerId,
                onBackClick = { onBackPressedDispatcher.onBackPressed() }
            )
        }
    }
}


