package com.passwordmanager.data.repository

import com.passwordmanager.core.utils.AppUtils.decrypt
import com.passwordmanager.core.utils.AppUtils.decryptList
import com.passwordmanager.core.utils.AppUtils.encrypt
import com.passwordmanager.data.data_source.PasswordDao
import com.passwordmanager.domain.model.Password
import com.passwordmanager.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PasswordRepositoryImpl(
    private val dao: PasswordDao
) : PasswordRepository {
    override suspend fun insertPassword(password: Password) {
        return dao.insertPassword(
            Password(
                id = password.id,
                account = encrypt(password.account),
                email = encrypt(password.email),
                password = encrypt(password.password)
            )
        )
    }

    override suspend fun getPassword(id: Int): Password? {
        return dao.getPassword(id)
    }

    override fun getPasswordList(): Flow<List<Password>> {
        return dao.getPasswordList().decryptList()
    }

    override suspend fun updatePassword(password: Password) {
        return dao.updatePassword(
            id = password.id,
            account = encrypt(password.account),
            email = encrypt(password.email),
            password = encrypt(password.password)
        )
    }

    override suspend fun deletePassword(password: Password) {
        return dao.deletePassword(password)
    }
}