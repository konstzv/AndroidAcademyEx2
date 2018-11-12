package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.AdItem
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.models.NewItemModelConverter
import com.zagulin.mycard.repositories.api.NewYorkTimesAPIService
import io.reactivex.Observable
import io.reactivex.Single
import toothpick.Toothpick
import javax.inject.Inject

class NYTTopStoriesFeedRepository @Inject constructor()
    : FeedRepositoryWithPagingationImitation() {

    override fun getCategory(): Category {
        return cat
    }


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

    private var cat: Category = categories[0]

    init {
        Toothpick.inject(this, Toothpick.openScope("FeedScope"))
    }

    override fun setCategory(category: Category) {
        cat = category
    }

    override fun getCategories(): Single<List<Category>> {
        return Single.just(categories.asList())
    }

    @Inject
    lateinit var converter: NewItemModelConverter
    private val service = NewYorkTimesAPIService.create()

    @Volatile
    private var data = HashMap<Int, List<FeedItem>>()

    override fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<FeedItem>> {


                //            if (data[category.id] == null) {

                data[cat.id]?.let { list ->
                    return Single.just(getPage(list, from, shift))
                } ?: run {
                    return service
                            .getTopStories(cat.name)

                            .flatMapIterable { it.results }
                            .map { it -> converter.convert(it) }
                            .toList()

                            .flatMap {
                                val list = mutableListOf<FeedItem>()
                                if (it.isNotEmpty()) {

                                    list.addAll(it)
                                    list.add(1, AdItem())
                                    data[cat.id] = list
                                }
                                Single.just(getPage(list, from, shift))

                            }

                }




    }


    override fun getNewsById(id: Int): Single<NewsItem> {

          return  Observable.fromIterable( data[cat.id]).filter{it is NewsItem && it.id == id}.map { it as NewsItem }.firstOrError()


    }
}




