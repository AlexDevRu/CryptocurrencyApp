package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.ObservableBoolean
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

    val ruSelected = ObservableBoolean()
    val enSelected = ObservableBoolean()

    private val themes = listOf("light", "dark")
    val selectedThemePosition = ObservableInt(0)

    init {
        setCurrentLanguage()

        ruSelected.addOnPropertyChangedCallback(PropertyChangedCallback {
            if(ruSelected.get()) setLanguage.postValue("ru")
        })
        enSelected.addOnPropertyChangedCallback(PropertyChangedCallback {
            if(enSelected.get()) setLanguage.postValue("en")
        })

        selectedThemePosition.addOnPropertyChangedCallback(PropertyChangedCallback {
            val theme = themes[selectedThemePosition.get()]
            Log.w("asd", "theme $theme")
            setTheme.postValue(theme)
        })
    }

    private fun setCurrentLanguage() {
        if(getLanguageUseCase.invoke() == "en")
            enSelected.set(true)
        else
            ruSelected.set(true)
    }

    fun setCurrentTheme() {
        if(getThemeUseCase.invoke() == "dark")
            selectedThemePosition.set(1)
    }

    fun saveLanguage(lang: String) {
        saveLanguageUseCase.invoke(lang)
    }

    fun saveTheme(theme: String) {
        saveThemeUseCase.invoke(theme)
    }
}