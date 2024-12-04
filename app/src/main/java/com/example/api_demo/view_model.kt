package com.example.api_demo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _newsState = MutableLiveData<NewsState>()
    val newsState: LiveData<NewsState> = _newsState

    fun fetchNews(apiKey: String) {
        viewModelScope.launch {
            _newsState.value = NewsState.Loading

            repository.getTopHeadlines(apiKey).fold(
                onSuccess = { articles ->
                    val filteredArticles = articles.filter { article ->
                        val invalidContent = "[Removed]"
                        val isValidTitle = !article.title.isNullOrEmpty() && article.title != invalidContent
                        val isValidDescription = !article.description.isNullOrEmpty() && article.description != invalidContent
                        isValidTitle && isValidDescription
                    }
                    // Add logging to check the filtered articles
                    filteredArticles.forEach { article ->
                        Log.d("NewsViewModel", "Filtered Article: ${article.title}")
                    }
                    _newsState.value = NewsState.Success(filteredArticles)
                },
                onFailure = { error ->
                    Log.e("NewsViewModel", "Error fetching news", error)
                    _newsState.value = NewsState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
}

sealed class NewsState {
    data object Loading : NewsState()
    data class Success(val articles: List<Article>) : NewsState()
    data class Error(val message: String) : NewsState()
}