package com.zagulin.mycard.repositories

import com.zagulin.mycard.common.DataUtils
import com.zagulin.mycard.models.NewsItem
import io.reactivex.Observable
import io.reactivex.Single

class LocalFeedRepository : FeedRepositoryWithPagingationImitation() {

    private val feedItems = getNewsWithAds()

    override fun getNewsWithAdsAsObservable(from: Int, shift: Int): Single<List<Any>> {
//        val newFeedItems = mutableListOf<Any>()
//        for (i in 0 until shift) {
//            newFeedItems.add(feedItems[(from + i) % feedItems.size])
//        }
        return Single.just(getPage(feedItems, from, shift))
    }

    private fun getNewsWithAds(): List<Any> {
        val data = DataUtils.generateNews().toMutableList<Any>()
        if (data.isNotEmpty()) {
            data.add(1, "")
        }
        return data
    }

    fun getNews(): List<NewsItem> {
        return DataUtils.generateNews()
    }

    override fun getNewsById(id: Int): Single<NewsItem> {
        return Observable.fromIterable(feedItems).filter { it is NewsItem && it.id == id }.map { it as NewsItem }.firstOrError()
    }
}




