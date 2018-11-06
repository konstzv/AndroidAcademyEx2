package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.NewItemModelConverter
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.repositories.api.NewYorkTimesAPIService
import io.reactivex.Single

open class NYTTopStoriesFeedRepository : FeedRepositoryWithPagingationImitation() {

    enum class SectionNames(val sectionName: String) {
        WORLD("world")
    }

    private val converter = NewItemModelConverter()
    private val service = NewYorkTimesAPIService.create()

    @Volatile
    private var data = mutableListOf<Any>()

    override fun getNewsWithAdsAsSingle(from: Int, shift: Int): Single<List<Any>> {
        if (data.isEmpty()) {

            return service
                    .getTopStories(SectionNames.WORLD.sectionName)

                    .flatMapIterable { it.results }
                    .map { it -> converter.convert(it) }
                    .toList()

                    .flatMap {
                        if (data.isEmpty() && it.isNotEmpty()) {
                            data.addAll(it)
                            data.add(1, "")
                        }
                        Single.just(getPage(data, from, shift))

                    }
        } else {
            return Single.just(getPage(data, from, shift))
        }
    }


    override fun getNewsById(id: Int): Single<NewsItem> {
        return Single.just(data.first { it is NewsItem && it.id == id } as NewsItem)

    }
}




