package com.korkalom.todolist.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.korkalom.todolist.model.room.TASK_DB_NAME
import com.korkalom.todolist.model.room.TaskDatabase
import com.korkalom.todolist.repository.Repository
import com.korkalom.todolist.repository.RoomRepositoryImpl
import com.korkalom.todolist.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun providesRepository(@ApplicationContext context: Context) : Repository {
        return RoomRepositoryImpl(provideRoomDatabase(context))
    }


    @Provides
    fun provideRoomDatabase(@ApplicationContext context : Context) : TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            TASK_DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}