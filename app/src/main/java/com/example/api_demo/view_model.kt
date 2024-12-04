package com.example.api_demo

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
                    _newsState.value = NewsState.Success(articles)
                },
                onFailure = { error ->
                    _newsState.value = NewsState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
}

sealed class NewsState {
    object Loading : NewsState()
    data class Success(val articles: List<Article>) : NewsState()
    data class Error(val message: String) : NewsState()
}