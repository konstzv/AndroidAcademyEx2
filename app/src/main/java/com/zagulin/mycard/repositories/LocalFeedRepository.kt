package com.zagulin.mycard.repositories

import com.zagulin.mycard.common.DataUtils
import com.zagulin.mycard.models.AdItem
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject


class LocalFeedRepository @Inject constructor() : FeedRepositoryWithPagingationImitation() {
    override fun getCategory(): Category {
        return cat
    }


    override fun getCategories(): Single<List<Category>> {
        return Single
                .just(feedItems.filter { it is NewsItem }
                        .mapNotNull { (it as NewsItem).category }
                        .distinctBy { it.id })
    }

    override fun setCategory(category: Category) {
        cat = category
    }


    private val feedItems = getNewsWithAds()
    private val categories = feedItems
            .filter { it is NewsItem }
            .mapNotNull { (it as NewsItem).category }
            .distinctBy { it.id }
    var cat: Category = categories[0]
    override fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<FeedItem>> {
        return Single.just(getPage(feedItems
                .filter { (it is NewsItem) && (it.category == cat) }, from, shift
        ))
    }

    private fun getNewsWithAds(): List<FeedItem> {
        val data = DataUtils.generateNews().toMutableList<FeedItem>()
        if (data.isNotEmpty()) {
            data.add(1, AdItem())
        }
        return data
    }

    override fun getNewsById(id: Int): Single<NewsItem> {
        return Observable.fromIterable(feedItems).filter { it is NewsItem && it.id == id }
                .map { it as NewsItem }
                .firstOrError()
    }
}




