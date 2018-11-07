package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import io.reactivex.Single

interface FeedRepository {
    fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<FeedItem>>
    fun getNewsById(id: Int): Single<NewsItem>
    fun getCategories():Single<List<Category>>
    fun setCategory(category: Category)
}