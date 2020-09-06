package com.pcfaktor.androiddev.data.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class ArticlesRoomDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: ArticlesRoomDatabase? = null

        fun getDatabase(context: Context): ArticlesRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticlesRoomDatabase::class.java,
                    "articles_database"
                )
//                    .addCallback(ArticlesRoomDatabaseCallback(CoroutineScope(Dispatchers.IO)))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class ArticlesRoomDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.articleDao())
                }
            }
        }

        suspend fun populateDatabase(articleDao: ArticleDao) {
            articleDao.deleteAll()
        }
    }

}
