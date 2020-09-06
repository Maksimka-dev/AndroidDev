package com.pcfaktor.androiddev.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pcfaktor.androiddev.data.Repository
import com.pcfaktor.androiddev.domain.entity.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel(private val repository: Repository) : ViewModel() {
    private val articles = MutableLiveData<List<Article>>()
    val articlesLiveData: LiveData<List<Article>>
        get() = articles

    internal fun onResume() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempArticles = repository.getArticlesAsync().toMutableList()
            val bookmarkTitlesList = mutableListOf<String>()
            repository.getAllBookmarksByDate().forEach {
                bookmarkTitlesList.add(it.title)
            }
            tempArticles.forEach {
                if (bookmarkTitlesList.contains(it.title))
                    it.isBookmarked = true
            }
            articles.postValue(tempArticles)
        }
    }

    private fun addBookmark(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToBookmarks(article)
        }
    }

    private fun deleteBookmark(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromBookmarks(article)
        }
    }

    internal fun updateBookmarks(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempMutableList: MutableList<Article> = articles.value!!.toMutableList()
            val indexArticle = tempMutableList.indexOf(article)
            article.isBookmarked = true
            val indexBookmark = repository.getAllBookmarksByDate().indexOf(article)
            if (indexBookmark != -1) {
                deleteBookmark(article)
                article.isBookmarked = false
                tempMutableList[indexArticle] = article
            } else {
                addBookmark(article)
                tempMutableList[indexArticle] = article
            }
            articles.postValue(tempMutableList)
        }
    }
}