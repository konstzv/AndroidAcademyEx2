package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.models.NewsItemNetwork
import com.zagulin.mycard.models.converters.NewsItemNetworkToNewItemModelConverter
import io.reactivex.Observable
import io.reactivex.Single
import toothpick.Toothpick
import javax.inject.Inject

class NYTTopStoriesFeedRepository @Inject constructor()
    : FeedRepositoryWithNetwork(), PagingationImitation {

    companion object {
        private val categories = arrayOf(
                Category(0, "home")
                , Category(1, "opinion")
                , Category(2, "world")
                , Category(3, "national")
                , Category(4, "politics")
                , Category(5, "upshot")
                , Category(6, "nyregion")
                , Category(7, "business")
                , Category(8, "technology")
                , Category(9, "health")
                , Category(10, "sports")
                , Category(11, "arts")
                , Category(12, "books")
                , Category(13, "movies")
                , Category(14, "theater")
                , Category(15, "sundayreview")
                , Category(16, "fashion")
                , Category(17, "tmagazine")
                , Category(18, "food")
                , Category(19, "travel")
                , Category(20, "magazine")
                , Category(21, "realestate")
                , Category(22, "automobiles")
                , Category(23, "obituaries")
                , Category(24, "insider")

        )
    }


    override var selectedCategory: Category = categories[0]

    init {
        Toothpick.inject(this, Toothpick.openScope("FeedScope"))
    }


    override fun getCategories(): Single<List<Category>> {
        return Single.just(categories.asList())
    }

    var mConverterItemNetworkToNews: NewsItemNetworkToNewItemModelConverter = NewsItemNetworkToNewItemModelConverter()


    @Volatile
    private var data = HashMap<Int, List<FeedItem>>()


    override fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<FeedItem>> {
        getFromStorage()?.let {
            return Single.just(getPage(it, from, shift))
        } ?: run {
            return serverDataToBasic(getDataFromNetwork(selectedCategory))
                    .doOnSuccess { saveToStorage(it) }
                    .map { getPage(it, from, shift) }
        }
    }


    private fun serverDataToBasic(dataFromServer: Single<List<NewsItemNetwork>>): Single<List<FeedItem>> {
        return dataFromServer.flatMap {
            Observable
                    .fromIterable(it)
                    .map(mConverterItemNetworkToNews::convert)
                    .toList()
        }
    }

    private fun saveToStorage(list: List<FeedItem>) {
        data[selectedCategory.id] = list
    }


    private fun getFromStorage(): List<FeedItem>? {
        return data[selectedCategory.id]
    }


    override fun getNewsById(id: Int): Single<NewsItem> {
        return Observable.fromIterable(data[selectedCategory.id])
                .filter {
                    it is NewsItem && it.id == id
                }
                .map { it as NewsItem }
                .firstOrError()
    }
}




