package com.passwordmanager.domain.usecases

import com.passwordmanager.domain.model.Password
import com.passwordmanager.domain.repository.PasswordRepository

class InsertPassword(
    private val repository: PasswordRepository
) {

    suspend fun execute(password: Password) =
        repository.insertPassword(password)

}