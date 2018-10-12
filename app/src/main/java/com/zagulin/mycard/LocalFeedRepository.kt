package com.zagulin.mycard

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LocalFeedRepository : FeedRepository {
    override fun getNewsWithAds(): List<Any> {
        val data = DataUtils.generateNews().toMutableList<Any>()
        if (data.isNotEmpty()) {
            data.add(1, String())
        }
        return data
    }

    override fun getNews(): List<NewsItem> {
        return DataUtils.generateNews()


    }

    override fun getNewsWithAdsAsObservable(): Observable<List<Any>> {
        return Observable.just(getNewsWithAds()).delay(2, TimeUnit.SECONDS, Schedulers.computation()).subscribeOn(Schedulers.computation())
    }

}