package com.passwordmanager.domain.repository

import com.passwordmanager.domain.model.Password
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    suspend fun insertPassword(password: Password)

    suspend fun getPassword(id: Int): Password?

    fun getPasswordList(): Flow<List<Password>>

   suspend fun updatePassword(password: Password)
   suspend fun deletePassword(password: Password)

}