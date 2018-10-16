package com.zagulin.mycard

import android.annotation.SuppressLint
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

    @SuppressLint("RxDefaultScheduler")
    fun getNewsAndAdsPeriodically(): Observable<Any> {
        val news = getNews()


        val newsObservable = Observable
                .interval(5, TimeUnit.SECONDS)
                .flatMap { i ->
                    val counter = i * 3
                    Observable.just<Any>(
                            news[(counter % news.size).toInt()],
                            news[((counter + 1) % news.size).toInt()],
                            news[((counter + 2) % news.size).toInt()])
                }

        val adsObservable = Observable
                .interval(8, TimeUnit.SECONDS)
                .map { _ -> String() }

        return newsObservable.mergeWith(adsObservable)
    }
}




