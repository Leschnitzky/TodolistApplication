package com.korkalom.baseapplication

import com.korkalom.baseapplication.di.ViewModelModule
import com.korkalom.baseapplication.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers


@TestInstallIn(components = [SingletonComponent::class],
    replaces = [ViewModelModule::class])
@Module
object TestViewModelModule {
    @Provides
    fun provideFakeInventoryRepository() : DispatcherProvider {
        return DispatcherProvider(
            dispatcherMain = Dispatchers.Main,
            dispatcherIO = Dispatchers.IO,
            dispatcherDefault = Dispatchers.Default
        )
    }
}
