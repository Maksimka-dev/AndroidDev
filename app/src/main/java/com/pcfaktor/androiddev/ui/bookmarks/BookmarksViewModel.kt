package com.pcfaktor.androiddev.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pcfaktor.androiddev.data.Repository
import com.pcfaktor.androiddev.domain.entity.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarksViewModel(private val repository: Repository) : ViewModel() {

    private val bookmarks = MutableLiveData<List<Article>>()
    val bookmarksLiveData: LiveData<List<Article>>
        get() = bookmarks

    internal fun onResume() {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarks.postValue(repository.getAllBookmarksByDate())
        }
    }

    internal fun deleteBookmark(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromBookmarks(article)
            bookmarks.postValue(repository.getAllBookmarksByDate())
        }
    }

    internal fun deleteAllBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllBookmarks()
            bookmarks.postValue(repository.getAllBookmarksByDate())
        }
    }
}