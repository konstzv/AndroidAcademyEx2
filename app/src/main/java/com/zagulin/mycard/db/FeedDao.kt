package com.zagulin.mycard.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.NewsItemDb
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface FeedDao {


    @Query("select * from news_items where categoryId = :categoryId")
    open fun findItemByCategoryId(categoryId: Int): Single<List<NewsItemDb>>

    @Query("select * from news_items where id = :id")
    fun findItemById(id: Long): Single<NewsItemDb>

    @Query("select * from news_items where id = :id")
    fun listenItemUpdate(id: Long): Flowable<NewsItemDb>

    @Query("select * from news_items")
    fun listenItemsUpdate(): Flowable<List<NewsItemDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(newsEntities: Iterable<NewsItemDb>)

    @Query("select * from categories where id = :id")
    fun findCategoryById(id: Long): Single<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(newsEntities: Iterable<Category>)

    @Query("select * from categories")
    fun getAllCategories(): Single<List<Category>>

    @Query("UPDATE  news_items SET title = :title, fulltext = :fullText WHERE id = :id ")
    fun update(id: Int, title: String, fullText: String): Int

    @Query("DELETE  FROM news_items WHERE id = :id ")
    fun deleteItem(id: Int): Int


}