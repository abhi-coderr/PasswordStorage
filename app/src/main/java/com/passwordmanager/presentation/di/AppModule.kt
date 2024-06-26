package com.passwordmanager.presentation.di

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import com.passwordmanager.core.utils.AppPreference
import com.passwordmanager.data.data_source.PasswordDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Description: This module class is used for the purpose of setting application wide hilt dependencies.
 *
 * @since 26/10/21
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val preferenceDataStoreName = "pm_preference"

    /**
     * For Preference Data Store
     */
    private val Context.prefDataStore by preferencesDataStore(
        name = preferenceDataStoreName
    )

    @Provides
    @Reusable
    fun providePreferenceDataStore(@ApplicationContext context: Context) = context.prefDataStore


    @Provides
    @Reusable
    internal fun providesAppPreference(@ApplicationContext context: Context) =
        AppPreference(context.prefDataStore)

    @Provides
    @Singleton
    fun providePasswordDatabase(app: Application): PasswordDatabase {
        return Room.databaseBuilder(
            app,
            PasswordDatabase::class.java,
            PasswordDatabase.DATABASE_NAME
        ).build()
    }
}