package com.pcfaktor.androiddev.data.network

import com.pcfaktor.androiddev.data.network.dto.DataDto
import retrofit2.http.GET

interface RssService {
    @GET("ru/rss/hub/android_dev")
    suspend fun getArticles(): DataDto
}