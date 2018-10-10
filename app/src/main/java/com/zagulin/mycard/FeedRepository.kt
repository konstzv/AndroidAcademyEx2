package com.zagulin.mycard

interface FeedRepository {
    fun getNews(): List<NewsItem>
    fun getNewsWithAds(): List<Any>
}