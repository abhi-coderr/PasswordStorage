package com.passwordmanager.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_table")
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val account: String,
    val password: String,
    val email: String = String()
)
