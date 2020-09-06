package com.pcfaktor.androiddev.data.base

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "article_table")
data class ArticleEntity(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "image")
    var image: String,
    @ColumnInfo(name = "creator")
    var creator: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "link")
    var link: String,
    @ColumnInfo(name = "readMoreReference")
    var readMoreLink: String,
    @ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean = false
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
