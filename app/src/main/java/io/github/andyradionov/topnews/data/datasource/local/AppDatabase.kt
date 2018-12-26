package io.github.andyradionov.topnews.data.datasource.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import io.github.andyradionov.topnews.data.entities.Article

/**
 * @author Andrey Radionov
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}