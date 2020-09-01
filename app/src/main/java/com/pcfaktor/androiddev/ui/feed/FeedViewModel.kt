package com.pcfaktor.androiddev.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pcfaktor.androiddev.data.Repository
import com.pcfaktor.androiddev.domain.entity.Article
import kotlinx.coroutines.launch

class FeedViewModel(private val repository: Repository) : ViewModel() {
    private val articles = MutableLiveData<List<Article>>()
    val articlesLiveData: LiveData<List<Article>>
        get() = articles

    internal fun onStart() {
        viewModelScope.launch {
            articles.value = repository.getArticlesAsync()
        }
    }
}