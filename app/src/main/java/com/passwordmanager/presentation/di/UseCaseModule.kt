package com.passwordmanager.presentation.di


import com.passwordmanager.domain.repository.PasswordRepository
import com.passwordmanager.domain.usecases.DeletePassword
import com.passwordmanager.domain.usecases.EditPassword
import com.passwordmanager.domain.usecases.GetPassword
import com.passwordmanager.domain.usecases.GetPasswordList
import com.passwordmanager.domain.usecases.InsertPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideInsertPassword(repository: PasswordRepository): InsertPassword {
        return InsertPassword(repository)
    }

    @Provides
    fun provideGetPassword(repository: PasswordRepository): GetPassword {
        return GetPassword(repository)
    }

    @Provides
    fun provideGetPasswordList(repository: PasswordRepository): GetPasswordList {
        return GetPasswordList(repository)
    }

    @Provides
    fun provideEditPassword(repository: PasswordRepository): EditPassword {
        return EditPassword(repository)
    }

    @Provides
    fun provideDeletePassword(repository: PasswordRepository): DeletePassword {
        return DeletePassword(repository)
    }
}