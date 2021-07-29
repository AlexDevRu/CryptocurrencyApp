package com.example.domain.use_cases.preferences

import com.example.domain.repositories.local.IPreferncesStorage

class SaveSignInStatusUseCase(private val repository: IPreferncesStorage) {
    operator fun invoke(status: Boolean) = repository.saveSignInStatus(status)
}