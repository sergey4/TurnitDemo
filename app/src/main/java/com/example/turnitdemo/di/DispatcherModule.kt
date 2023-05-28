package com.example.turnitdemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}

private class DispatcherProviderImpl(
    override val default: CoroutineDispatcher = Dispatchers.Default,
    override val main: CoroutineDispatcher = Dispatchers.Main,
    override val io: CoroutineDispatcher = Dispatchers.IO
) : DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    fun provideDatabaseInstance(): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}