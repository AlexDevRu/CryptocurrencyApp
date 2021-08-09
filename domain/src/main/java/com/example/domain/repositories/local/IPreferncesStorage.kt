package com.example.domain.repositories.local

interface IPreferncesStorage {
    fun saveSignInStatus(value: Boolean)
    fun getSignInStatus(): Boolean

    fun saveLanguage(value: String)
    fun getLanguage(): String

    fun saveTheme(value: String)
    fun getTheme(): String
}