package com.passwordmanager.domain.usecases

import com.passwordmanager.domain.model.Password
import com.passwordmanager.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow

class GetPasswordList(
    private val repository: PasswordRepository
) {
    fun execute(): Flow<List<Password>> = repository.getPasswordList()
}