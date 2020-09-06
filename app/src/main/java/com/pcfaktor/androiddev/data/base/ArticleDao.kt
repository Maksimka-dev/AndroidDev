package com.pcfaktor.androiddev.data.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: ArticleEntity)

    @Query("DELETE FROM article_table WHERE title LIKE :title")
    suspend fun delete(title: String)

    @Query("DELETE FROM article_table")
    suspend fun deleteAll()

    @Query("SELECT * from article_table ORDER BY date DESC")
    suspend fun getAllArticlesByDate(): List<ArticleEntity>
}