package com.zagulin.mycard.common.pagination

import com.zagulin.mycard.models.PaginationData
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


class FeedPaginator(private val repository: FeedRepository) : Paginator<Any> {

    companion object {
        const val PAGE_SIZE = 10
    }

    private var pageNum = 0


    private var source = PublishSubject.create<PaginationData<Any>>()

    override fun loadNextPage() {
        val currentPageNum = pageNum
        val start = pageNum++ * PAGE_SIZE
        repository.getNewsWithAdsAsObservable(start, PAGE_SIZE).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onSuccess = {
                    source.onNext(PaginationData(it, currentPageNum, PAGE_SIZE))
                },
                onError = {
                    source.onError(it)
                }

        )

    }

    override fun reload() {
        pageNum = 0
    }

    override fun paginationObservable(): Observable<PaginationData<Any>> {
        return source
    }

}