package com.example.domain.use_cases.preferences

import com.example.domain.repositories.local.IPreferncesStorage

class GetSignInStatusUseCase(private val repository: IPreferncesStorage) {
    operator fun invoke() = repository.getSignInStatus()
}