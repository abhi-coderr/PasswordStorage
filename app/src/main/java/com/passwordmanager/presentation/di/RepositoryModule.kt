package com.passwordmanager.presentation.di

import com.passwordmanager.data.data_source.PasswordDao
import com.passwordmanager.data.data_source.PasswordDatabase
import com.passwordmanager.data.repository.PasswordRepositoryImpl
import com.passwordmanager.domain.repository.PasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providePasswordRepository(db: PasswordDatabase): PasswordRepository {
        return PasswordRepositoryImpl(db.passwordDao)
    }

}