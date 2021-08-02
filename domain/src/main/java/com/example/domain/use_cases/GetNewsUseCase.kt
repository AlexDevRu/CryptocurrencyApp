package com.example.domain.use_cases

import com.example.domain.repositories.remote.INewsApiRepository

class GetNewsUseCase(private val repository: INewsApiRepository) {
    suspend operator fun invoke(query: String) = repository.getNews(query)
}
