package com.zagulin.mycard.repositories

import com.zagulin.mycard.common.DataUtils
import com.zagulin.mycard.models.AdItem
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import io.reactivex.Observable
import io.reactivex.Single

class LocalFeedRepository : FeedRepositoryWithPagingationImitation() {

    private val feedItems = getNewsWithAds()
    override fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<FeedItem>> {
        return Single.just(getPage(feedItems, from, shift))
    }

    private fun getNewsWithAds(): List<FeedItem> {
        val data = DataUtils.generateNews().toMutableList<FeedItem>()
        if (data.isNotEmpty()) {
            data.add(1, AdItem())
        }
        return data
    }

    override fun getNewsById(id: Int): Single<NewsItem> {
        return Observable.fromIterable(feedItems).filter { it is NewsItem && it.id == id }.map { it as NewsItem }.firstOrError()
    }
}




