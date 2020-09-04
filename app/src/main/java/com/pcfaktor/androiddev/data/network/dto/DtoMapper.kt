package com.pcfaktor.androiddev.data.network.dto

import com.pcfaktor.androiddev.domain.entity.Article
import com.pcfaktor.androiddev.domain.entity.Channel
import org.jsoup.Jsoup

private const val POSTFIX_RU = "Читать дальше"
private const val POSTFIX_EN = "Read more"

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
        val descriptionData = Jsoup.parse(articleDto.description)
        val imgLink = descriptionData.selectFirst("img")?.let {
            it.attr("src")
        } ?: ""
        val readMoreLink = descriptionData.select("a")?.let {
            it.eachAttr("href").last()
        } ?: ""
        val descriptionText = descriptionData.text()
            .substringBefore(POSTFIX_RU)
            .substringBefore(POSTFIX_EN)

        val date = articleDto.pubDate.substring(5..21)
        return Article(
            title = articleDto.title,
            description = descriptionText,
            image = imgLink,
            creator = articleDto.creator,
            date = date,
            link = articleDto.link,
            readMoreReference = readMoreLink
        )
    }
}