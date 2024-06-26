package com.passwordmanager.presentation.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.passwordmanager.core.utils.AppUtils.decryptPassword
import com.passwordmanager.core.utils.EncryptionUtil
import com.passwordmanager.domain.model.Password
import com.passwordmanager.domain.usecases.DeletePassword
import com.passwordmanager.domain.usecases.EditPassword
import com.passwordmanager.domain.usecases.GetPassword
import com.passwordmanager.domain.usecases.GetPasswordList
import com.passwordmanager.domain.usecases.InsertPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPasswordList: GetPasswordList,
    private val insertPassword: InsertPassword,
    private val getPassword: GetPassword,
    private val editPassword: EditPassword,
    private val deletePassword: DeletePassword
) : ViewModel() {

    private var _collectData = MutableStateFlow<List<Password>>(emptyList())
    val collectData: StateFlow<List<Password>> = _collectData

    private var _userPassword = MutableStateFlow<Password?>(null)
    val userPassword: StateFlow<Password?> = _userPassword

    init {
        viewModelScope.launch {
            getPasswordList.execute().collect {
                _collectData.value = it
            }
        }
    }

    fun addPassword(password: Password) {
        viewModelScope.launch { insertPassword.execute(password) }
    }

    fun getPassword(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _userPassword.value = getPassword.execute(id)?.decryptPassword().also {
                println(it ?: "empty")
                onSuccess()
            }
        }
    }

    fun editPassword(password: Password) {
        viewModelScope.launch { editPassword.execute(password) }
    }

    fun deletePassword(password: Password) {
        viewModelScope.launch { deletePassword.execute(password) }
    }

    fun encryptPassword(password: Password) {

    }


    fun encrypt(data: String): String {
        return EncryptionUtil.encrypt(data).orEmpty()
    }

    fun decrypt(data: String): String {
        return EncryptionUtil.decrypt(data).orEmpty()
    }
}