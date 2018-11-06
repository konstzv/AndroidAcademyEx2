package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.NewsItem
import io.reactivex.Single

interface FeedRepository {
    fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<Any>>
    fun getNewsById(id: Int): Single<NewsItem>
}