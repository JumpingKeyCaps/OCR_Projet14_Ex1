package com.kirabium.relayance.di

import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.service.CustomerService
import com.kirabium.relayance.data.service.CustomerServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Fichier contenant les dépendances de l'application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Fournit une instance du service pour les clients.
     */
    @Provides
    @Singleton
    fun provideCustomerService(): CustomerService {
        return CustomerServiceImpl()
    }

    /**
     * Fournit une instance du repository pour les clients.
     */
    @Provides
    @Singleton
    fun provideCustomerRepository(customerService: CustomerService): CustomerRepository {
        return CustomerRepository(customerService)
    }



}