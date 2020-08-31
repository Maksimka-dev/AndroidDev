package com.pcfaktor.androiddev.domain.entity

data class Article(
    val title: String,
    val description: String,
    val image: String,
    val creator: String,
    val date: String,
    val link: String,
    val readMoreReference: String
)