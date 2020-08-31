package com.pcfaktor.androiddev.data.network.dto

import com.pcfaktor.androiddev.domain.entity.Article
import com.pcfaktor.androiddev.domain.entity.Channel

class DtoMapper {

    fun mapDataToArticles(dataDto: DataDto): Channel {
        val channelDataDto = dataDto.channelDto
        return Channel(
            title = channelDataDto.title,
            description = channelDataDto.description,
            pubDate = channelDataDto.pubDate,
            language = channelDataDto.language,
            link = channelDataDto.link,
            image = channelDataDto.image.url,
            articleList = mapArticleList(channelDataDto.articleList)
        )
    }

    private fun mapArticleList(articleList: List<ArticleDto>): List<Article> {
        return articleList.map { articleDto -> mapArticle(articleDto) }
    }

    private fun mapArticle(articleDto: ArticleDto): Article {
        return Article(
            title = articleDto.title,
            description = articleDto.description,
            image = articleDto.link, // TODO: get image from description
            creator = articleDto.creator,
            date = articleDto.pubDate,
            link = articleDto.link,
            readMoreReference = articleDto.link // TODO: get read more reference from description
        )
    }
}