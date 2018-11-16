package com.zagulin.mycard.repositories

import com.zagulin.mycard.App
import com.zagulin.mycard.db.AppDatabase
import com.zagulin.mycard.models.*
import com.zagulin.mycard.models.converters.NewsItemDbToNewItemModelConverter
import com.zagulin.mycard.models.converters.NewsItemNetworkToNewItemDbModelConverter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
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
        return appDatabase.feedDao().getAllCategories().subscribeOn(Schedulers.io())
    }


    override fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<FeedItem>> {
        return getFromStorage()
                .filter { it.isEmpty() }
                .switchIfEmpty(
                        serverDataToDb(getDataFromNetwork(selectedCategory))
                                .doOnSuccess { saveToStorage(it) }
                                .flatMap { getFromStorage() }

                )

    }

    private fun saveToStorage(list: List<NewsItemDb>) {
        appDatabase.feedDao().insertItems(list)
    }

    private fun serverDataToDb(dataFromServer: Single<List<NewsItemNetwork>>): Single<List<NewsItemDb>> {
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


    private fun getFromStorage(): Single<List<FeedItem>> {
        return appDatabase.feedDao().findItemByCategoryId(selectedCategory.id)
                .flatMapObservable { it -> Observable.fromIterable(it) }
                .map { newsItemDbToNewItemModelConverter.convert(it) as FeedItem }
                .toList().subscribeOn(Schedulers.io())
    }


    override fun getNewsById(id: Int): Single<NewsItem> {
        return  appDatabase.feedDao()
                .findItemById(id.toLong())
                .map { newsItemDbToNewItemModelConverter.convert(it) }
                .  subscribeOn(Schedulers.io())
    }
}



