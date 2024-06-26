package com.passwordmanager.domain.usecases

import com.passwordmanager.domain.repository.PasswordRepository

class GetPassword(
    private val repository: PasswordRepository
) {
    suspend fun execute(id: Int) = repository.getPassword(id)
}