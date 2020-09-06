package com.pcfaktor.androiddev.data.base

import com.pcfaktor.androiddev.domain.entity.Article

fun Article.articleToEntity(): ArticleEntity {
    return ArticleEntity(
        title = this.title,
        description = this.description,
        image = this.image,
        creator = this.creator,
        date = this.date,
        link = this.link,
        readMoreLink = this.readMoreLink,
        isBookmarked = this.isBookmarked
    )
}

fun ArticleEntity.entityToArticle(): Article {
    return Article(
        title = this.title,
        description = this.description,
        image = this.image,
        creator = this.creator,
        date = this.date,
        link = this.link,
        readMoreLink = this.readMoreLink,
        isBookmarked = this.isBookmarked
    )
}

