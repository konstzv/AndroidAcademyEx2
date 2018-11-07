package com.zagulin.mycard.common.pagination

import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.PaginationData
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


class FeedPaginator(private val repository: FeedRepository

) : Paginator<FeedItem> {



    companion object {
        const val PAGE_SIZE = 10

        enum class STATE {
            IDLE, LOADING
        }
    }

    private var state = STATE.IDLE

    private var pageNum = 0


    private var source = PublishSubject.create<PaginationData<FeedItem>>()




    override fun loadNextPage() {

            if (state == STATE.IDLE) {
                state = STATE.LOADING
                val currentPageNum = pageNum
                val start = pageNum++ * PAGE_SIZE
                repository.getNewsWithAdsAsSingle(start, PAGE_SIZE).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                        onSuccess = {
                            source.onNext(PaginationData(it, currentPageNum, PAGE_SIZE))
                            state = STATE.IDLE
                        },
                        onError = {
                            source.onError(it)
                            state = STATE.IDLE
                        }

                )
            }



    }

    override fun reload() {
        state = STATE.IDLE
        pageNum = 0
    }

    override fun paginationObservable(): Observable<PaginationData<FeedItem>> {
        return source
    }

}

