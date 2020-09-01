package com.pcfaktor.androiddev.data.network.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
data class DataDto(
    @Attribute
    val version: String,
    @Element(name = "channel")
    val channelDto: ChannelDto
)

@Xml(name = "channel")
data class ChannelDto(
    @PropertyElement
    val title: String,
    @PropertyElement
    val description: String,
    @PropertyElement
    val link: String,
    @PropertyElement
    val language: String,
    @PropertyElement
    val pubDate: String,
    @PropertyElement
    val managingEditor: String,
    @PropertyElement
    val generator: String,
    @Element
    val image: Image,
    @Element(name = "item")
    val articleList: List<ArticleDto>
)

@Xml(name = "image")
data class Image(
    @PropertyElement
    val link: String,
    @PropertyElement
    val url: String,
    @PropertyElement
    val title: String
)

@Xml(name = "item")
data class ArticleDto(
    @PropertyElement
    val title: String,
    @PropertyElement
    val description: String,
    @PropertyElement
    val link: String,
    @PropertyElement
    val pubDate: String,
    @PropertyElement(name = "dc:creator")
    val creator: String,
    @PropertyElement
    val category: String
)