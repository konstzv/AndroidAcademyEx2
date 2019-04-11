package com.zagulin.mycard.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.NewsItemDb


@Database(entities = [NewsItemDb::class,Category::class], version = 7, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun feedDao(): FeedDao

}