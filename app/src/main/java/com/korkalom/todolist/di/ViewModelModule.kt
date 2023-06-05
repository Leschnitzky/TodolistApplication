package com.korkalom.todolist.di

import com.korkalom.todolist.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    fun providesDispatcher(): DispatcherProvider {
        return DispatcherProvider(
            dispatcherIO = Dispatchers.IO,
            dispatcherDefault = Dispatchers.Default,
            dispatcherMain = Dispatchers.Main
        )
    }
}