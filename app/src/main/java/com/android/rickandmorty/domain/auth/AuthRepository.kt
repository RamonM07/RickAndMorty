package com.android.rickandmorty.domain.auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
}
