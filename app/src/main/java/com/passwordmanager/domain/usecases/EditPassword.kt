package com.passwordmanager.domain.usecases

import com.passwordmanager.domain.model.Password
import com.passwordmanager.domain.repository.PasswordRepository

class EditPassword(
    private val repository: PasswordRepository
) {
    suspend fun execute(password: Password) = repository.updatePassword(password)
}