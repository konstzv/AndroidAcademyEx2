package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.NewsItemNetwork
import com.zagulin.mycard.repositories.api.NewYorkTimesAPIService
import io.reactivex.Single


abstract class FeedRepositoryWithNetwork : FeedRepository {

    private val service = NewYorkTimesAPIService.create()

    fun getDataFromNetwork(category: Category): Single<List<NewsItemNetwork>> {
        return service
                .getTopStories(category.name)
                .map {
                    val res = it.results
                    res ?: emptyList<NewsItemNetwork>()
                }
    }

}