package com.zagulin.mycard.common.pagination

import com.zagulin.mycard.models.PaginationData
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject


class FeedPaginator(val repository: FeedRepository): Paginator<Any> {

    companion object {
        const val PAGE_SIZE = 10
    }

    var pageNum = 0



    var source = PublishSubject.create<PaginationData<Any>>()

    override fun loadNextPage() {
        val currentPageNum = pageNum
        val start = pageNum++ * PAGE_SIZE
        repository.getNewsWithAdsAsObservable(start, PAGE_SIZE).subscribeBy(
                onNext = {
                    source.onNext(PaginationData(it,currentPageNum, PAGE_SIZE))
                }
        )

    }

    override fun reload() {
        pageNum = 0
    }

    override fun paginationObservable(): Observable<PaginationData<Any>> {
       return  source
    }

}