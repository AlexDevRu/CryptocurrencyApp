package com.example.domain.repositories.local

interface IPreferncesStorage {
    fun saveSignInStatus(value: Boolean)
    fun getSignInStatus(): Boolean
}