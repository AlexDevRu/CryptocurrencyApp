package com.example.data.repositories.local

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.repositories.local.IPreferncesStorage

class PreferencesStorage(context: Context): IPreferncesStorage {

    companion object {
        const val STORAGE_NAME = "Settings"

        private const val SIGN_IN_STATUS = "SIGN_IN_STATUS"
        private const val SIGN_IN_STATUS_DEFAULT = false

        const val LANGUAGE = "LANGUAGE"
        const val LANGUAGE_DEFAULT = "ru"

        const val THEME = "THEME"
        const val THEME_DEFAULT = "light"
    }

    private val settings: SharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = settings.edit()

    override fun saveSignInStatus(value: Boolean) {
        editor.putBoolean(SIGN_IN_STATUS, value)
        editor.commit()
    }
    override fun getSignInStatus() = settings.getBoolean(SIGN_IN_STATUS, SIGN_IN_STATUS_DEFAULT)

    override fun saveLanguage(value: String) {
        editor.putString(LANGUAGE, value)
        editor.commit()
    }
    override fun getLanguage() = settings.getString(LANGUAGE, LANGUAGE_DEFAULT).toString()

    override fun saveTheme(value: String) {
        editor.putString(THEME, value)
        editor.commit()
    }
    override fun getTheme() = settings.getString(THEME, THEME_DEFAULT).toString()
}