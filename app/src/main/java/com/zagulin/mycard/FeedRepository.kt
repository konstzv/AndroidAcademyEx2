package com.zagulin.mycard

import io.reactivex.Observable

interface FeedRepository {
    fun getNews(): List<NewsItem>
    fun getNewsWithAds(): List<Any>
    fun getNewsWtihAdsAsObservable(): Observable<List<Any>>
}