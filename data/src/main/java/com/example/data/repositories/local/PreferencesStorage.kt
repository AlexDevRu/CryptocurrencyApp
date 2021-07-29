package com.example.data.repositories.local

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.repositories.local.IPreferncesStorage

class PreferencesStorage(context: Context): IPreferncesStorage {

    companion object {
        private const val STORAGE_NAME = "Settings"

        private const val SIGN_IN_STATUS = "SIGN_IN_STATUS"
        private const val SIGN_IN_STATUS_DEFAULT = false
    }

    private val settings: SharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = settings.edit()

    override fun saveSignInStatus(value: Boolean) {
        editor.putBoolean(SIGN_IN_STATUS, value)
        editor.commit()
    }

    override fun getSignInStatus() = settings.getBoolean(SIGN_IN_STATUS, SIGN_IN_STATUS_DEFAULT)
}