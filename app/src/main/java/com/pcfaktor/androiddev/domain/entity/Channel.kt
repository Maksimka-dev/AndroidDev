package com.pcfaktor.androiddev.domain.entity

data class Channel(
    val title: String,
    val description: String,
    val pubDate: String,
    val language: String,
    val link: String,
    val image: String,
    val articleList: List<Article>
)