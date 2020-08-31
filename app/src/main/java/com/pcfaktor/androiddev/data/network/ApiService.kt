package com.pcfaktor.androiddev.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.create

object ApiService {
    private const val BASE_URL = "https://habr.com/"

    private var tikXml = TikXml.Builder().exceptionOnUnreadXml(false).build()
    private val retrofitRss = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(TikXmlConverterFactory.create(tikXml))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retrofitService: HabrRssService = retrofitRss.create()
}