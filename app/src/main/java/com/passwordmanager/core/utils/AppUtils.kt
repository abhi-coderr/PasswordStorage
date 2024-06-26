package com.passwordmanager.core.utils

import com.passwordmanager.domain.model.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object AppUtils {
    fun encrypt(data: String): String {
        return EncryptionUtil.encrypt(data).orEmpty()
    }

    fun decrypt(data: String): String {
        return EncryptionUtil.decrypt(data).orEmpty()
    }

    fun Flow<List<Password>>.decryptList(): Flow<List<Password>> {

        return this.map { list ->
            list.map { password ->
                password.copy(
                    account = decrypt(password.account),
                    email = decrypt(password.email),
                    password = decrypt(password.password)
                )
            }

        }
    }

    fun Password.decryptPassword(): Password {

        return this.copy(
            account = decrypt(this.account),
            email = decrypt(this.email),
            password = decrypt(this.password)
        )

    }
}