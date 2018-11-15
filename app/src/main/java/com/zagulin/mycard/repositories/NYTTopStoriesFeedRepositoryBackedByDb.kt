package com.zagulin.mycard.repositories

import com.zagulin.mycard.App
import com.zagulin.mycard.db.AppDatabase
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.models.NewsItemDb
import com.zagulin.mycard.models.NewsItemNetwork
import com.zagulin.mycard.models.converters.NewsItemDbToNewItemModelConverter
import com.zagulin.mycard.models.converters.NewsItemNetworkToNewItemDbModelConverter
import io.reactivex.Observable
import io.reactivex.Single
import toothpick.Toothpick
import javax.inject.Inject

class NYTTopStoriesFeedRepositoryBackedByDb @Inject constructor()
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

    @Inject
    lateinit var appDatabase: AppDatabase

    private var newsItemDbToNewItemModelConverter = NewsItemDbToNewItemModelConverter()
    private var newsItemNetworkToNewItemDbModelConverter = NewsItemNetworkToNewItemDbModelConverter()


    init {
        Toothpick.inject(this, Toothpick.openScopes(
                App.Companion.Scopes.APP_SCOPE.name
                , App.Companion.Scopes.FEED_SCOPE.name)
        )
    }


    override fun getCategories(): Single<List<Category>> {
        var list = appDatabase.feedDao().getAllCategories()
        if (list.isEmpty()) {
            appDatabase.feedDao().insertCategories(categories.asIterable())
            list = appDatabase.feedDao().getAllCategories()
        }
        return Single.just(list)
    }


    override fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<FeedItem>> {
        val data = getFromStorage()
        return if (data != null && data.isNotEmpty()) {
            Single.just(getPage(data, from, shift))
        } else {

            return serverDataToDb(getDataFromNetwork(selectedCategory))
                    .doOnSuccess { saveToStorage(it) }
                    .map { getFromStorage() }

        }

    }

    private fun saveToStorage(list: List<NewsItemDb>) {
        appDatabase.feedDao().insertItems(list)
    }

    private fun serverDataToDb(dataFromServer: Single<List<NewsItemNetwork?>>): Single<List<NewsItemDb>> {
        return dataFromServer.flatMap {
            Observable
                    .fromIterable(it)
                    .map { itemNetworkt ->

                        val t = newsItemNetworkToNewItemDbModelConverter.convert(itemNetworkt)
                        t.categoryId = selectedCategory.id
                        t
                    }
                    .toList()

        }
    }


    private fun getFromStorage(): List<FeedItem>? {
        return appDatabase.feedDao().findItemByCategoryId(selectedCategory.id).map {
            newsItemDbToNewItemModelConverter.convert(it)
        }
    }


    override fun getNewsById(id: Int): Single<NewsItem> {
        return Single.just(appDatabase
                .feedDao()
                .findItemById(id.toLong()))
                .map { newsItemDbToNewItemModelConverter.convert(it) }
    }
}




