package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.NewsItem
import io.reactivex.Observable
import io.reactivex.Single

interface FeedRepository {
    fun getNews(): List<NewsItem>
    fun getNewsWithAds(): List<Any>
    fun getNewsWithAdsAsObservable(from:Int, shift:Int ): Observable<List<Any>>
    fun getNewsById(id:Int ): Single<NewsItem>
}