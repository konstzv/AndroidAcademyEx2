package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.models.Optional
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface FeedRepository {
    fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<FeedItem>>
    fun getNewsById(id: Int): Single<NewsItem>
    fun updateItem(newsItem: NewsItem): Completable
    fun getCategories(): Single<List<Category>>
    fun listenItemUpdate(id:Int): Flowable<Optional<NewsItem>>
    fun removeItem(id:Int):Completable
    var selectedCategory:Category
    fun clearStorage(): Completable
    fun isServerAvailable(): Single<Boolean>
    fun getSelectedCategoryObserbable(): Observable<Category>
}