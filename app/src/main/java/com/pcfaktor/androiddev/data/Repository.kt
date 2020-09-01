package com.pcfaktor.androiddev.data

import com.pcfaktor.androiddev.data.network.HabrRssService
import com.pcfaktor.androiddev.data.network.dto.DtoMapper
import com.pcfaktor.androiddev.domain.entity.Article
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class Repository(
    private val apiService: HabrRssService,
    private val dtoMapper: DtoMapper
) {

    suspend fun getArticlesAsync(): List<Article> {
        val articlesData = withContext(coroutineContext) {
            apiService.getArticles()
        }
        return dtoMapper.mapDataToArticles(articlesData).articleList
    }
}