package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.ObservableInt
import com.example.domain.use_cases.preferences.*
import com.example.kulakov_p4_cryptocurrency_app.events.SingleLiveEvent
import com.example.kulakov_p4_cryptocurrency_app.utils.PropertyChangedCallback
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    private val saveLanguageUseCase: SaveLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase
): BaseVM() {
    val setLanguage = SingleLiveEvent<String>()
    val setTheme = SingleLiveEvent<String>()

    var initLanguage = false

    val locales = listOf("ru", "en")
    val selectedLangPosition = ObservableInt(0)

    private val themes = listOf("light", "dark")
    val selectedThemePosition = ObservableInt(0)

    init {
        selectedLangPosition.addOnPropertyChangedCallback(PropertyChangedCallback {
            val lang = locales[selectedLangPosition.get()]
            Log.w("asd", "lang $lang")
            setLanguage.postValue(lang)
        })

        selectedThemePosition.addOnPropertyChangedCallback(PropertyChangedCallback {
            val theme = themes[selectedThemePosition.get()]
            Log.w("asd", "theme $theme")
            setTheme.postValue(theme)
        })
    }

    fun setCurrentLanguage() {
        val savedLang = getLanguageUseCase.invoke()
        Log.w("asd", "init $savedLang")
        selectedLangPosition.set(if(savedLang == "en") 1 else 0)
    }

    fun saveLanguage(lang: String) {
        saveLanguageUseCase.invoke(lang)
    }

    fun saveTheme(theme: String) {
        saveThemeUseCase.invoke(theme)
    }

    fun getLanguage() = getLanguageUseCase.invoke()

    fun getTheme() = getThemeUseCase.invoke()
}