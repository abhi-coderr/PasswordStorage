package com.passwordmanager.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.passwordmanager.domain.model.Password
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: Password)

    @Query("SELECT * FROM password_table")
    fun getPasswordList(): Flow<List<Password>>

    @Query("SELECT * FROM password_table WHERE id = :id")
    suspend fun getPassword(id: Int): Password?

    @Query("UPDATE password_table SET email = :email, account = :account, password = :password WHERE id = :id")
    suspend fun updatePassword(id: Int,account: String, email: String, password: String)

    @Delete
    suspend fun deletePassword(password: Password)
}