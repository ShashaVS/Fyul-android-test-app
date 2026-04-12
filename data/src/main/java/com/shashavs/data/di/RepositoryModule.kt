package com.shashavs.data.di

import com.shashavs.data.repository.ProductRepositoryImpl
import com.shashavs.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindProductsRepository(impl: ProductRepositoryImpl): ProductRepository
}