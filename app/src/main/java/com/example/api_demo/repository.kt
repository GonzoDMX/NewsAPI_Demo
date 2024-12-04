package com.example.api_demo

class NewsRepository {
    private val apiService = RetrofitClient.newsApiService

    suspend fun getTopHeadlines(apiKey: String): Result<List<Article>> {
        return try {
            val response = apiService.getTopHeadlines(apiKey = apiKey)
            Result.success(response.articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}