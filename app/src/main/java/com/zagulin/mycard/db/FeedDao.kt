package com.zagulin.mycard.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.NewsItemDb
import io.reactivex.Single


@Dao
interface FeedDao {



    @Query("select * from news_items where categoryId = :categoryId")
    open fun findItemByCategoryId(categoryId: Int): Single<List<NewsItemDb>>

    @Query("select * from news_items where id = :id")
    fun findItemById(id: Long): Single<NewsItemDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(newsEntities: Iterable<NewsItemDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(newsEntities: Iterable<Category>)

    @Query("select * from categories")
    fun getAllCategories(): Single<List<Category>>
}