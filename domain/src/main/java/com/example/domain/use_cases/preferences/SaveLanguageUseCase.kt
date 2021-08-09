package com.example.domain.use_cases.preferences

import com.example.domain.repositories.local.IPreferncesStorage

class SaveLanguageUseCase(private val repository: IPreferncesStorage) {
    operator fun invoke(lang: String) = repository.saveLanguage(lang)
}