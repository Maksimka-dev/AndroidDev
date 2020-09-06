package com.pcfaktor.androiddev.data

import com.pcfaktor.androiddev.data.base.ArticleDao
import com.pcfaktor.androiddev.data.base.ArticleEntity
import com.pcfaktor.androiddev.data.base.articleToEntity
import com.pcfaktor.androiddev.data.base.entityToArticle
import com.pcfaktor.androiddev.data.network.RssService
import com.pcfaktor.androiddev.data.network.dto.DtoMapper
import com.pcfaktor.androiddev.domain.entity.Article
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class Repository(
    private val apiService: RssService,
    private val dtoMapper: DtoMapper,
    private val dao: ArticleDao
) {

    suspend fun getArticlesAsync(): List<Article> {
        val articlesData =
            withContext(coroutineContext) {
                apiService.getArticles()
            }
        return dtoMapper.mapDataToArticles(articlesData).articleList
    }

    suspend fun addToBookmarks(article: Article) {
        dao.insert(article.articleToEntity())
    }

    suspend fun deleteFromBookmarks(article: Article) {
        dao.delete(article.articleToEntity().title)
    }

    suspend fun getAllBookmarksByDate(): List<Article> {
        return dao.getAllArticlesByDate()
            .map { articleEntity: ArticleEntity -> articleEntity.entityToArticle() }
    }

    suspend fun deleteAllBookmarks() {
        dao.deleteAll()
    }
}