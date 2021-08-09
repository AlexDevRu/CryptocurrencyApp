package com.example.domain.use_cases.preferences

import com.example.domain.repositories.local.IPreferncesStorage

class SaveThemeUseCase(private val repository: IPreferncesStorage) {
    operator fun invoke(theme: String) = repository.saveTheme(theme)
}